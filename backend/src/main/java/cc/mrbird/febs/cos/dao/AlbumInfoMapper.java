package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.AlbumInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 专辑管理 mapper层
 *
 * @author FanK
 */
public interface AlbumInfoMapper extends BaseMapper<AlbumInfo> {

    /**
     * 分页获取专辑信息
     *
     * @param page      分页对象
     * @param albumInfo 专辑信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryAlbumPage(Page<AlbumInfo> page, @Param("albumInfo") AlbumInfo albumInfo);

    /**
     * 获取专辑信息
     *
     * @param key 专辑信息
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> queryAlbumList(@Param("key") String key);

    /**
     * 获取主页专辑信息
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> queryHomeAlbumList();

    /**
     * 获取主页专辑信息
     *
     * @param singerId 歌手ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> queryAlbumBySingerList(@Param("singerId") Integer singerId);
}
