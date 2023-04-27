package com.atguigu.process.controller;


import com.atguigu.auth.service.SysRoleService;
import com.atguigu.common.result.Result;
import com.atguigu.model.process.ProcessRecord;
import com.atguigu.model.process.ProcessType;
import com.atguigu.model.process.Question;
import com.atguigu.process.service.QuestionRecordService;
import com.atguigu.process.service.QuestionService;
import com.atguigu.security.custom.LoginUserInfoHelper;
import com.atguigu.vo.process.ProcessQueryVo;
import com.atguigu.vo.process.ProcessVo;
import com.atguigu.vo.process.QuestionRecordVo;
import com.atguigu.vo.system.AssginRoleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author afraid
 * @since 2023-04-12
 */
@Api(tags = "问卷管理")
@RestController
@RequestMapping("/process/question")
@CrossOrigin //跨域:访问协议 ip地址 端口号一个不一样就会产生跨域
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private QuestionRecordService questionRecordService;

    //查询所有审批分类
    @ApiOperation(value = "查询所有")
    @GetMapping("findAll")
    public Result findAll() {
        List<Question> list = questionService.list();
        return Result.ok(list);
    }

    //审批类型得分页查询
    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(@PathVariable Long page,
                        @PathVariable Long limit) {
        //添加条件，查询自已创建的模板id
        //如果是管理员，展现所有的问卷
        Page<Question> pageParam = new Page<>(page,limit);
        System.out.println(LoginUserInfoHelper.getUserId());
        if(LoginUserInfoHelper.getUserId() == 1L){
            IPage<Question> pageModel = questionService.page(pageParam);
            return Result.ok(pageModel);
        }
        //如果不是管理员，获取
          // 根据用户id查看当前当前用户的角色id

          //1.根据角色id获取问卷id
          //2.根据问卷id获取所有问卷

        IPage<Question> pageModel = questionService.selectPage2(pageParam,LoginUserInfoHelper.getUserId());
        //System.out.println(pageModel);
        LambdaQueryWrapper<Question> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Question::getUserId,LoginUserInfoHelper.getUserId()).orderByDesc();
        //IPage<Question> pageModel = questionService.page(pageParam,wrapper);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        Question question = questionService.getById(id);
        return Result.ok(question);
    }

    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result save(@RequestBody Question question) {
        //保存问卷模板
        System.out.println("正在保存问卷模板");
        question.setUserId(LoginUserInfoHelper.getUserId());
        questionService.save(question);
        return Result.ok();
    }

    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result updateById(@RequestBody Question question) {
        questionService.updateById(question);
        return Result.ok();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        questionService.removeById(id);
        return Result.ok();
    }

    //问卷发布，向用户表中的所有用户发布
    //发布的同时需要指定问卷状态为status为1 ，作为前端判断的依据
    @ApiOperation(value = "发布")
    @GetMapping("/publish/{id}")
    public Result publish(@PathVariable Long id) {
        //修改模板发布状态 1 已经发布
        //流程定义部署
        questionService.publish(id);
        return Result.ok();
    }

    //1 查询所有角色 和 当前问卷所属角色
    @ApiOperation("获取角色")
    @GetMapping("/toAssign/{userId}")
    public Result toAssign(@PathVariable Long userId) {
        Map<String,Object> map = sysRoleService.findRoleDataByQuestionId(userId);
        return Result.ok(map);
    }

    //2.为问卷分配角色
    @ApiOperation("为用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleVo assginRoleVo) {
        sysRoleService.doAssignByQuestionId(assginRoleVo);
        return Result.ok();
    }

}

