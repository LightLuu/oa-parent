package com.atguigu.process.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.atguigu.auth.service.SysUserService;
import com.atguigu.model.process.Process;
import com.atguigu.model.process.ProcessTemplate;
import com.atguigu.model.process.Question;
import com.atguigu.model.process.QuestionRecord;
import com.atguigu.model.system.SysUser;
import com.atguigu.process.mapper.QuestionMapper;
import com.atguigu.process.service.QuestionService;
import com.atguigu.security.custom.LoginUserInfoHelper;
import com.atguigu.vo.process.ProcessVo;
import com.atguigu.vo.process.QuestionVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author afraid
 * @since 2023-04-12
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

   @Autowired
   private SysUserService sysUserService;


   @Autowired
   private QuestionService questionService;

    /**
     * 发布问卷的目的
     * 将问卷模板上传到微信端
     * 查询拥有所有openid的用户
     * 然后将问卷链接发布给所有微信用户
     * @param id
     */
    public void publish(Long id){
        //1.将模板制作成微信端的id
        //把问卷模板状态转为1 即已发布
        Question question = questionService.getById(id);
        //把问卷模板状态转为1 即已发布
        question.setStatus(1);
        //将选中的角色id问卷id在question_role中插入
        //现在做插入功能
            /**如果是查询，那么如果是admin就全部返回
                            根据userid查询表
                            根据role_question查询问卷id,然后根据问卷id查询问卷
                            中间如果出现重复要跳过
             */
        questionService.updateById(question);

    }

    public IPage<Question>  selectPage2(Page<Question> pageParam, Long userId){
       IPage<Question> pageModel = baseMapper.selectPage2(pageParam,userId);
        return pageModel;
        //return null;
    }

}
