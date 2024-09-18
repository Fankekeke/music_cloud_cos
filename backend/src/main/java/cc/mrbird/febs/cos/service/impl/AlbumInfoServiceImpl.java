package cc.mrbird.febs.cos.service.impl;

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

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

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
