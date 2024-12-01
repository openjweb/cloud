package org.openjweb.sys.config.oauth;

import lombok.extern.slf4j.Slf4j;
import org.openjweb.core.service.CommUserService;
import org.openjweb.sys.config.oauth.service.OAuth2ClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

//授权服务器配置
@Configuration
@EnableAuthorizationServer
@Slf4j
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter
{
    @Autowired
    private CommUserService myUserDetailsService;

    @Autowired
    private OAuth2ClientDetailsService myClientDetailsService;

    @Autowired
    //@Qualifier("authenticationManagerBean") //authenticationManagerBean有什么区别,对应WebSecurityConfig里的
    @Qualifier("authenticationManager") //

    private AuthenticationManager authenticationManager;

    //service企业认证
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(myClientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).userDetailsService(myUserDetailsService);
    }
}
