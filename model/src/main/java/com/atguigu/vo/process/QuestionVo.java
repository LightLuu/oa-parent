package com.atguigu.vo.process;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
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
 * @since 2023-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QuestionVo implements Serializable {

    @ApiModelProperty(value = "问卷模板id")
    private Long processTemplateId;

    @ApiModelProperty(value = "表单值")
    private String formValues;
}
