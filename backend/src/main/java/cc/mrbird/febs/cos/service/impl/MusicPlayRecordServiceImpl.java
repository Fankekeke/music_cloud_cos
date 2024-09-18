package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.MusicInfo;
import cc.mrbird.febs.cos.entity.MusicPlayRecord;
import cc.mrbird.febs.cos.dao.MusicPlayRecordMapper;
import cc.mrbird.febs.cos.service.IMusicPlayRecordService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 音乐播放记录 实现层
 *
 * @author FanK
 */
@Service
public class MusicPlayRecordServiceImpl extends ServiceImpl<MusicPlayRecordMapper, MusicPlayRecord> implements IMusicPlayRecordService {

    /**
     * 分页获取音乐信息
     *
     * @param page      分页对象
     * @param musicInfo 音乐信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPlayRecordPage(Page<MusicPlayRecord> page, MusicPlayRecord musicInfo) {
        return baseMapper.queryPlayRecordPage(page, musicInfo);
    }

    /**
     * 获取用户播放记录
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectRecordByUser(Integer userId) {
        return baseMapper.selectRecordByUser(userId);
    }
}
