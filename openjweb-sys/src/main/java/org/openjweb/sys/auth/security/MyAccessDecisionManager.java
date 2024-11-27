package org.openjweb.sys.auth.security;


import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class MyAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication auth, Object object, Collection<ConfigAttribute> ca) {
        System.out.println("decide......................");
        Collection<? extends GrantedAuthority> auths = auth.getAuthorities();
        for (ConfigAttribute configAttribute : ca) {

            if ("ROLE_LOGIN".equals(configAttribute.getAttribute())
                    && auth instanceof UsernamePasswordAuthenticationToken) {
                //如果需要的角色是ROLE_LOGIN且已登录
                //这里将queryUser匹配给了ROLE_LOGIN ，为什么
                return;
            }
            if ("ROLE_USER".equals(configAttribute.getAttribute())
                    && auth instanceof UsernamePasswordAuthenticationToken) {
                //如果需要的角色是ROLE_LOGIN且已登录
                //这里将queryUser匹配给了ROLE_LOGIN ，为什么
                return;
            }
            for (GrantedAuthority authority : auths) {
                System.out.println("得到的角色-当前角色："+authority.getAuthority()+"-----"+configAttribute.getAttribute());
                //if(configAttribute.getAttribute().equals("ROLE_LOGIN")) {//为什么会有ROLE_LOGIN
                //	return ;//对ROLE_LOGIN的角色不限制
                //}
                if (configAttribute.getAttribute().equals(authority.getAuthority())) {
                    System.out.println("匹配:"+authority.getAuthority()+",返回");
                    return;
                }
            }
        }
        System.out.println("不匹配，抛异常..........");
        throw new AccessDeniedException("权限不足");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }


}
