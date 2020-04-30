package com.bluto.fc.web;

import com.bluto.fc.model.request.AgentParam;
import com.bluto.fc.service.AgentService;
import com.bluto.fc.util.JsonResult;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class AgentController {
    @Autowired
    private AgentService agentService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public JsonResult getAllList(@RequestBody AgentParam param){
        PageInfo pageInfo = agentService.getAll(param);
        return JsonResult.getResult(pageInfo);
    }
}
