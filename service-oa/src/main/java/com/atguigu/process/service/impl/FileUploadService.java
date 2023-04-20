package com.atguigu.process.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.atguigu.common.oss.AliyunConfig;
import com.atguigu.common.oss.FileUploadResult;
import com.atguigu.common.result.Result;
import com.atguigu.model.file.SysFile;
import com.atguigu.process.service.SysFileService;
import com.atguigu.security.custom.LoginUserInfoHelper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;

/**
 * @author 巅峰小词典
 * @description 使用ossClient操作阿里云OSS，进行上传、下载、删除、查看所有文件等操作，同时可以将图片的url进行入库操作。
 * @date 2021/5/20
 * @project springboot_oss
 */
@Service
public class FileUploadService {

    // 允许上传的格式
    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg", ".jpeg", ".gif", ".png"};

    @Resource
    private OSS ossClient;
    @Resource
    private AliyunConfig aliyunConfig;

    @Autowired
    private SysFileService sysFileService;
    /**
     * 文件上传
     *
     * @param uploadFile
     * @return
     */
    public FileUploadResult upload(MultipartFile uploadFile) {
        // 校验图片格式
        boolean isLegal = false;
        //获取文件名
        String originalFilename = uploadFile.getOriginalFilename();
        for (String type : IMAGE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(), type)) {
                isLegal = true;
                break;
            }
        }
        // 封装Result对象，并且将文件的byte数组放置到result对象中
        FileUploadResult fileUploadResult = new FileUploadResult();
        if (!isLegal) {
            fileUploadResult.setStatus("error");
            return fileUploadResult;
        }
        // 文件新路径
        String fileName = uploadFile.getOriginalFilename();
        String filePath = getFilePath(fileName);
        // 上传到阿里云
        try {
            ossClient.putObject(aliyunConfig.getBucketName(), filePath, new ByteArrayInputStream(uploadFile.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            // 上传失败
            fileUploadResult.setStatus("error");
            return fileUploadResult;
        }
        fileUploadResult.setStatus("done");
        fileUploadResult.setResponse("success");
        // 文件路径需要保存到数据库
        SysFile sysFile = new SysFile();
        //用户自定义文件名//TODO
        sysFile.setFilename(originalFilename);
        //设置提交的用户id和用户名
        Long userId = LoginUserInfoHelper.getUserId();
        String username = LoginUserInfoHelper.getUsername();
        sysFile.setUserId(userId);
        sysFile.setUsername(username);
        //设置文件在oss的key
        sysFile.setOsskeys(filePath);
        //设置文件id 用当前时间作为file uid
        Long fileId = (Long) System.currentTimeMillis();
        sysFile.setFileId(fileId);
        //设置文件描述
        //TODO
        sysFile.setDescription("test");
        //存储到数据库中
        sysFileService.save(sysFile);
        //返回的结果对象
        fileUploadResult.setName(this.aliyunConfig.getUrlPrefix() + filePath);
        fileUploadResult.setUid(String.valueOf(fileId));
        return fileUploadResult;
    }
    /**
     * 生成路径以及文件名
     *
     * @param sourceFileName
     * @return
     */
    private String getFilePath(String sourceFileName) {
        DateTime dateTime = new DateTime();
        return "images/" + dateTime.toString("yyyy") + "/" + dateTime.toString("MM") + "/"
                + dateTime.toString("dd") + "/" + System.currentTimeMillis()
                + RandomUtils.nextInt(100, 9999) + "."
                + StringUtils.substringAfterLast(sourceFileName, ".");
    }

    /**
     * 查看文件列表
     *
     * @return
     */
    public List<OSSObjectSummary> list() {
        // 设置最大个数。
        final int maxKeys = 200;
        // 列举文件。
        ObjectListing objectListing = ossClient.listObjects(new ListObjectsRequest(aliyunConfig.getBucketName()).withMaxKeys(maxKeys));
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        return sums;
    }

    /**
     * 删除文件
     *
     * @param objectName
     * @return
     */
    public Result delete(String objectName) {
        // 根据BucketName,objectName删除文件
        ossClient.deleteObject(aliyunConfig.getBucketName(), objectName);
        //数据库中保证已经删除
        System.out.println("00000000000000000000000000");
        LambdaQueryWrapper<SysFile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysFile::getOsskeys,objectName);
        sysFileService.remove(wrapper);
        return Result.ok();
    }

    /**
     * 下载文件
     *
     * @param os
     * @param objectName
     * @throws IOException
     */
    public void exportOssFile(OutputStream os, String objectName) throws IOException {
        // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
        OSSObject ossObject = ossClient.getObject(aliyunConfig.getBucketName(), objectName);
        // 读取文件内容。
        BufferedInputStream in = new BufferedInputStream(ossObject.getObjectContent());
        BufferedOutputStream out = new BufferedOutputStream(os);
        byte[] buffer = new byte[1024];
        int lenght = 0;
        while ((lenght = in.read(buffer)) != -1) {
            out.write(buffer, 0, lenght);
        }
        if (out != null) {
            out.flush();
            out.close();
        }
        if (in != null) {
            in.close();
        }
    }

    /**
     * 更新文件名，和描述
     * @param fileid
     * @param filename
     * @param des
     * @return
     */
    public FileUploadResult updata(String fileid, String filename, String des,String type) {
        LambdaQueryWrapper<SysFile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysFile::getFileId,Long.parseLong(fileid));
        SysFile sysFile = sysFileService.getOne(wrapper);
        sysFile.setFilename(filename);
        sysFile.setDescription(des);
        sysFile.setTypes(type);
        sysFileService.updateById(sysFile);
        FileUploadResult result = new FileUploadResult();
        result.setResponse("success");
        result.setName(filename);
        result.setResponse("ok");
        result.setCode(200);
        return result;
    }
}