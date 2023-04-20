package com.atguigu.process.service.impl;


import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.atguigu.auth.utils.MenuHelper;
import com.atguigu.common.oss.AliyunConfig;
import com.atguigu.common.oss.StsService;
import com.atguigu.common.result.Result;
import com.atguigu.model.file.SysFile;
import com.atguigu.process.mapper.SysFileMapper;
import com.atguigu.process.service.SysFileService;
import com.atguigu.security.custom.LoginUserInfoHelper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author afraid
 * @since 2023-04-18
 */
@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {

    @Autowired
    private FileUploadService fileUploadService;

    @Resource
    private OSS ossClient;

    @Resource
    private AliyunConfig aliyunConfig;
    /**
     * 获得上传oss的sts
     ** @return
     * */
    public Map<String, String> getkey(){
        Map<String, String> map = StsService.getkey();
        if( map!= null){
            return map;
        }
        return null;
    }

    /**
     * 获得该用户的所有文件及分类
     * @return
     */
    public List<SysFile> findNodes(){
        //新建四个目录列表
        String[] strs = {"实习","成绩","资料","其他"};
        List<SysFile> trees = new ArrayList<>();
        //目录
        for (int i = 0; i < strs.length; i++) {
            SysFile temp = new SysFile();
            temp.setFilename(strs[i]);
            temp.setId(Integer.toUnsignedLong(i+1));
            trees.add(temp);
        }
        //1.查询该用户的所有文件
        LambdaQueryWrapper<SysFile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysFile::getUserId, LoginUserInfoHelper.getUserId());
        List<SysFile> list = baseMapper.selectList(wrapper);
        //2.获得每一个类别对应的文件
        for (SysFile sysfile:trees) {
            sysfile = MenuHelper.getChildren(sysfile,list);
        }
        return trees;
    }

    /**
     * 重写的根据id删除方法
     */
    @Override
    public boolean removeById(Long id){
        //1.根据id查询objecname
        LambdaQueryWrapper<SysFile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysFile::getId,id);
        SysFile sysFile = baseMapper.selectOne(wrapper);
        String osskeys = sysFile.getOsskeys();
        Result result = fileUploadService.delete(osskeys);
        return true;
    }

    /**
     * 获取urL
     * @param osskeys
     * @param filename
     */
    @Override
    public  Result upload(String osskeys, String filename,Integer time){
        //获得osskeys的url
        try {
            // 设置签名URL过期时间，单位为毫秒。
            Integer len = 3600;
            if(time == 0){
                len = 1800;
            }else if(time  == 1){
                len = 24*60;
            }else {
                len = 3*24*60;
            }

            Date expiration = new Date(new Date().getTime() + len * 1000);
            // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
            URL url = ossClient.generatePresignedUrl(aliyunConfig.getBucketName(), osskeys, expiration);
            System.out.println(url);
            return Result.ok(url);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        //
        return Result.ok();
    }

}
