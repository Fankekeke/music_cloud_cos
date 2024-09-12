package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.MusicInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

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
}
