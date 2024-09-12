package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.SubscriptionInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

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
}
