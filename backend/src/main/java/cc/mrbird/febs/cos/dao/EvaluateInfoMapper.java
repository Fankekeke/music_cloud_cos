package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.EvaluateInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 音乐评价 mapper层
 *
 * @author FanK
 */
public interface EvaluateInfoMapper extends BaseMapper<EvaluateInfo> {

    /**
     * 分页获取音乐评价信息
     *
     * @param page         分页对象
     * @param evaluateInfo 音乐评价信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryEvaluatePage(Page<EvaluateInfo> page, @Param("evaluateInfo") EvaluateInfo evaluateInfo);

    /**
     * 根据音乐ID获取评价信息
     *
     * @param musicId 音乐ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> queryEvaluateByMusic(@Param("musicId") Integer musicId);

    /**
     * 根据用户ID获取评价信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> queryEvaluateByUser(@Param("userId") Integer userId);
}
