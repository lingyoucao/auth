package com.demo.service;

import com.demo.mapper.UserMapper;
import com.demo.token.model.User;
import com.demo.token.util.DesUtil;
import com.demo.util.UserIdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    public Integer addUser() {
        try {
            User user = new User();
            String uuid = UUID.randomUUID().toString().toLowerCase().replaceAll("-", "");
            String authCode = DesUtil.encrypt(uuid);
            String userId = UserIdUtil.getUserId(uuid);
            long id = Long.parseLong(userId);
            user.setUserId(id);
            user.setAuthCode(authCode);
            return userMapper.addUser(user);
        } catch (Exception e) {
            logger.error("添加用户出错", e);
            return 0;
        }
    }

    public User getUserById(String userId) {
        User user = null;
        try {
            user = userMapper.getUserById(userId);
            String passWord = DesUtil.decrypt(user.getAuthCode());
            user.setPassword(passWord);
        } catch (Exception e) {
            logger.error("获取用户信息出错", e);
        }
        return user;
    }

}
