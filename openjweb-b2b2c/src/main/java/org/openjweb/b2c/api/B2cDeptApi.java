package org.openjweb.b2c.api;


import org.openjweb.core.service.CommDeptService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RequestMapping("/api/b2b2c/dept")
@RestController
public class B2cDeptApi {
    //localhost:8001/api/b2b2c/dept/query
    @RequestMapping("query")
    public String getDeptName(){
        String name = "研发部1";
        return name;
    }

    @Resource
    private CommDeptService commDeptService;
    //localhost:8001/api/b2b2c/dept/queryService
    @RequestMapping("queryService")
    public String getDeptName2(){
        String name = commDeptService.getDeptDemoName("");
        return name;
    }
}