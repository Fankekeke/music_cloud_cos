package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.EvaluateInfo;
import cc.mrbird.febs.cos.service.IEvaluateInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 音乐评价 控制层
 *
 * @author FanK
 */
@RestController
@RequestMapping("/cos/evaluate-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EvaluateInfoController {

    private final IEvaluateInfoService evaluateInfoService;

    /**
     * 分页获取音乐评价信息
     *
     * @param page         分页对象
     * @param evaluateInfo 音乐评价信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<EvaluateInfo> page, EvaluateInfo evaluateInfo) {
        return R.ok(evaluateInfoService.queryEvaluatePage(page, evaluateInfo));
    }

    /**
     * 根据音乐ID获取评价信息
     *
     * @param musicId 音乐ID
     * @return 结果
     */
    @GetMapping("/selectEvaluateByMusic")
    public R selectEvaluateByMusic(@RequestParam("musicId") Integer musicId) {
        return R.ok(evaluateInfoService.queryEvaluateByMusic(musicId));
    }

    /**
     * 根据用户ID获取评价信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/selectEvaluateByUser")
    public R selectEvaluateByUser(@RequestParam("userId") Integer userId) {
        return R.ok(evaluateInfoService.queryEvaluateByUser(userId));
    }

    /**
     * 查询音乐评价信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(evaluateInfoService.getById(id));
    }

    /**
     * 查询音乐评价信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(evaluateInfoService.list());
    }

    /**
     * 新增音乐评价信息
     *
     * @param evaluateInfo 音乐评价信息
     * @return 结果
     */
    @PostMapping
    public R save(EvaluateInfo evaluateInfo) {
        evaluateInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(evaluateInfoService.save(evaluateInfo));
    }

    /**
     * 修改音乐评价信息
     *
     * @param evaluateInfo 音乐评价信息
     * @return 结果
     */
    @PutMapping
    public R edit(EvaluateInfo evaluateInfo) {
        return R.ok(evaluateInfoService.updateById(evaluateInfo));
    }

    /**
     * 删除音乐评价信息
     *
     * @param ids ids
     * @return 音乐评价信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(evaluateInfoService.removeByIds(ids));
    }
}
