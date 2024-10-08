package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.MusicInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 音乐管理 service层
 *
 * @author FanK
 */
public interface IMusicInfoService extends IService<MusicInfo> {

    /**
     * 分页获取音乐信息
     *
     * @param page      分页对象
     * @param musicInfo 音乐信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryMusicPage(Page<MusicInfo> page, MusicInfo musicInfo);

    /**
     * 首页统计信息
     *
     * @return 结果
     */
    LinkedHashMap<String, Object> homeData();

    /**
     * 用户搜索
     *
     * @param key 参数
     * @return 结果
     */
    LinkedHashMap<String, Object> selSearchDetailList(String key);

    /**
     * 根据专辑获取收录歌曲
     *
     * @param albumId 专辑ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectMusicByAlbum(Integer albumId);

    /**
     * 根据歌手获取收录歌曲
     *
     * @param singerId 歌手ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectMusicBySinger(Integer singerId);

    /**
     * 获取歌曲详情
     *
     * @param musicId 歌曲ID
     * @return 结果
     */
    LinkedHashMap<String, Object> queryMusicDetail(Integer musicId);

    /**
     * 新增音乐信息
     *
     * @param musicInfo 音乐信息
     * @return 结果
     */
    Boolean musicAdd(MusicInfo musicInfo);
}
