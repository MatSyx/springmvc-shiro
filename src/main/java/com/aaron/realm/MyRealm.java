package com.aaron.realm;

import com.aaron.domain.Role;
import com.aaron.domain.User;
import com.aaron.service.PermissionService;
import com.aaron.service.RoleService;
import com.aaron.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiyongxiang on 16/10/14.
 */
@Slf4j
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String currentUsername = (String) super.getAvailablePrincipal(principalCollection);
        List<String> roleNames = new ArrayList<>();
        List<String> permissionNames = new ArrayList<>();
        //从数据库中获取当前登录用户的详细信息
        User user = userService.getByUsername(currentUsername);
        if (null != user) {
            //实体类User中包含有用户角色的实体类信息
            List<Role> roles = roleService.getRolesByUserId(user.getId());
            roleNames = roleService.getRoleNamesUserId(user.getId());
            if (null != roleNames) {
                permissionNames = permissionService.getPermissionNamesByRoles(roles);
            }
        } else {
            throw new AuthorizationException();
        }
        //为当前用户设置角色和权限
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        simpleAuthorInfo.addRoles(roleNames);
        simpleAuthorInfo.addStringPermissions(permissionNames);
        return simpleAuthorInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        log.info("当前subject的token为：{}", token);
        User user = userService.getByUsername(token.getUsername());
        if (null != user) {
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(),getName());
            return authcInfo;
        } else {
            return null;
        }
    }
}
