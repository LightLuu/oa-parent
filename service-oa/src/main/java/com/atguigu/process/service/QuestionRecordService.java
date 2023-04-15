package com.atguigu.process.service;


import com.atguigu.model.process.QuestionRecord;
import com.atguigu.vo.process.QuestionVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author afraid
 * @since 2023-04-13
 */
public interface QuestionRecordService extends IService<QuestionRecord> {

    /**
     * 提交表单
     * @param questionVo
     */
    void startUp(QuestionVo questionVo);
}
