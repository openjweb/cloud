package org.openjweb.core.api;

import org.openjweb.core.entity.CommUser;
import org.openjweb.core.service.CommUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/comm/auth")
@RestController
public class CommUserDemoApi {
    //测试地址：localhost:8001/api/comm/auth/test?loginId=admin
    @Autowired
    CommUserService userService;
    @RequestMapping("test")
    public String testauth(String loginId){
        List<String> authList  = null;
        String auth = "";
        try {
            authList = userService.getAuth(loginId);
            auth = String.join(",",authList);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
         return auth;
    }


}
