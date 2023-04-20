package com.atguigu.model.file;

import com.atguigu.model.system.SysMenu;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author afraid
 * @since 2023-04-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 文件id
     */
    private Long fileId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除标记（0:不可用 1:可用）
     */
    private Integer isDeleted;

    /**
     * 用户名
     */
    private String username;

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 文件描述
     */
    private String description;

    /**
     * 文件key
     */
    private String osskeys;

    /**
     * 文件类型
     */
    private String types;
    // 下级列表
    @TableField(exist = false)
    private List<SysFile> children;
}
