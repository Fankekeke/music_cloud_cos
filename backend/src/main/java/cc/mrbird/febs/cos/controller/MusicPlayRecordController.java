package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.MusicPlayRecord;
import cc.mrbird.febs.cos.service.IMusicPlayRecordService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 音乐播放记录 控制层
 *
 * @author FanK
 */
@RestController
@RequestMapping("/cos/music-play-record")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MusicPlayRecordController {

    private final IMusicPlayRecordService musicPlayRecordService;

    /**
     * 分页获取音乐信息
     *
     * @param page      分页对象
     * @param musicPlayRecord 音乐信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<MusicPlayRecord> page, MusicPlayRecord musicPlayRecord) {
        return R.ok(musicPlayRecordService.queryPlayRecordPage(page, musicPlayRecord));
    }

    /**
     * 获取用户播放记录
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/selectRecordByUser")
    public R selectRecordByUser(@RequestParam("userId") Integer userId) {
        return R.ok(musicPlayRecordService.selectRecordByUser(userId));
    }

    /**
     * 查询音乐信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(musicPlayRecordService.getById(id));
    }

    /**
     * 查询音乐信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(musicPlayRecordService.list());
    }

    /**
     * 新增音乐信息
     *
     * @param musicPlayRecord 音乐信息
     * @return 结果
     */
    @PostMapping
    public R save(MusicPlayRecord musicPlayRecord) {
        musicPlayRecord.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(musicPlayRecordService.save(musicPlayRecord));
    }

    /**
     * 修改音乐信息
     *
     * @param musicPlayRecord 音乐信息
     * @return 结果
     */
    @PutMapping
    public R edit(MusicPlayRecord musicPlayRecord) {
        return R.ok(musicPlayRecordService.updateById(musicPlayRecord));
    }

    /**
     * 删除音乐信息
     *
     * @param ids ids
     * @return 音乐信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(musicPlayRecordService.removeByIds(ids));
    }
}
