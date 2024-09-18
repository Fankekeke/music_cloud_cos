package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.EvaluateInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 音乐评价 service层
 *
 * @author FanK
 */
public interface IEvaluateInfoService extends IService<EvaluateInfo> {

    /**
     * 分页获取音乐评价信息
     *
     * @param page         分页对象
     * @param evaluateInfo 音乐评价信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryEvaluatePage(Page<EvaluateInfo> page, EvaluateInfo evaluateInfo);

    /**
     * 根据音乐ID获取评价信息
     *
     * @param musicId 音乐ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> queryEvaluateByMusic(Integer musicId);

    /**
     * 根据用户ID获取评价信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> queryEvaluateByUser(Integer userId);
}
