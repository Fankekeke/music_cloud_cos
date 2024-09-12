package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.UserInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 用户管理 service层
 *
 * @author FanK
 */
public interface IUserInfoService extends IService<UserInfo> {

    /**
     * 分页获取用户信息
     *
     * @param page     分页对象
     * @param userInfo 用户信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryUserPage(Page<UserInfo> page, UserInfo userInfo);
}
