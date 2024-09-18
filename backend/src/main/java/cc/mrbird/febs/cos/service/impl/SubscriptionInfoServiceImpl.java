package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.SubscriptionInfo;
import cc.mrbird.febs.cos.dao.SubscriptionInfoMapper;
import cc.mrbird.febs.cos.service.ISubscriptionInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 歌手关注 实现层
 *
 * @author FanK
 */
@Service
public class SubscriptionInfoServiceImpl extends ServiceImpl<SubscriptionInfoMapper, SubscriptionInfo> implements ISubscriptionInfoService {

    /**
     * 分页获取歌手关注信息
     *
     * @param page             分页对象
     * @param subscriptionInfo 歌手关注信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> querySubPage(Page<SubscriptionInfo> page, SubscriptionInfo subscriptionInfo) {
        return baseMapper.querySubPage(page, subscriptionInfo);
    }

    /**
     * 根据用户获取关注歌手
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectSubByUser(Integer userId) {
        return baseMapper.selectSubByUser(userId);
    }
}
