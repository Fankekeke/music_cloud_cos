package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.SingerInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 歌手信息 mapper层
 *
 * @author FanK
 */
public interface SingerInfoMapper extends BaseMapper<SingerInfo> {

    /**
     * 分页获取歌手信息信息
     *
     * @param page       分页对象
     * @param singerInfo 歌手信息信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> querySingerPage(Page<SingerInfo> page, @Param("singerInfo") SingerInfo singerInfo);
}
