package com.atguigu.process.controller;

import com.aliyun.oss.model.OSSObjectSummary;
import com.atguigu.common.oss.FileUploadResult;
import com.atguigu.common.result.Result;
import com.atguigu.model.file.SysFile;
import com.atguigu.process.service.impl.FileUploadService;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author 巅峰小词典
 * @description
 * @date 2021/5/20
 * @project springboot_oss
 */
@Api(tags = "文件上传")
@RestController
@RequestMapping(value = "/file")
@CrossOrigin //跨域:访问协议 ip地址 端口号一个不一样就会产生跨域
public class FileUploadController {

    @Resource
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    public FileUploadResult upload(@RequestParam("file") MultipartFile uploadFile) throws Exception {
        System.out.println("11111111111111111111111111111111111111111111111111111");
        //return this.fileUploadService.upload(uploadFile);
        return this.fileUploadService.upload(uploadFile);
    }

    @PostMapping("/ocrdemo")
    public Result ocrdemo(@RequestParam("file") MultipartFile uploadFile) throws Exception {
        //System.out.println("11111111111111111111111111111111111111111111111111111");
        //return this.fileUploadService.upload(uploadFile);
        return this.fileUploadService.ocrDemo(uploadFile);
    }

    //@RequestMapping("file/updata")
   // @GetMapping("{fileid}/{filename}/{des}")
    //@ResponseBody
    @GetMapping("/updata/{fileId}/{filename}/{des}/{type}")
    public FileUploadResult updata(@PathVariable String fileId,@PathVariable String filename,@PathVariable  String des,@PathVariable String type){
        System.out.println(1111);
        return this.fileUploadService.updata(fileId,filename,des,type);
    }

    @RequestMapping("file/delete")
    @ResponseBody
    public Result delete(@RequestParam("fileName") String objectName) throws Exception {
        return this.fileUploadService.delete(objectName);
    }

    @RequestMapping("file/list")
    @ResponseBody
    public List<OSSObjectSummary> list() throws Exception {
        return this.fileUploadService.list();
    }

    @RequestMapping("file/download")
    @ResponseBody
    public void download(@RequestParam("fileName") String objectName, HttpServletResponse response) throws IOException {
        // 浏览器以附件形式下载
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(objectName.getBytes(), "ISO-8859-1"));
        this.fileUploadService.exportOssFile(response.getOutputStream(), objectName);
    }

}