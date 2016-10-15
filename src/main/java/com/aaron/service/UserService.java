package com.aaron.service;

import com.aaron.domain.User;
import com.aaron.domain.UserExample;
import com.aaron.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by shiyongxiang on 16/10/15.
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


    public User getByUsername(String username) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria=userExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<User> users=userMapper.selectByExample(userExample);
        return CollectionUtils.isEmpty(users)?null:users.get(0);
    }
}
