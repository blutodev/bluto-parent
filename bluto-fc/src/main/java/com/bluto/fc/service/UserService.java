package com.bluto.fc.service;

import com.bluto.fc.dao.TUserMapper;
import com.bluto.fc.model.TUser;
import com.bluto.fc.model.TUserExample;
import com.bluto.fc.model.request.UserParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService extends BaseService{

    @Autowired
    private TUserMapper tUserMapper;

    public TUser getUser(UserParam param){
        TUserExample example = new TUserExample();
        TUserExample.Criteria criteria = example.createCriteria();
        String userName = param.getUserName();
        String password = param.getPassword();
        if(!StringUtils.isEmpty(userName)){
            criteria.andUserNameEqualTo(userName);
        }
        if(!StringUtils.isEmpty(password)){
            criteria.andPasswordEqualTo(password);
        }
        List<TUser> list = tUserMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }else{
            return null;
        }
    }
}
