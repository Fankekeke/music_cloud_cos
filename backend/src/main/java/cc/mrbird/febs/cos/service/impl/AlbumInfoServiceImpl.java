package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.AlbumInfo;
import cc.mrbird.febs.cos.dao.AlbumInfoMapper;
import cc.mrbird.febs.cos.service.IAlbumInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 专辑管理 实现层
 *
 * @author FanK
 */
@Service
public class AlbumInfoServiceImpl extends ServiceImpl<AlbumInfoMapper, AlbumInfo> implements IAlbumInfoService {

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
}
