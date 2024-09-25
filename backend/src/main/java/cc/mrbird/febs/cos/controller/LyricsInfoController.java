package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.LyricsInfo;
import cc.mrbird.febs.cos.service.ILyricsInfoService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 歌词管理 控制层
 *
 * @author FanK
 */
@RestController
@RequestMapping("/cos/lyrics-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LyricsInfoController {

    private final ILyricsInfoService lyricsInfoService;

    /**
     * 分页获取歌词信息
     *
     * @param page       分页对象
     * @param lyricsInfo 歌词信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<LyricsInfo> page, LyricsInfo lyricsInfo) {
        return R.ok(lyricsInfoService.queryLyricsPage(page, lyricsInfo));
    }

    /**
     * 获取音乐歌词
     *
     * @param musicId 音乐ID
     * @return 结果
     */
    @GetMapping("/queryLyricsByMusic")
    public R queryLyricsByMusic(@RequestParam("musicId") Integer musicId) {
        return R.ok(lyricsInfoService.getOne(Wrappers.<LyricsInfo>lambdaQuery().eq(LyricsInfo::getMusicId, musicId)));
    }

    /**
     * 查询歌词信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(lyricsInfoService.getById(id));
    }

    /**
     * 查询歌词信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(lyricsInfoService.list());
    }

    /**
     * 新增歌词信息
     *
     * @param lyricsInfo 歌词信息
     * @return 结果
     */
    @PostMapping
    public R save(LyricsInfo lyricsInfo) throws FebsException {
        // 校验是否已经绑定
        int count = lyricsInfoService.count(Wrappers.<LyricsInfo>lambdaQuery().eq(LyricsInfo::getMusicId, lyricsInfo.getMusicId()));
        if (count > 0) {
            throw new FebsException("此条已经被绑定过了");
        }
        return R.ok(lyricsInfoService.save(lyricsInfo));
    }

    /**
     * 修改歌词信息
     *
     * @param lyricsInfo 歌词信息
     * @return 结果
     */
    @PutMapping
    public R edit(LyricsInfo lyricsInfo) throws FebsException {
        // 校验是否已经绑定
        List<LyricsInfo> count = lyricsInfoService.list(Wrappers.<LyricsInfo>lambdaQuery().eq(LyricsInfo::getMusicId, lyricsInfo.getMusicId()));
        if (CollectionUtil.isNotEmpty(count) && !count.get(0).getId().equals(lyricsInfo.getId())) {
            throw new FebsException("此条已经被绑定过了");
        }
        return R.ok(lyricsInfoService.updateById(lyricsInfo));
    }

    /**
     * 删除歌词信息
     *
     * @param ids ids
     * @return 歌词信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(lyricsInfoService.removeByIds(ids));
    }
}
