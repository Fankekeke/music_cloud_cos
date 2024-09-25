package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.SingerInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 歌手信息 mapper层
 *
 * @author FanK
 */
public interface SingerInfoMapper extends BaseMapper<SingerInfo> {

    /**
     * 分页获取歌手信息信息
     *
     * @param page       分页对象
     * @param singerInfo 歌手信息信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> querySingerPage(Page<SingerInfo> page, @Param("singerInfo") SingerInfo singerInfo);

    /**
     * 获取歌手信息信息
     *
     * @param key 关键字
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> querySingerList(@Param("key") String key);

    /**
     * 获取首页歌手信息
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> queryHomeSingerList();
}
