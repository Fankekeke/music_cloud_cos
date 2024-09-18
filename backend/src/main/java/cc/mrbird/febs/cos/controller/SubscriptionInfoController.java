package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.SubscriptionInfo;
import cc.mrbird.febs.cos.service.ISubscriptionInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 歌手关注 控制层
 *
 * @author FanK
 */
@RestController
@RequestMapping("/cos/subscription-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SubscriptionInfoController {

    private final ISubscriptionInfoService subscriptionInfoService;

    /**
     * 分页获取歌手关注信息
     *
     * @param page             分页对象
     * @param subscriptionInfo 歌手关注信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<SubscriptionInfo> page, SubscriptionInfo subscriptionInfo) {
        return R.ok(subscriptionInfoService.querySubPage(page, subscriptionInfo));
    }

    /**
     * 查询歌手关注信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(subscriptionInfoService.getById(id));
    }

    /**
     * 根据用户获取关注歌手
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/selectSubByUser")
    public R selectSubByUser(@RequestParam("userId") Integer userId) {
        return R.ok(subscriptionInfoService.selectSubByUser(userId));
    }

    /**
     * 查询歌手关注信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(subscriptionInfoService.list());
    }

    /**
     * 新增歌手关注信息
     *
     * @param subscriptionInfo 歌手关注信息
     * @return 结果
     */
    @PostMapping
    public R save(SubscriptionInfo subscriptionInfo) {
        subscriptionInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(subscriptionInfoService.save(subscriptionInfo));
    }

    /**
     * 修改歌手关注信息
     *
     * @param subscriptionInfo 歌手关注信息
     * @return 结果
     */
    @PutMapping
    public R edit(SubscriptionInfo subscriptionInfo) {
        return R.ok(subscriptionInfoService.updateById(subscriptionInfo));
    }

    /**
     * 删除歌手关注信息
     *
     * @param ids ids
     * @return 歌手关注信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(subscriptionInfoService.removeByIds(ids));
    }
}
