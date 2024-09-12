package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.LyricsInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 歌词管理 mapper层
 *
 * @author FanK
 */
public interface LyricsInfoMapper extends BaseMapper<LyricsInfo> {

    /**
     * 分页获取歌词信息
     *
     * @param page         分页对象
     * @param lyricsInfo 歌词信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryLyricsPage(Page<LyricsInfo> page, @Param("lyricsInfo") LyricsInfo lyricsInfo);
}
