package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.dao.*;
import cc.mrbird.febs.cos.entity.*;
import cc.mrbird.febs.cos.service.IBulletinInfoService;
import cc.mrbird.febs.cos.service.IMessageInfoService;
import cc.mrbird.febs.cos.service.IMusicInfoService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 音乐管理 实现层
 *
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MusicInfoServiceImpl extends ServiceImpl<MusicInfoMapper, MusicInfo> implements IMusicInfoService {

    private final SubscriptionInfoMapper subscriptionInfoMapper;

    private final IMessageInfoService messageInfoService;

    private final AlbumInfoMapper albumInfoMapper;

    private final MusicPlayRecordMapper musicPlayRecordMapper;

    private final EvaluateInfoMapper evaluateInfoMapper;

    private final SingerInfoMapper singerInfoMapper;

    private final UserInfoMapper userInfoMapper;

    private final CollectInfoMapper collectInfoMapper;

    private final IBulletinInfoService bulletinInfoService;

    /**
     * 分页获取音乐信息
     *
     * @param page      分页对象
     * @param musicInfo 音乐信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryMusicPage(Page<MusicInfo> page, MusicInfo musicInfo) {
        return baseMapper.queryMusicPage(page, musicInfo);
    }

    /**
     * 首页统计信息
     *
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> homeData() {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("userNum", 0);
                put("singerNum", 0);
                put("musicNum", 0);
                put("albumNum", 0);
            }
        };


        result.put("userNum", userInfoMapper.selectCount(Wrappers.<UserInfo>lambdaQuery()));
        result.put("singerNum", singerInfoMapper.selectCount(Wrappers.<SingerInfo>lambdaQuery()));
        result.put("musicNum", this.count());
        result.put("albumNum", albumInfoMapper.selectCount(Wrappers.<AlbumInfo>lambdaQuery()));

        Integer year = DateUtil.thisYear();
        Integer month = DateUtil.thisMonth() + 1;
//        // 本月发帖数量
//        result.put("monthOrderNum", baseMapper.selectPostNumByDate(year, month));
//        // 获取本月浏览量
//        result.put("monthOrderTotal", baseMapper.selectViewNumByDate(year, month));
//
//        // 本年发帖数量
//        result.put("yearOrderNum", baseMapper.selectPostNumByDate(year, null));
//        // 本年浏览量
//        result.put("yearOrderTotal", baseMapper.selectViewNumByDate(year, null));

//        // 近十天发帖统计
//        result.put("orderNumDayList", baseMapper.selectOrderNumWithinDays());
        // 近十天浏览统计
        result.put("orderViewDayList", baseMapper.selectOrderViewWithinDays());
        // 公告信息
        result.put("bulletinInfoList", bulletinInfoService.list(Wrappers.<BulletinInfo>lambdaQuery().eq(BulletinInfo::getRackUp, 1)));

        return result;
    }

    /**
     * 用户搜索
     *
     * @param key 参数
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> selSearchDetailList(String key) {
        // 返回数据
        return new LinkedHashMap<String, Object>() {
            {
                put("singer", singerInfoMapper.querySingerList(key));
                put("music", baseMapper.queryMusicList(key));
                put("album", albumInfoMapper.queryAlbumList(key));
            }
        };
    }

    /**
     * 根据专辑获取收录歌曲
     *
     * @param albumId 专辑ID
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectMusicByAlbum(Integer albumId) {
        return baseMapper.selectMusicByAlbum(albumId);
    }

    /**
     * 根据歌手获取收录歌曲
     *
     * @param singerId 歌手ID
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectMusicBySinger(Integer singerId) {
        return baseMapper.selectMusicBySinger(singerId);
    }

    /**
     * 获取歌曲详情
     *
     * @param musicId 歌曲ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> queryMusicDetail(Integer musicId) {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("music", null);
                put("singer", null);
                put("evaluate", Collections.emptyList());
            }
        };
        // 歌曲信息
        MusicInfo musicInfo = this.getById(musicId);
        // 歌曲收藏
        musicInfo.setCollectNum(collectInfoMapper.selectCount(Wrappers.<CollectInfo>lambdaQuery().eq(CollectInfo::getMusicId, musicId)));

        // 歌手信息
        SingerInfo singerInfo = singerInfoMapper.selectById(musicInfo.getSingerId());
        result.put("singer", singerInfo);
        // 歌曲评价
        result.put("evaluate", evaluateInfoMapper.queryEvaluateByMusic(musicId));
        return result;
    }

    /**
     * 新增音乐信息
     *
     * @param musicInfo 音乐信息
     * @return 结果
     */
    @Override
    public Boolean musicAdd(MusicInfo musicInfo) {
        musicInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        // 歌曲编号
        musicInfo.setCode("MUS-" + System.currentTimeMillis());
//        List<String> singerIds = StrUtil.split(musicInfo.getSingerIds(), ",");
        // 获取关注此歌手的用户
        List<SubscriptionInfo> subscriptionList = subscriptionInfoMapper.selectList(Wrappers.<SubscriptionInfo>lambdaQuery()
                .eq(SubscriptionInfo::getSingerId, musicInfo.getSingerId()));

        if (CollectionUtil.isNotEmpty(subscriptionList)) {
            List<MessageInfo> toAddList = new ArrayList<>();
            for (SubscriptionInfo subscription : subscriptionList) {
                // 消息通知
                toAddList.add(new MessageInfo(subscription.getUserId(), "新曲通知", "你好、您关注的歌手有新曲发布~《" + musicInfo.getName() + "》正在火爆放映", "0", DateUtil.formatDateTime(new Date())));
            }
            messageInfoService.saveBatch(toAddList);
        }
        return save(musicInfo);
    }
}
