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

    @RequestMapping("login")
    public String login(String loginId){

        return "hi";
    }


}
