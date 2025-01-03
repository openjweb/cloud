package org.openjweb.core.api;

import org.openjweb.core.service.CommUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/comm/user")
@RestController
public class UCenterApi {
    @Autowired
    CommUserService userService;

    //http://localhost:8001/api/comm/user/login

    //https://c0001-1.zzyicheng.cn/clouds/api/comm/user/login
    //http://c0001-1.zzyicheng.cn:8001/api/comm/user/login 可以

    @RequestMapping("login")
    public String login(String loginId){
        //在这里获取前端传入的登录账号、密码、图片验证码等

        return "hi";
    }


}
