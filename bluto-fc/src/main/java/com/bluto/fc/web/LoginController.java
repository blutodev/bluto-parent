package com.bluto.fc.web;

import com.bluto.fc.exception.BusinessException;
import com.bluto.fc.model.request.UserParam;
import com.bluto.fc.model.request.bo.ErrorCodeEnum;
import com.bluto.fc.service.LoginService;
import com.bluto.fc.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonResult login(@RequestBody UserParam param){
        try {
            loginService.loginChk(param);
        }catch (BusinessException be){
            return JsonResult.error(be.getErrorCode(), be.getErrorMsg());
        }catch (Exception e){
            return JsonResult.error(ErrorCodeEnum.SYSTEM_ERROR.getErrorCode(),"");
        }
        return JsonResult.success();
    }
}
