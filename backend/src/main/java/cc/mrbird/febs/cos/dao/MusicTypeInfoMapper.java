package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.MusicTypeInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 歌曲类型 mapper层
 *
 * @author FanK
 */
public interface MusicTypeInfoMapper extends BaseMapper<MusicTypeInfo> {

    /**
     * 分页获取歌曲类型信息
     *
     * @param page          分页对象
     * @param musicTypeInfo 歌曲类型信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryMusicTypePage(Page<MusicTypeInfo> page, @Param("musicTypeInfo") MusicTypeInfo musicTypeInfo);
}
