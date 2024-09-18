package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户消息
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MessageInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String contnet;

    /**
     * 状态（0.未读 1.已读）
     */
    private String status;

    /**
     * 创建时间
     */
    private String createDate;

    public MessageInfo(Integer userId, String title, String contnet, String status, String createDate) {
        this.userId = userId;
        this.title = title;
        this.contnet = contnet;
        this.status = status;
        this.createDate = createDate;
    }

    public MessageInfo(){}
}
