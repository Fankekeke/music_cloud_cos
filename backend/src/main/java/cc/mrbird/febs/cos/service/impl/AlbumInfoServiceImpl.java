package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.dao.MusicInfoMapper;
import cc.mrbird.febs.cos.dao.SingerInfoMapper;
import cc.mrbird.febs.cos.dao.SubscriptionInfoMapper;
import cc.mrbird.febs.cos.entity.AlbumInfo;
import cc.mrbird.febs.cos.dao.AlbumInfoMapper;
import cc.mrbird.febs.cos.entity.MessageInfo;
import cc.mrbird.febs.cos.entity.SubscriptionInfo;
import cc.mrbird.febs.cos.service.IAlbumInfoService;
import cc.mrbird.febs.cos.service.IMessageInfoService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 专辑管理 实现层
 *
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AlbumInfoServiceImpl extends ServiceImpl<AlbumInfoMapper, AlbumInfo> implements IAlbumInfoService {

    private final SubscriptionInfoMapper subscriptionInfoMapper;

    private final IMessageInfoService messageInfoService;

    private final MusicInfoMapper musicInfoMapper;

    private final SingerInfoMapper singerInfoMapper;

    /**
     * 分页获取专辑信息
     *
     * @param page      分页对象
     * @param albumInfo 专辑信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryAlbumPage(Page<AlbumInfo> page, AlbumInfo albumInfo) {
        return baseMapper.queryAlbumPage(page, albumInfo);
    }

    /**
     * 获取专辑详情
     *
     * @param albumId 专辑ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> queryAlbumDetail(Integer albumId) {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("album", null);
                put("singer", null);
                put("music", musicInfoMapper.selectMusicByAlbum(albumId));
            }
        };
        // 专辑信息
        AlbumInfo albumInfo = this.getById(albumId);
        result.put("album", albumInfo);
        // 歌手信息
        result.put("singer", singerInfoMapper.selectById(albumInfo.getSingerId()));
        return result;
    }

    /**
     * 新增专辑信息
     *
     * @param albumInfo 专辑信息
     * @return 结果
     */
    @Override
    public Boolean albumAdd(AlbumInfo albumInfo) {
        albumInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        // 专辑编号
        albumInfo.setCode("ALB-" + System.currentTimeMillis());

        // 获取关注此歌手的用户
        List<SubscriptionInfo> subscriptionList = subscriptionInfoMapper.selectList(Wrappers.<SubscriptionInfo>lambdaQuery()
                .eq(SubscriptionInfo::getSingerId, albumInfo.getSingerId()));

        if (CollectionUtil.isNotEmpty(subscriptionList)) {
            List<MessageInfo> toAddList = new ArrayList<>();
            for (SubscriptionInfo subscription : subscriptionList) {
                // 消息通知
                toAddList.add(new MessageInfo(subscription.getUserId(), "专辑发布", "你好、您关注的歌手有新专辑发布~《" + albumInfo.getName() + "》、请进行查看", "0", DateUtil.formatDateTime(new Date())));
            }
            messageInfoService.saveBatch(toAddList);
        }
        return save(albumInfo);
    }
}
