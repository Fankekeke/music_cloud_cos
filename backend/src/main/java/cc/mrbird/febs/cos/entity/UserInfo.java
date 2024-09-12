package cc.mrbird.febs.cos.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户管理
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户编号
     */
    private String code;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 性别（1.男 2.女）
     */
    private String sex;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 备注
     */
    private String remark;

    /**
     * 头像
     */
    private String images;

    /**
     * 创建时间
     */
    private String createDate;


}
