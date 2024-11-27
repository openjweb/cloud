package org.openjweb.sys.auth.security;

import lombok.Data;
import org.openjweb.common.util.AESUtil;
import org.openjweb.common.util.MD5Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Data
public class AESPasswordEncoder implements PasswordEncoder {
    //暂时沿用MD5的
    @Value("${md5.salt}")
    private  String salt = "";
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {

        String encodePwd =  AESUtil.aesEncode(salt,(String)rawPassword);
        return encodedPassword.equals(encodePwd);
    }

    @Override
    public String encode(CharSequence rawPassword) {

        String encodePwd = AESUtil.aesEncode(salt,(String)rawPassword);
        return encodePwd;
    }
}
