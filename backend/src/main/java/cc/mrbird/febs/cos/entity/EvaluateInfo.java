package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 音乐评价
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EvaluateInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 所属音乐
     */
    private Integer musicId;

    /**
     * 评价用户
     */
    private Integer userId;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 评价分数
     */
    private Integer score;

    /**
     * 创建时间
     */
    private String createDate;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String musicName;

    @TableField(exist = false)
    private String singerName;

    @TableField(exist = false)
    private String albumName;
}
