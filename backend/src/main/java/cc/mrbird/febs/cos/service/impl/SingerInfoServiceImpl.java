package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.SingerInfo;
import cc.mrbird.febs.cos.dao.SingerInfoMapper;
import cc.mrbird.febs.cos.service.ISingerInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 歌手信息 实现层
 *
 * @author FanK
 */
@Service
public class SingerInfoServiceImpl extends ServiceImpl<SingerInfoMapper, SingerInfo> implements ISingerInfoService {

    /**
     * 分页获取歌手信息信息
     *
     * @param page       分页对象
     * @param singerInfo 歌手信息信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> querySingerPage(Page<SingerInfo> page, SingerInfo singerInfo) {
        return baseMapper.querySingerPage(page, singerInfo);
    }
}
