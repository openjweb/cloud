package org.openjweb.cms.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/cms2/cmsCateGory")
@RestController
public class CmsCategoryApi {

    //测试跨包调用接口 localhost:8001/api/cms2/cmsCateGory/list
    @RequestMapping("list")
    public String list(){
        System.out.println("test........");
        return "hi2";
    }
}
