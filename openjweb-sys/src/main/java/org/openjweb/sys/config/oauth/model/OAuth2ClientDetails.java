package org.openjweb.sys.config.oauth.model;


import lombok.extern.slf4j.Slf4j;
import org.openjweb.core.entity.CommSsoClientApp;
import org.openjweb.sys.auth.security.AESPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class OAuth2ClientDetails implements ClientDetails {
    private CommSsoClientApp client;

    public OAuth2ClientDetails(CommSsoClientApp client) {
        this.client = client;
    }

    @Override
    public String getClientId() {
        return client.getAccount();
    }

    @Override
    public Set<String> getResourceIds() {
        return new HashSet<>();
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        //return client.getPassword();
        log.info("返回的用户密码："+client.getPassword());
        //String bcryptPwd = new BCryptPasswordEncoder().encode(client.getPassword());
        //log.info("BCRYPT:");
        //log.info(bcryptPwd);
        //https://juejin.cn/post/6957109670221873166
        //return "{AES}"+new AESPasswordEncoder().encode(client.getPassword());

        return "{bcrypt}"+new BCryptPasswordEncoder().encode(client.getPassword());
    }

    @Override
    public boolean isScoped() {
        return true;
    }

    @Override
    public Set<String> getScope() {
        Set<String> set = new HashSet<>();
        set.add("read");

        return set;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        Set<String> set = new HashSet<>();
        set.add("authorization_code");
        set.add("refresh_token");

        return set;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        Set<String> set = new HashSet<>();
        set.add(client.getCallBackUrl());

        return set;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return new HashSet<>();
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return client.getAccessTokenOverdueSeconds();
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return client.getRefreshTokenOverdueSeconds();
    }

    @Override
    public boolean isAutoApprove(String s) {
        return true;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return new HashMap<>();
    }


}
