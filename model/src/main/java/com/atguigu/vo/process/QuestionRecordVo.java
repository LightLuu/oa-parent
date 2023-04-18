package com.atguigu.vo.process;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

@Data
public class QuestionRecordVo {
    private Long id;

    /**
     * 问卷id
     */
    private Long processId;

    /**
     * 问卷名称
     */
    private String name;

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
