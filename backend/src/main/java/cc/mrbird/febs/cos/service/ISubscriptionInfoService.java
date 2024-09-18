package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.SubscriptionInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 歌手关注 service层
 *
 * @author FanK
 */
public interface ISubscriptionInfoService extends IService<SubscriptionInfo> {

    /**
     * 分页获取歌手关注信息
     *
     * @param page             分页对象
     * @param subscriptionInfo 歌手关注信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> querySubPage(Page<SubscriptionInfo> page, SubscriptionInfo subscriptionInfo);

    /**
     * 根据用户获取关注歌手
     *
     * @param userId 用户ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectSubByUser(Integer userId);

    /**
     * 根据歌手获取粉丝数量
     *
     * @param singerId 歌手ID
     * @return 结果
     */
    Integer selectFansBySinger(Integer singerId);
}
