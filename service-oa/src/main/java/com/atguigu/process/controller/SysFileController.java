package com.atguigu.process.controller;


import com.atguigu.common.result.Result;
import com.atguigu.model.process.Question;
import com.atguigu.process.service.SysFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author afraid
 * @since 2023-04-18
 */
@RestController
@RequestMapping("/process/sys-file")
@CrossOrigin //跨域:访问协议 ip地址 端口号一个不一样就会产生跨域
public class SysFileController {

    @Autowired
    private SysFileService sysFileService;
    //获得上传oss的sts
    @GetMapping("getkey")
    public Result findAll() {
        Map<String,String> map= sysFileService.getkey();
        return Result.ok(map);
    }
}

