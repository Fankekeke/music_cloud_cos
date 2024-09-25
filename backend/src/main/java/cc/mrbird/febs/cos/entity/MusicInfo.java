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
 * 音乐管理
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MusicInfo implements Serializable {

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
     * 歌曲名
     */
    private String name;

    /**
     * 总时常秒
     */
    private Integer totalTime;

    /**
     * 所属专辑
     */
    private Integer albumId;

    /**
     * 封面
     */
    private String images;

    /**
     * 备注
     */
    private String content;

    /**
     * 歌手信息
     */
    private Integer singerId;

    /**
     * 文件地址
     */
    private String fileUrl;

    /**
     * 歌曲标签
     */
    private String tag;

    /**
     * 歌曲类型
     */
    private Integer typeId;

    /**
     * 创建时间
     */
    private String createDate;

    @TableField(exist = false)
    private String singerName;

    @TableField(exist = false)
    private String albumName;

    @TableField(exist = false)
    private Integer collectNum;
}
