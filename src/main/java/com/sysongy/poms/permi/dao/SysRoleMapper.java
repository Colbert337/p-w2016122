package com.sysongy.poms.permi.dao;

import com.sysongy.poms.permi.model.SysRole;
import com.sysongy.poms.permi.model.SysUser;

import java.util.List;

public interface SysRoleMapper {
    List<SysRole> queryRoleList(SysRole sysRole);

    SysRole queryRoleById(String roleId);

    List<SysRole> queryRoleListByUserType(String userType,String stationId);

    int deleteRoleById(String roleId);

    int addRole(SysRole record);

    int updateRole(SysRole record);

    List<SysRole> queryAdminRoleList(SysRole sysRole);
}