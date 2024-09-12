package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.CollectInfo;
import cc.mrbird.febs.cos.dao.CollectInfoMapper;
import cc.mrbird.febs.cos.service.ICollectInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 用户音乐收藏 实现层
 *
 * @author FanK
 */
@Service
public class CollectInfoServiceImpl extends ServiceImpl<CollectInfoMapper, CollectInfo> implements ICollectInfoService {

    /**
     * 分页获取用户音乐收藏信息
     *
     * @param page        分页对象
     * @param collectInfo 用户音乐收藏信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryCollectPage(Page<CollectInfo> page, CollectInfo collectInfo) {
        return baseMapper.queryCollectPage(page, collectInfo);
    }
}
