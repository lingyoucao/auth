package com.demo.user;

import com.demo.AuthApplication;
import com.demo.service.UserService;
import com.demo.token.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {
    private static final Logger logger = LoggerFactory.getLogger(UserTest.class);
    @Autowired
    private UserService userService;

    @Test
    public void testAddUser() throws Exception {
        userService.addUser();
    }

    @Test
    public void testGetUser() {
        User user = userService.getUserById("5912480581547");
        logger.info("用户的明文密码："+user.getPassword());
    }
}
