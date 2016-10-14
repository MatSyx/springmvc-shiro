package com.aaron.controller;

import com.aaron.domain.User;
import com.aaron.domain.UserCriteria;
import com.aaron.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by shiyongxiang on 16/10/14.
 */
@Controller
@Slf4j
public class SiteController {

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/")
    public String index(){
        log.info("这里是首页={}",1);
        return "index";
    }

    @RequestMapping("rest")
    @ResponseBody
    public String rest(){
        return "时永祥";
    }

    @RequestMapping("my")
    @ResponseBody
    public List<User> my(){
        UserCriteria userCriteria=new UserCriteria();
        UserCriteria.Criteria criteria= userCriteria.createCriteria();
        return userMapper.selectByExample(userCriteria);
    }
}
