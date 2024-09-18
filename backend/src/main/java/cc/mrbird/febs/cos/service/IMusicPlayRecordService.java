package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.MusicInfo;
import cc.mrbird.febs.cos.entity.MusicPlayRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 音乐播放记录 service层
 *
 * @author FanK
 */
public interface IMusicPlayRecordService extends IService<MusicPlayRecord> {

    /**
     * 分页获取音乐信息
     *
     * @param page      分页对象
     * @param musicInfo 音乐信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPlayRecordPage(Page<MusicPlayRecord> page, MusicPlayRecord musicInfo);

    /**
     * 获取用户播放记录
     *
     * @param userId 用户ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectRecordByUser(@Param("userId") Integer userId);
}
