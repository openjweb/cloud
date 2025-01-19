package org.openjweb.core.api;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/api/comm/companyDemo")
@RestController
public class CommCommpanyDemoApi {

    //localhost:8001/api/comm/companyDemo/query

    @RequestMapping("query")
    public String getComName(){
        String comName = "百度";

        return comName;

    }


    @Resource(name = "jdbcTemplateOne")
    private JdbcTemplate service;

    //localhost:8001/api/comm/company/queryDb


    @RequestMapping("queryDb")
    public String getComNameDb(){
        int count = service.queryForObject("select count(*) from comm_system_parms ",Integer.class);
        return String.valueOf(count);
    }

}
