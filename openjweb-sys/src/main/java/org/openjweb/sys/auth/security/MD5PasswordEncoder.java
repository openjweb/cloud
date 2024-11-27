package org.openjweb.sys.auth.security;

import lombok.Data;
import org.openjweb.common.util.MD5Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@Data
public class MD5PasswordEncoder implements  PasswordEncoder  {
    @Value("${md5.salt}")
    private  String salt = "";
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {

        return encodedPassword.equals(MD5Util.encode((String)rawPassword, salt));
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return MD5Util.encode((String)rawPassword,salt);
    }

}
