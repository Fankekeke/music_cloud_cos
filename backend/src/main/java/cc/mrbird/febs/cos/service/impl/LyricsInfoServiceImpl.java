package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.LyricsInfo;
import cc.mrbird.febs.cos.dao.LyricsInfoMapper;
import cc.mrbird.febs.cos.service.ILyricsInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 歌词管理 实现层
 *
 * @author FanK
 */
@Service
public class LyricsInfoServiceImpl extends ServiceImpl<LyricsInfoMapper, LyricsInfo> implements ILyricsInfoService {

    /**
     * 分页获取歌词信息
     *
     * @param page       分页对象
     * @param lyricsInfo 歌词信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryLyricsPage(Page<LyricsInfo> page, LyricsInfo lyricsInfo) {
        return baseMapper.queryLyricsPage(page, lyricsInfo);
    }
}
