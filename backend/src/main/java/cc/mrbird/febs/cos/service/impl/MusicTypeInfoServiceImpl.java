package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.MusicTypeInfo;
import cc.mrbird.febs.cos.dao.MusicTypeInfoMapper;
import cc.mrbird.febs.cos.service.IMusicTypeInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 歌曲类型 实现层
 *
 * @author FanK
 */
@Service
public class MusicTypeInfoServiceImpl extends ServiceImpl<MusicTypeInfoMapper, MusicTypeInfo> implements IMusicTypeInfoService {

    /**
     * 分页获取歌曲类型信息
     *
     * @param page          分页对象
     * @param musicTypeInfo 歌曲类型信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryMusicTypePage(Page<MusicTypeInfo> page, MusicTypeInfo musicTypeInfo) {
        return baseMapper.queryMusicTypePage(page, musicTypeInfo);
    }
}
