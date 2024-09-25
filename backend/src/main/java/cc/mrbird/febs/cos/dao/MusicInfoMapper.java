package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.MusicInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

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

    /**
     * 获取音乐信息
     *
     * @param key 关键字
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> queryMusicList(@Param("key") String key);

    /**
     * 获取首页音乐信息
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> queryHomeMusicList();

    /**
     * 十天内访问记录统计
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectOrderViewWithinDays();

    /**
     * 根据专辑获取收录歌曲
     *
     * @param albumId 专辑ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectMusicByAlbum(@Param("albumId") Integer albumId);

    /**
     * 根据歌手获取收录歌曲
     *
     * @param singerId 歌手ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectMusicBySinger(@Param("singerId") Integer singerId);
}
