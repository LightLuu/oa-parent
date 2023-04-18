package com.atguigu.process.mapper;


import com.atguigu.model.process.QuestionRecord;
import com.atguigu.vo.process.ProcessQueryVo;
import com.atguigu.vo.process.ProcessVo;
import com.atguigu.vo.process.QuestionRecordVo;
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
 * @since 2023-04-13
 */
public interface QuestionRecordMapper extends BaseMapper<QuestionRecord> {

    //查询问卷列表
    IPage<QuestionRecordVo> selectPage(Page<QuestionRecordVo> pageParam, @Param("vo") ProcessQueryVo processQueryVo);
}
