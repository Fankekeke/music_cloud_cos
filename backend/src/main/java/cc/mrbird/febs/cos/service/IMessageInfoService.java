package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.MessageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 用户消息 service层
 *
 * @author FanK
 */
public interface IMessageInfoService extends IService<MessageInfo> {

    /**
     * 分页获取用户消息信息
     *
     * @param page        分页对象
     * @param messageInfo 用户消息信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryMessagePage(Page<MessageInfo> page, MessageInfo messageInfo);
}
