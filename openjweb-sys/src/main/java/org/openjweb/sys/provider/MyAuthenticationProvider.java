package org.openjweb.sys.provider;

import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.util.AESUtil;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collections;

@Slf4j
public class MyAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    public MyAuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("自定义的AuthenticationProvider..................");
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        //自行密码验证
        //将数据库中的AES密码还原
        String encodePwd = userDetails.getPassword();
        //这个KEY怎么传进来？暂时先写死
        String decodePwd = AESUtil.aesDncode("/Z3E1YW1mxM0BCluJdYaLHCnhTuzE8j0",encodePwd);

        if (userDetails == null || !password.equals(decodePwd)) {
            log.info("抛出异常.................");

            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
