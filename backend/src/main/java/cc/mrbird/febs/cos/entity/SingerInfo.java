package cc.mrbird.febs.cos.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 歌手信息
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SingerInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 编号
     */
    private String code;

    /**
     * 歌手名称
     */
    private String name;

    /**
     * 姓名（1.男 2.女）
     */
    private String sex;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 头像
     */
    private String images;

    /**
     * 身份
     */
    private String identity;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private String createDate;

    @TableField(exist = false)
    private Integer fans;
}
