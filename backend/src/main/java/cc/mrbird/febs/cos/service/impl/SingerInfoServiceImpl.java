package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.dao.*;
import cc.mrbird.febs.cos.entity.CollectInfo;
import cc.mrbird.febs.cos.entity.SingerInfo;
import cc.mrbird.febs.cos.entity.SubscriptionInfo;
import cc.mrbird.febs.cos.service.ISingerInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;

/**
 * 歌手信息 实现层
 *
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SingerInfoServiceImpl extends ServiceImpl<SingerInfoMapper, SingerInfo> implements ISingerInfoService {

    private final MusicInfoMapper musicInfoMapper;

    private final AlbumInfoMapper albumInfoMapper;

    private final SubscriptionInfoMapper subscriptionInfoMapper;

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

    /**
     * 获取歌手详细信息
     *
     * @param singerId 歌手ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> querySingerDetail(Integer singerId) {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("singer", null);
                put("music", musicInfoMapper.selectMusicBySinger(singerId));
                put("album", albumInfoMapper.queryAlbumBySingerList(singerId));
            }
        };

        SingerInfo singerInfo = this.getById(singerId);
        // 获取订阅数量
        singerInfo.setFans(subscriptionInfoMapper.selectCount(Wrappers.<SubscriptionInfo>lambdaQuery().eq(SubscriptionInfo::getSingerId, singerId)));
        result.put("singer", singerInfo);
        return result;
    }
}
