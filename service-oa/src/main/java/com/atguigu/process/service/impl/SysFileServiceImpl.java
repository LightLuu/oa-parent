package com.atguigu.process.service.impl;


import com.atguigu.common.oss.StsService;
import com.atguigu.model.file.SysFile;
import com.atguigu.process.mapper.SysFileMapper;
import com.atguigu.process.service.SysFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
}
