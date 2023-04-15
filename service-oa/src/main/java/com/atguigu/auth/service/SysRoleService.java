package com.atguigu.auth.service;

import com.atguigu.model.system.SysRole;
import com.atguigu.vo.system.AssginRoleVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface SysRoleService extends IService<SysRole> {

    //1 查询所有角色 和 当前用户所属角色
    Map<String, Object> findRoleDataByUserId(Long userId);

    //2 为用户分配角色
    void doAssign(AssginRoleVo assginRoleVo);

    //3. 查询所有角色 和当前问卷所属角色
    Map<String, Object> findRoleDataByQuestionId(Long userId);

    //4.为问卷分配所属id
    void doAssignByQuestionId(AssginRoleVo assginRoleVo);
}
