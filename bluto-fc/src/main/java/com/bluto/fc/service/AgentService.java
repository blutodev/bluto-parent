package com.bluto.fc.service;

import com.bluto.fc.mapper.AgentMapper;
import com.bluto.fc.model.Agent;
import com.bluto.fc.model.AgentExample;
import com.bluto.fc.model.request.AgentParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class AgentService {

    @Autowired
    private AgentMapper agentMapper;

    /**
     *
     * @return
     */
    public PageInfo getAll(AgentParam param){
        if (param.getPageNum() != null && param.getPageSize() != null){
            //分页查询
            PageHelper.startPage(param.getPageNum(),param.getPageSize());
        }
        AgentExample example = new AgentExample();
        List<Agent> list = agentMapper.selectByExample(example);
        return new PageInfo(list);
    }
}
