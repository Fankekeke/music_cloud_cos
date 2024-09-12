package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.LyricsInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 歌词管理 service层
 *
 * @author FanK
 */
public interface ILyricsInfoService extends IService<LyricsInfo> {

    /**
     * 分页获取歌词信息
     *
     * @param page         分页对象
     * @param lyricsInfo 歌词信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryLyricsPage(Page<LyricsInfo> page, LyricsInfo lyricsInfo);
}
