package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.CollectInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 用户音乐收藏 mapper层
 *
 * @author FanK
 */
public interface CollectInfoMapper extends BaseMapper<CollectInfo> {

    /**
     * 分页获取用户音乐收藏信息
     *
     * @param page        分页对象
     * @param collectInfo 用户音乐收藏信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryCollectPage(Page<CollectInfo> page, @Param("collectInfo") CollectInfo collectInfo);

    /**
     * 获取用户收藏歌曲
     *
     * @param userId 用户ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> queryCollectByUserId(@Param("userId") Integer userId);
}
