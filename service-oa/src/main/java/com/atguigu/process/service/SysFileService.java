package com.atguigu.process.service;

import com.atguigu.common.result.Result;
import com.atguigu.model.file.SysFile;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author afraid
 * @since 2023-04-18
 */
public interface SysFileService extends IService<SysFile> {

    /**
     * 获得上传oss的sts
     * @return
     */
    Map<String, String> getkey();

    /**
     * 获得该用户的所有文件及分类
     * @return
     */
    List<SysFile> findNodes();

    /**
     * 重写的根据id删除方法
     */
    boolean removeById(Long id);

    /**
     * 获取urL
     * @param osskeys
     * @param filename
     */
     Result upload(String osskeys, String filename,Integer time);
}
