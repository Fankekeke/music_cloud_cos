package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.MessageInfo;
import cc.mrbird.febs.cos.dao.MessageInfoMapper;
import cc.mrbird.febs.cos.service.IMessageInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 用户消息 实现层
 *
 * @author FanK
 */
@Service
public class MessageInfoServiceImpl extends ServiceImpl<MessageInfoMapper, MessageInfo> implements IMessageInfoService {

    /**
     * 分页获取用户消息信息
     *
     * @param page        分页对象
     * @param messageInfo 用户消息信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryMessagePage(Page<MessageInfo> page, MessageInfo messageInfo) {
        return baseMapper.queryMessagePage(page, messageInfo);
    }
}
