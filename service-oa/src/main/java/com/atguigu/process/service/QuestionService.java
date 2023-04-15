package com.atguigu.process.service;

import com.atguigu.model.process.Question;
import com.atguigu.vo.process.QuestionVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author afraid
 * @since 2023-04-12
 */
public interface QuestionService extends IService<Question> {

    /**
     * 发布问卷的目的
     * @param id
     */
    void publish(Long id);

    /**
     * 查询所属角色的角色id
     * @param userId
     */
    IPage<Question> selectPage2(Page<Question> pageParam,Long userId);

}
