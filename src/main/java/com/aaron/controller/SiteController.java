package com.aaron.controller;

import com.aaron.domain.User;
import com.aaron.util.VerifyCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by shiyongxiang on 16/10/14.
 */
@Controller
@Slf4j
public class SiteController {

    @Autowired
    private CacheManager cacheManager;

    @RequestMapping("/")
    public String index() {
        log.info("======首页======");
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/logout")
    public String doLogout(HttpServletRequest request, Model model) {
        log.info("======用户" + request.getSession().getAttribute("currentUser") + "退出了系统======");
        SecurityUtils.getSubject().logout();
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(User user, HttpServletRequest request, Model model, String captcha) {
        log.info("======用户进入了SiteController的login======");
        String returnUrl = "";
        String msg = "";
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        String verifyCode = (String) request.getSession().getAttribute("verifyCode");
        log.info("用户［{}］登录时输入的验证码为[{}]", user.getUsername(), captcha);
        if (StringUtils.isEmpty(captcha) || !verifyCode.equals(captcha.toLowerCase())) {
            msg="验证码不正确";
            model.addAttribute("message", msg);
            return "login";
        }
        token.setRememberMe(true);
        Subject subject = SecurityUtils.getSubject();
        try {
            log.info("对用户[" + user.getUsername() + "]进行登录验证..验证开始");
            subject.login(token);
            log.info("对用户[" + user.getUsername() + "]进行登录验证..验证通过");
            returnUrl = "redirect:/";
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
        if (subject.isAuthenticated()) {
            log.info("用户[" + user.getUsername() + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
        } else {
            token.clear();
            returnUrl = "login";//登录失败
        }
        log.info("returnUrl={}", returnUrl);
        return returnUrl;
    }

    @RequestMapping("/saveUrl")
    public String test1() {
        return "saveUrl";
    }

    @RequestMapping("/unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }

    /**
     * 获取验证码图片和文本(验证码文本会保存在HttpSession中)
     */
    @RequestMapping("/captcha")
    public void genCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置页面不缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        String verifyCode = VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_NUM_ONLY, 4, null);
        //将验证码放到HttpSession里面
        request.getSession().setAttribute("verifyCode", verifyCode);
        log.info("本次生成的验证码为[{}],已存放到HttpSession中", verifyCode);
        //设置输出的内容的类型为JPEG图像
        response.setContentType("image/jpeg");
        BufferedImage bufferedImage = VerifyCodeUtil.generateImageCode(verifyCode, 90, 30, 5, true, Color.WHITE, null, null);
        //写给浏览器
        ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());
    }
}
