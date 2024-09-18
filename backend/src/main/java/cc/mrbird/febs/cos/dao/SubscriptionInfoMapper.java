package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.SubscriptionInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 歌手关注 mapper层
 *
 * @author FanK
 */
public interface SubscriptionInfoMapper extends BaseMapper<SubscriptionInfo> {

    /**
     * 分页获取歌手关注信息
     *
     * @param page             分页对象
     * @param subscriptionInfo 歌手关注信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> querySubPage(Page<SubscriptionInfo> page, @Param("subscriptionInfo") SubscriptionInfo subscriptionInfo);

    /**
     * 根据用户获取关注歌手
     *
     * @param userId 用户ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectSubByUser(@Param("userId") Integer userId);
}
