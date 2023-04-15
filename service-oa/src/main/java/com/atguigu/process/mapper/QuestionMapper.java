package com.atguigu.process.mapper;

import com.atguigu.model.process.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author afraid
 * @since 2023-04-12
 */
public interface QuestionMapper extends BaseMapper<Question> {
    //可查看问卷列表
    IPage<Question> selectPage2(Page<Question> pageParam, @Param("userId") Long userId);

}
