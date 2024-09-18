package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.dao.SubscriptionInfoMapper;
import cc.mrbird.febs.cos.entity.MessageInfo;
import cc.mrbird.febs.cos.entity.MusicInfo;
import cc.mrbird.febs.cos.dao.MusicInfoMapper;
import cc.mrbird.febs.cos.entity.SubscriptionInfo;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

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
        List<String> singerIds = StrUtil.split(musicInfo.getSingerIds(), ",");
        // 获取关注此歌手的用户
        List<SubscriptionInfo> subscriptionList = subscriptionInfoMapper.selectList(Wrappers.<SubscriptionInfo>lambdaQuery()
                .in(CollectionUtil.isNotEmpty(singerIds), SubscriptionInfo::getSingerId, singerIds));

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
