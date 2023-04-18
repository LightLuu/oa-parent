package com.atguigu.process.service;

import com.atguigu.model.file.SysFile;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
