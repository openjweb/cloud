package org.openjweb.core.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/auth")
@RestController
public class AuthDemoApi {

    /**
     * 此接口的URL加到demo_user_auth中测试权限控制
     * //测试地址：localhost:8001/api/auth/test

     * @return
     */

    @RequestMapping("test")
    public String testauth(){

        return "hello";//不做权限配置的话，返回hello

    }

    @RequestMapping("test2")
    public String testauth2(){

        return "hello2";//不做权限配置的话，返回hello

    }

}
