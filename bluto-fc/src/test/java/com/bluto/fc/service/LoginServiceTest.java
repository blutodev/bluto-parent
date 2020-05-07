package com.bluto.fc.service;

import com.bluto.fc.exception.BusinessException;
import com.bluto.fc.model.request.UserParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LoginServiceTest {

    @Autowired
    private LoginService loginService;

    @Test
    public void loginChk() {
        UserParam param = new UserParam();
        param.setUserName("bluto");
        param.setPassword("111111");
        try {
            loginService.loginChk(param);
        }catch (BusinessException be){
            System.out.println(be.getErrorCode());
        }catch (Exception e){
        }
    }
}