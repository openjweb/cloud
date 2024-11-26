package org.openjweb.b2c.api;

import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.response.ResponseResult;
import org.openjweb.core.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jwt/")
@Slf4j
public class JwtDemoApi {

    @Value("${jwt.expire}")
    private long expire;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.header}")
    private String header;

    ////localhost:8001/api/jwt/testjwt?loginId=admin
    @RequestMapping("/testjwt")
    public ResponseResult testJwt(String loginId) {
        String str = "";
        log.info("开始调用testjwt::");
        log.info("配置里读header");
        log.info(header);
        log.info(secret);
        log.info(String.valueOf(expire));

        JwtUtil jwtUtil = new JwtUtil(header, secret, expire);
         str = jwtUtil.generateToken(loginId);
        //检查是否读取了配置文件的参数
        log.info("测试账号转jwt:");
        log.info(str);
        log.info(jwtUtil.getClaimsByToken(str).getSubject());
        return ResponseResult.okResult(str);
    }
}
