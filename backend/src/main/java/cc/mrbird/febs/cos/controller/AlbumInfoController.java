package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.AlbumInfo;
import cc.mrbird.febs.cos.service.IAlbumInfoService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 专辑管理 控制层
 *
 * @author FanK
 */
@RestController
@RequestMapping("/cos/album-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AlbumInfoController {

    private final IAlbumInfoService albumInfoService;

    /**
     * 分页获取专辑信息
     *
     * @param page      分页对象
     * @param albumInfo 专辑信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<AlbumInfo> page, AlbumInfo albumInfo) {
        return R.ok(albumInfoService.queryAlbumPage(page, albumInfo));
    }

    /**
     * 获取歌手专辑信息
     *
     * @param singerId 歌手ID
     * @return 结果
     */
    @GetMapping("/selectAlbumBySinger")
    public R selectAlbumBySinger(@RequestParam("singerId") Integer singerId) {
        return R.ok(albumInfoService.list(Wrappers.<AlbumInfo>lambdaQuery().eq(AlbumInfo::getSingerId, singerId)));
    }

    /**
     * 查询专辑信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(albumInfoService.getById(id));
    }

    /**
     * 查询专辑信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(albumInfoService.list());
    }

    /**
     * 新增专辑信息
     *
     * @param albumInfo 专辑信息
     * @return 结果
     */
    @PostMapping
    public R save(AlbumInfo albumInfo) {
        albumInfo.setCode("ABM-" + System.currentTimeMillis());
        albumInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(albumInfoService.albumAdd(albumInfo));
    }

    /**
     * 修改专辑信息
     *
     * @param albumInfo 专辑信息
     * @return 结果
     */
    @PutMapping
    public R edit(AlbumInfo albumInfo) {
        return R.ok(albumInfoService.updateById(albumInfo));
    }

    /**
     * 删除专辑信息
     *
     * @param ids ids
     * @return 专辑信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(albumInfoService.removeByIds(ids));
    }
}
