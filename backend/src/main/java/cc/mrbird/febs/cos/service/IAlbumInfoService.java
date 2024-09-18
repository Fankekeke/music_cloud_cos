package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.AlbumInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 专辑管理 service层
 *
 * @author FanK
 */
public interface IAlbumInfoService extends IService<AlbumInfo> {

    /**
     * 分页获取专辑信息
     *
     * @param page      分页对象
     * @param albumInfo 专辑信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryAlbumPage(Page<AlbumInfo> page, AlbumInfo albumInfo);

    /**
     * 新增专辑信息
     *
     * @param albumInfo 专辑信息
     * @return 结果
     */
    Boolean albumAdd(AlbumInfo albumInfo);
}
