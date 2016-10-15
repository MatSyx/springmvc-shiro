package com.aaron.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by shiyongxiang on 16/10/15.
 */
@Controller
public class ShiroController {

    @RequiresRoles("admin")
    @RequestMapping("/needRoleAdmin")
    public String needRoleAdmin(){
        return "authority/needRoleAdmin";
    }

    @RequiresPermissions(value = {"user:view","user:create"},logical = Logical.AND)
    @RequestMapping("/needPermissionUserVAC")
    public String needPermissionUserViewAndCreate(){
        return "authority/needPermissionUserVAC";
    }

    @RequiresPermissions(value = {"user:view","user:create"},logical = Logical.OR)
    @RequestMapping("/needPermissionUserVOC")
    public String needPermissionUserViewOrCreate(){
        return "authority/needPermissionUserVOC";
    }

    @RequestMapping("/adminUnauthorized")
    public String adminUnauthorized(){
        return "authority/adminUnauthorized";
    }
}
