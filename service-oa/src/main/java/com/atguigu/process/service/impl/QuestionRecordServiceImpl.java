package com.atguigu.process.service.impl;


import com.atguigu.auth.service.SysUserService;
import com.atguigu.model.process.QuestionRecord;
import com.atguigu.model.system.SysUser;
import com.atguigu.process.mapper.QuestionRecordMapper;
import com.atguigu.process.service.QuestionRecordService;
import com.atguigu.security.custom.LoginUserInfoHelper;
import com.atguigu.vo.process.QuestionVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author afraid
 * @since 2023-04-13
 */
@Service
public class QuestionRecordServiceImpl extends ServiceImpl<QuestionRecordMapper, QuestionRecord> implements QuestionRecordService {
    @Autowired
    SysUserService sysUserService;

    /**
     * 提交表单
     * @param questionVo
     */
    public void startUp(QuestionVo questionVo){
        //问卷id
        Long questionId = questionVo.getProcessTemplateId();
        //获得当前用户
        //1 根据当前用户id获取用户信息
        //用户信息
        SysUser sysUser = sysUserService.getById(LoginUserInfoHelper.getUserId());

       //新建一个问卷记录对象
        QuestionRecord questionRecord = new QuestionRecord();
        //问卷id
        questionRecord.setProcessId(questionId);
        //操作对象和操作对象id
        questionRecord.setOperateUser(sysUser.getName());
        questionRecord.setOperateUserId(sysUser.getId());
        //描述问卷已经填写
        questionRecord.setDescription(sysUser.getName()+"已填写");
        //状态已填写 1
        questionRecord.setStatus(1);
        //表单值
        questionRecord.setFormValues(questionVo.getFormValues());

        baseMapper.insert(questionRecord);
    }
}
