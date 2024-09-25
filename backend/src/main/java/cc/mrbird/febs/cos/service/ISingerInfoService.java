package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.SingerInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 歌手信息 service层
 *
 * @author FanK
 */
public interface ISingerInfoService extends IService<SingerInfo> {

    /**
     * 分页获取歌手信息信息
     *
     * @param page       分页对象
     * @param singerInfo 歌手信息信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> querySingerPage(Page<SingerInfo> page, SingerInfo singerInfo);

    /**
     * 获取歌手详细信息
     *
     * @param singerId 歌手ID
     * @return 结果
     */
    LinkedHashMap<String, Object> querySingerDetail(Integer singerId);
}
