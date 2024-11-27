package org.openjweb.sys.auth.security;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.util.AESUtil;
import org.openjweb.common.util.MD5Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Data
@Slf4j

public class AESPasswordEncoder implements PasswordEncoder {
    //暂时沿用MD5的
    @Value("${aes.key}")
    private  String salt = "";
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {

        //log.info("原始:"+rawPassword.toString());
        //log.info(salt);


        String encodePwd =  AESUtil.aesEncode(salt,(String)rawPassword);
        //log.info("加密后:"+encodePwd);
        //log.info("encodedPassword"+encodedPassword);
        return encodedPassword.equals(encodePwd);
    }

    @Override
    public String encode(CharSequence rawPassword) {

        String encodePwd = AESUtil.aesEncode(salt,(String)rawPassword);
        return encodePwd;
    }

}
