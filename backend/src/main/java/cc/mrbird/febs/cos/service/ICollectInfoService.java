package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.CollectInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 用户音乐收藏 service层
 *
 * @author FanK
 */
public interface ICollectInfoService extends IService<CollectInfo> {

    /**
     * 分页获取用户音乐收藏信息
     *
     * @param page        分页对象
     * @param collectInfo 用户音乐收藏信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryCollectPage(Page<CollectInfo> page, CollectInfo collectInfo);

    /**
     * 获取用户收藏歌曲
     *
     * @param userId 用户ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> queryCollectByUserId(Integer userId);
}
