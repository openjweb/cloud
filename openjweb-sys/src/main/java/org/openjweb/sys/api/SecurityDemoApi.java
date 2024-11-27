package org.openjweb.sys.api;

import lombok.extern.slf4j.Slf4j;
import org.openjweb.sys.auth.security.MD5PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试地址localhost:8001/demo/auth/checksalt
 */
@Slf4j
@RequestMapping("/demo/auth")
@RestController
public class SecurityDemoApi {

    @Autowired
    private MD5PasswordEncoder md5PasswordEncoder;

    @RequestMapping("checksalt")
    public String checksalt(){
        String salt = md5PasswordEncoder.getSalt();
        log.info("salt::::"+String.valueOf(salt));
        return salt;
    }
}
