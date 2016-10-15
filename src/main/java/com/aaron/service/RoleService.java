package com.aaron.service;

import com.aaron.domain.Role;
import com.aaron.domain.RoleExample;
import com.aaron.domain.UserRole;
import com.aaron.domain.UserRoleExample;
import com.aaron.mapper.RoleMapper;
import com.aaron.mapper.UserRoleMapper;
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
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    public List<Role> getRolesByUserId(Integer userId) {
        UserRoleExample userRoleExample = new UserRoleExample();
        UserRoleExample.Criteria userRoleCriteria = userRoleExample.createCriteria();
        userRoleCriteria.andUserIdEqualTo(userId);
        List<UserRole> userRoles = userRoleMapper.selectByExample(userRoleExample);

        List<Integer> roleIds = new LinkedList<>();
        for (UserRole userRole : userRoles) {
            roleIds.add(userRole.getRoleId());
        }

        if (!CollectionUtils.isEmpty(roleIds)) {
            RoleExample roleExample = new RoleExample();
            RoleExample.Criteria roleCriteria = roleExample.createCriteria();
            roleCriteria.andIdIn(roleIds);
            List<Role> roles = roleMapper.selectByExample(roleExample);
            return roles;
        }
        return null;
    }

    public List<String> getRoleNamesUserId(Integer userId) {
        List<Role> roles = getRolesByUserId(userId);
        List<String> roleNames = new LinkedList<>();
        if (!CollectionUtils.isEmpty(roles)) {
            for (Role role : roles) {
                roleNames.add(role.getName());
            }
            log.info("roleNames={}",roleNames);
            return roleNames;
        }
        return null;
    }
}
