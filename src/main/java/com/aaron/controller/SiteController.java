package com.aaron.controller;

import com.aaron.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by shiyongxiang on 16/10/14.
 */
@Controller
@Slf4j
public class SiteController {

    @RequestMapping("/")
    public String index() {
        log.info("======首页======");
        return "index";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/logout")
    public String doLogout(HttpServletRequest request, Model model) {
        log.info("======用户" + request.getSession().getAttribute("currentUser") + "退出了系统======");
        SecurityUtils.getSubject().logout();
        return "redirect:/login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String doLogin(User user, HttpServletRequest request, Model model) {
        log.info("======用户进入了SiteController的doLogin======");
        String msg="";
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        token.setRememberMe(true);
        Subject subject = SecurityUtils.getSubject();
        try {
            log.info("对用户[" + user.getUsername() + "]进行登录验证..验证开始");
            subject.login(token);
            log.info("对用户[" + user.getUsername() + "]进行登录验证..验证通过");
            return "forward:/";
//            if (subject.isAuthenticated()) {
//                request.getSession().setAttribute("currentUser", user);
//                SavedRequest savedRequest = WebUtils.getSavedRequest(request);
//                if (savedRequest == null || savedRequest.getRequestURI() == null) {
//                    log.info("打开首页");
//                    return "redirect:/";
//                } else {
//                    log.info("打开之前的页面");
//                    return "forward:" + savedRequest.getRequestURI();
//                }
//            } else {
//                return "redirect:/login";
//            }
        } catch (IncorrectCredentialsException e) {
            msg = "登录密码错误. Password for account " + token.getPrincipal() + " was incorrect.";
            model.addAttribute("message", msg);
        } catch (ExcessiveAttemptsException e) {
            msg = "登录失败次数过多";
            model.addAttribute("message", msg);
        } catch (LockedAccountException e) {
            msg = "帐号已被锁定. The account for username " + token.getPrincipal() + " was locked.";
            model.addAttribute("message", msg);
        } catch (DisabledAccountException e) {
            msg = "帐号已被禁用. The account for username " + token.getPrincipal() + " was disabled.";
            model.addAttribute("message", msg);
        } catch (ExpiredCredentialsException e) {
            msg = "帐号已过期. the account for username " + token.getPrincipal() + "  was expired.";
            model.addAttribute("message", msg);
        } catch (UnknownAccountException e) {
            msg = "帐号不存在. There is no user with username of " + token.getPrincipal();
            model.addAttribute("message", msg);
        } catch (UnauthorizedException e) {
            msg = "您没有得到相应的授权！" + e.getMessage();
            model.addAttribute("message", msg);
        }
        log.info("msg={}", msg);
        //验证是否登录成功
        if(subject.isAuthenticated()){
            log.info("用户[" + user.getUsername() + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
        }else{
            token.clear();
        }
        return "login";
    }

    @RequestMapping("/saveUrl")
    public String test1() {
        return "saveUrl";
    }

    @RequestMapping("/unauthorized")
    public String unauthorized(){
        return "unauthorized";
    }

}
