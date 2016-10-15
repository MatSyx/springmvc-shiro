package com.aaron.service;

import com.aaron.domain.*;
import com.aaron.mapper.PermissionMapper;
import com.aaron.mapper.RolePermissionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by shiyongxiang on 16/10/15.
 */
@Service
@Slf4j
public class PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    public List<String> getPermissionNamesByRoles(List<Role> roles) {
        List<Integer> roleIds = new LinkedList<>();
        List<Integer> permissionIds = new LinkedList<>();
        List<String> permissionNames = new LinkedList<>();
        if (!CollectionUtils.isEmpty(roles)) {
            for (Role role : roles) {
                roleIds.add(role.getId());
            }
            RolePermissionExample rolePermissionExample = new RolePermissionExample();
            RolePermissionExample.Criteria rolePermissionCriteria = rolePermissionExample.createCriteria();
            rolePermissionCriteria.andRoleIdIn(roleIds);
            List<RolePermission> rolePermissions = rolePermissionMapper.selectByExample(rolePermissionExample);
            if (!CollectionUtils.isEmpty(rolePermissions)) {
                for (RolePermission rolePermission : rolePermissions) {
                    permissionIds.add(rolePermission.getPermissionId());
                }
                PermissionExample permissionExample = new PermissionExample();
                PermissionExample.Criteria permissionCriteria = permissionExample.createCriteria();
                permissionCriteria.andIdIn(permissionIds);
                List<Permission> permissions=permissionMapper.selectByExample(permissionExample);
                if (!CollectionUtils.isEmpty(permissions)){
                    for (Permission permission:permissions){
                        permissionNames.add(permission.getName());
                    }
                    log.info("permissionNames={}",permissionNames);
                    return permissionNames;
                }
            }
        }
        return null;
    }
}
