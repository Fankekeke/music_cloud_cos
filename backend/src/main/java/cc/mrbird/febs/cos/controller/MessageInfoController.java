package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.MessageInfo;
import cc.mrbird.febs.cos.service.IMessageInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 用户消息 控制层
 *
 * @author FanK
 */
@RestController
@RequestMapping("/cos/message-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageInfoController {

    private final IMessageInfoService messageInfoService;

    /**
     * 分页获取用户消息信息
     *
     * @param page        分页对象
     * @param messageInfo 用户消息信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<MessageInfo> page, MessageInfo messageInfo) {
        return R.ok(messageInfoService.queryMessagePage(page, messageInfo));
    }

    /**
     * 获取用户消息
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/selectMessageByUser")
    public R selectMessageByUser(@RequestParam("userId") Integer userId) {
        return R.ok(messageInfoService.list(Wrappers.<MessageInfo>lambdaQuery().eq(MessageInfo::getUserId, userId)));
    }

    /**
     * 查询用户消息信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(messageInfoService.getById(id));
    }

    /**
     * 查询用户消息信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(messageInfoService.list());
    }

    /**
     * 新增用户消息信息
     *
     * @param messageInfo 用户消息信息
     * @return 结果
     */
    @PostMapping
    public R save(MessageInfo messageInfo) {
        messageInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(messageInfoService.save(messageInfo));
    }

    /**
     * 修改用户消息信息
     *
     * @param messageInfo 用户消息信息
     * @return 结果
     */
    @PutMapping
    public R edit(MessageInfo messageInfo) {
        return R.ok(messageInfoService.updateById(messageInfo));
    }

    /**
     * 删除用户消息信息
     *
     * @param ids ids
     * @return 用户消息信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(messageInfoService.removeByIds(ids));
    }
}
