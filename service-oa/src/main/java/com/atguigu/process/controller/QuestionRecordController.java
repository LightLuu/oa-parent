package com.atguigu.process.controller;


import com.atguigu.common.result.Result;
import com.atguigu.process.service.QuestionRecordService;
import com.atguigu.process.service.QuestionService;
import com.atguigu.vo.process.ProcessFormVo;
import com.atguigu.vo.process.QuestionVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author afraid
 * @since 2023-04-13
 */
@RestController
@CrossOrigin //跨域:访问协议 ip地址 端口号一个不一样就会产生跨域
@RequestMapping("/process/question-record")
public class QuestionRecordController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRecordService questionRecordService;
    @ApiOperation(value = "启动流程")
    @PostMapping("/startUp")
    public Result startUp(@RequestBody QuestionVo questionVo) {
        questionRecordService.startUp(questionVo);
        return Result.ok();
    }
}

