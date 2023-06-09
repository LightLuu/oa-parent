package com.atguigu.process.controller;


import com.atguigu.common.result.Result;
import com.atguigu.model.file.SysFile;
import com.atguigu.model.process.Question;
import com.atguigu.process.service.SysFileService;
import com.atguigu.process.service.impl.FileUploadService;
import com.atguigu.security.custom.LoginUserInfoHelper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
@Api(tags = "文件管理")
@RestController
@RequestMapping("/process/sys-file")
@CrossOrigin //跨域:访问协议 ip地址 端口号一个不一样就会产生跨域
public class SysFileController {

    @Autowired
    private SysFileService sysFileService;

    @Autowired
    private FileUploadService fileUploadService;
    //获得上传oss的sts
    @GetMapping("getkey")
    public Result getkey() {
        Map<String,String> map= sysFileService.getkey();
        return Result.ok(map);
    }

    //获得该用户的所有文件及分类
    @GetMapping("findNodes")
    public Result findNodes(){
        List<SysFile> list = sysFileService.findNodes();
        return Result.ok(list);
    }

    //查询所有上传文件
    @GetMapping("findAll")
    public Result findAll() {
        List<SysFile> list = sysFileService.list();
        return Result.ok(list);
    }

    //审批类型得分页查询
    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(@PathVariable Long page,
                        @PathVariable Long limit) {
        //添加条件，查询自已创建的模板id
        //如果是管理员，展现所有的问卷
        Page<SysFile> pageParam = new Page<>(page,limit);
        System.out.println(LoginUserInfoHelper.getUserId());
        if(LoginUserInfoHelper.getUserId() == 1L){
            IPage<SysFile> pageModel = sysFileService.page(pageParam);
            return Result.ok(pageModel);
        }
        //如果不是管理员，获取
        // 根据用户id查看当前当前用户的角色id
        //IPage<Question> pageModel = sysFileService.selectPage2(pageParam,LoginUserInfoHelper.getUserId());
        //System.out.println(pageModel);
        LambdaQueryWrapper<SysFile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysFile::getUserId,LoginUserInfoHelper.getUserId()).orderByDesc();
        IPage<SysFile> pageModel = sysFileService.page(pageParam,wrapper);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        SysFile sysFile = sysFileService.getById(id);
        return Result.ok(sysFile);
    }

    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result save(@RequestBody SysFile sysFile) {
        //保存问卷模板
        System.out.println("正在保存oss文件id");
        sysFile.setUserId(LoginUserInfoHelper.getUserId());
        sysFileService.save(sysFile);
        return Result.ok();
    }

    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result updateById(@RequestBody SysFile sysFile) {
        sysFileService.updateById(sysFile);
        return Result.ok();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id,HttpServletResponse response) {
        //根据id 查到 osskeys
        sysFileService.removeById(id);
        return Result.ok();
    }

    //TODO 字节流上传至服务器
    @ApiOperation(value = "下载")
    @GetMapping("upload2")
    public void upload2(@RequestParam("osskeys") String osskeys, @RequestParam("filename") String filename, HttpServletResponse response) throws IOException {
        // 浏览器以附件形式下载
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("utf-8"), "ISO8859-1" ));
        //response.reset();
        response.setContentType("image/png");
        //response.setCharacterEncoding("utf-8");
        //response.setContentLength((int) file.length());
        //response.setHeader("Content-Disposition", "attachment;filename=" + filename );

        fileUploadService.exportOssFile(response.getOutputStream(), osskeys);
    }

    @ApiOperation(value = "下载url")
    @GetMapping("upload")
    public Result upload(@RequestParam("osskeys") String osskeys, @RequestParam("filename") String filename,@RequestParam("time") Integer time) throws IOException {
       return sysFileService.upload(osskeys,filename,time);
    }
}

