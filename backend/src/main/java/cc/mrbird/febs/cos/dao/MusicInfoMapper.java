package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.MusicInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 音乐管理 mapper层
 *
 * @author FanK
 */
public interface MusicInfoMapper extends BaseMapper<MusicInfo> {

    /**
     * 分页获取音乐信息
     *
     * @param page      分页对象
     * @param musicInfo 音乐信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryMusicPage(Page<MusicInfo> page, @Param("musicInfo") MusicInfo musicInfo);
}