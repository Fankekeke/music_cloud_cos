package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.SingerInfo;
import cc.mrbird.febs.cos.service.ISingerInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 歌手信息 控制层
 *
 * @author FanK
 */
@RestController
@RequestMapping("/cos/singer-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SingerInfoController {

    private final ISingerInfoService singerInfoService;

    /**
     * 分页获取歌手信息信息
     *
     * @param page       分页对象
     * @param singerInfo 歌手信息信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<SingerInfo> page, SingerInfo singerInfo) {
        return R.ok(singerInfoService.querySingerPage(page, singerInfo));
    }

    /**
     * 查询歌手信息信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(singerInfoService.getById(id));
    }

    /**
     * 查询歌手信息信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(singerInfoService.list());
    }

    /**
     * 新增歌手信息信息
     *
     * @param singerInfo 歌手信息信息
     * @return 结果
     */
    @PostMapping
    public R save(SingerInfo singerInfo) {
        singerInfo.setCode("SG-" + System.currentTimeMillis());
        singerInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(singerInfoService.save(singerInfo));
    }

    /**
     * 修改歌手信息信息
     *
     * @param singerInfo 歌手信息信息
     * @return 结果
     */
    @PutMapping
    public R edit(SingerInfo singerInfo) {
        return R.ok(singerInfoService.updateById(singerInfo));
    }

    /**
     * 删除歌手信息信息
     *
     * @param ids ids
     * @return 歌手信息信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(singerInfoService.removeByIds(ids));
    }
}
