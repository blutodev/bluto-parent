package com.bluto.fc.service;

import com.bluto.fc.exception.BusinessException;
import com.bluto.fc.model.TUser;
import com.bluto.fc.model.request.UserParam;
import com.bluto.fc.model.request.bo.ErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class LoginService extends BaseService{

    @Autowired
    public UserService userService;

    public void loginChk(UserParam param){
        TUser user = userService.getUser(param);
        if (null == user){
            throw new BusinessException(ErrorCodeEnum.DATA_NO_FOUND, messageSource, "");
        }
    }
}
