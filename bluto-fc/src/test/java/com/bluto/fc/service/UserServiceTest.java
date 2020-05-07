package com.bluto.fc.service;

import com.bluto.fc.model.TUser;
import com.bluto.fc.model.request.UserParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void getUser() {
        UserParam param = new UserParam();
        param.setUserName("bluto");
        param.setPassword("111111");
        TUser user = userService.getUser(param);
        System.out.println("user="+user);
    }
}