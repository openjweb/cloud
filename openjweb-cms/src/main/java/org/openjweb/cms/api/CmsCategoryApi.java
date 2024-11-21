package org.openjweb.cms.api;

import org.openjweb.core.service.CommCompanyService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/api/cms/cmsInfo")
@RestController
public class CmsCategoryApi {

    @Resource(name = "jdbcTemplateOne")
    private JdbcTemplate service;

    //测试跨包调用接口 localhost:8001/api/cms/cmsinfo/list
    @RequestMapping("list")
    public String list(){
        System.out.println("test........");

        int count = service.queryForObject("select count(*) from cms_info ",Integer.class);
        System.out.println(count);

        return "hi555555511111111111";
    }

    @Resource
    private CommCompanyService commCompanyService;

    //localhost:8001/api/cms/cmsInfo/queryCom

    @RequestMapping("queryCom")
    public String getComName(){


        String name = commCompanyService.getComName("");
        return name;

    }




}
