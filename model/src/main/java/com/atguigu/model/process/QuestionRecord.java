package com.atguigu.model.process;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author afraid
 * @since 2023-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QuestionRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 问卷id
     */
    private Long processId;

    /**
     * 问卷描述
     */
    private String description;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 操作用户id
     */
    private Long operateUserId;

    /**
     * 操作用户
     */
    private String operateUser;

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
     * 表单值
     */
    private String formValues;


}
