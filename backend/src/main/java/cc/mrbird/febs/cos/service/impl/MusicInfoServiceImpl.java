package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.MusicInfo;
import cc.mrbird.febs.cos.dao.MusicInfoMapper;
import cc.mrbird.febs.cos.service.IMusicInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 音乐管理 实现层
 *
 * @author FanK
 */
@Service
public class MusicInfoServiceImpl extends ServiceImpl<MusicInfoMapper, MusicInfo> implements IMusicInfoService {

    /**
     * 分页获取音乐信息
     *
     * @param page      分页对象
     * @param musicInfo 音乐信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryMusicPage(Page<MusicInfo> page, MusicInfo musicInfo) {
        return baseMapper.queryMusicPage(page, musicInfo);
    }
}
