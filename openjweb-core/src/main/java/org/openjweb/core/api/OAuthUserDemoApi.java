package org.openjweb.core.api;

import org.openjweb.core.entity.CommUser;
import org.openjweb.core.service.CommUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequestMapping("/api/user")
@RestController
public class OAuthUserDemoApi {

    @Autowired
    private CommUserService commUserService;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommUser getUserInfo(Principal principal) {

        CommUser user = commUserService.selectUserByLoginId(principal.getName());
        user.setPassword("");

        return user;
    }
}
