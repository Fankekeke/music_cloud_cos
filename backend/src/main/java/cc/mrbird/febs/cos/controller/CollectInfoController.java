package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.CollectInfo;
import cc.mrbird.febs.cos.service.ICollectInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 用户音乐收藏 控制层
 *
 * @author FanK
 */
@RestController
@RequestMapping("/cos/collect-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CollectInfoController {

    private final ICollectInfoService collectInfoService;

    /**
     * 分页获取用户音乐收藏信息
     *
     * @param page        分页对象
     * @param collectInfo 用户音乐收藏信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<CollectInfo> page, CollectInfo collectInfo) {
        return R.ok(collectInfoService.queryCollectPage(page, collectInfo));
    }

    /**
     * 删除用户音乐收藏信息
     *
     * @param collectInfo 用户音乐收藏信息
     * @return 结果
     */
    @GetMapping("/removeLike")
    public R removeLike(CollectInfo collectInfo) {
        return R.ok(collectInfoService.remove(Wrappers.<CollectInfo>lambdaQuery().eq(CollectInfo::getUserId, collectInfo.getUserId())
                .eq(CollectInfo::getMusicId, collectInfo.getMusicId())));
    }

    /**
     * 获取用户收藏歌曲
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/selectCollectByUserId")
    public R selectCollectByUserId(@RequestParam("userId") Integer userId) {
        return R.ok(collectInfoService.queryCollectByUserId(userId));
    }

    /**
     * 查询用户音乐收藏信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(collectInfoService.getById(id));
    }

    /**
     * 查询用户音乐收藏信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(collectInfoService.list());
    }

    /**
     * 新增用户音乐收藏信息
     *
     * @param collectInfo 用户音乐收藏信息
     * @return 结果
     */
    @PostMapping
    public R save(CollectInfo collectInfo) {
        collectInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(collectInfoService.save(collectInfo));
    }

    /**
     * 修改用户音乐收藏信息
     *
     * @param collectInfo 用户音乐收藏信息
     * @return 结果
     */
    @PutMapping
    public R edit(CollectInfo collectInfo) {
        return R.ok(collectInfoService.updateById(collectInfo));
    }

    /**
     * 删除用户音乐收藏信息
     *
     * @param ids ids
     * @return 用户音乐收藏信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(collectInfoService.removeByIds(ids));
    }
}
