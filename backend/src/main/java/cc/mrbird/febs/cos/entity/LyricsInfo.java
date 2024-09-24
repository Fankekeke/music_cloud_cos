package cc.mrbird.febs.cos.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 歌词管理
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LyricsInfo implements Serializable {

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
     * 时间
     */
    private Integer timeCheck;

    /**
     * 歌词
     */
    private String words;

    @TableField(exist = false)
    private String musicName;

    @TableField(exist = false)
    private String singerName;

    @TableField(exist = false)
    private String albumName;
}
