package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.MusicInfo;
import cc.mrbird.febs.cos.service.IMusicInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 音乐管理 控制层
 *
 * @author FanK
 */
@RestController
@RequestMapping("/cos/music-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MusicInfoController {

    private final IMusicInfoService musicInfoService;

    /**
     * 分页获取音乐信息
     *
     * @param page      分页对象
     * @param musicInfo 音乐信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<MusicInfo> page, MusicInfo musicInfo) {
        return R.ok(musicInfoService.queryMusicPage(page, musicInfo));
    }

    /**
     * 根据专辑获取收录歌曲
     *
     * @param albumId 专辑ID
     * @return 结果
     */
    @GetMapping("/selectMusicByAlbum")
    public R selectMusicByAlbum(@RequestParam("albumId") Integer albumId) {
        return R.ok(musicInfoService.selectMusicByAlbum(albumId));
    }

    /**
     * 根据歌手获取收录歌曲
     *
     * @param singerId 歌手ID
     * @return 结果
     */
    @GetMapping("/selectMusicBySinger")
    public R selectMusicBySinger(@RequestParam("singerId") Integer singerId) {
        return R.ok(musicInfoService.selectMusicBySinger(singerId));
    }

    /**
     * 查询音乐信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(musicInfoService.getById(id));
    }

    /**
     * 查询音乐信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(musicInfoService.list());
    }

    /**
     * 新增音乐信息
     *
     * @param musicInfo 音乐信息
     * @return 结果
     */
    @PostMapping
    public R save(MusicInfo musicInfo) {
        musicInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(musicInfoService.musicAdd(musicInfo));
    }

    /**
     * 修改音乐信息
     *
     * @param musicInfo 音乐信息
     * @return 结果
     */
    @PutMapping
    public R edit(MusicInfo musicInfo) {
        return R.ok(musicInfoService.updateById(musicInfo));
    }

    /**
     * 删除音乐信息
     *
     * @param ids ids
     * @return 音乐信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(musicInfoService.removeByIds(ids));
    }
}
