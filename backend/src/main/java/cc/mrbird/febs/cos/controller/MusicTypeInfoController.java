package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.MusicTypeInfo;
import cc.mrbird.febs.cos.service.IMusicTypeInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 歌曲类型 控制层
 *
 * @author FanK
 */
@RestController
@RequestMapping("/cos/music-type-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MusicTypeInfoController {

    private final IMusicTypeInfoService musicTypeInfoService;

    /**
     * 分页获取歌曲类型信息
     *
     * @param page          分页对象
     * @param musicTypeInfo 歌曲类型信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<MusicTypeInfo> page, MusicTypeInfo musicTypeInfo) {
        return R.ok(musicTypeInfoService.queryMusicTypePage(page, musicTypeInfo));
    }

    /**
     * 查询歌曲类型信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(musicTypeInfoService.getById(id));
    }

    /**
     * 查询歌曲类型信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(musicTypeInfoService.list());
    }

    /**
     * 新增歌曲类型信息
     *
     * @param musicTypeInfo 歌曲类型信息
     * @return 结果
     */
    @PostMapping
    public R save(MusicTypeInfo musicTypeInfo) {
        musicTypeInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(musicTypeInfoService.save(musicTypeInfo));
    }

    /**
     * 修改歌曲类型信息
     *
     * @param musicTypeInfo 歌曲类型信息
     * @return 结果
     */
    @PutMapping
    public R edit(MusicTypeInfo musicTypeInfo) {
        return R.ok(musicTypeInfoService.updateById(musicTypeInfo));
    }

    /**
     * 删除歌曲类型信息
     *
     * @param ids ids
     * @return 歌曲类型信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(musicTypeInfoService.removeByIds(ids));
    }
}
