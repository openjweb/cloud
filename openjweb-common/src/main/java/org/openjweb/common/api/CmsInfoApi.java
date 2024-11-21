package org.openjweb.common.api;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/api/cms1/cmsInfo")
@RestController
public class CmsInfoApi {


    //测试跨包调用接口 localhost:8001/api/cms1/cmsInfo/list
    @RequestMapping("list")
    public String list(){
        System.out.println("test........");


        return "hi5555555";
    }

    //@Resource
    //private DemoService demoService;

}
