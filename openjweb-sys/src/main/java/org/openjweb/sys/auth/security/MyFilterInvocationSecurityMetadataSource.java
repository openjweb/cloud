package org.openjweb.sys.auth.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Component
@Slf4j
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    //private static JdbcTemplate service = JdbcTemplateConfig.getDefaultJdbcTemplate();
    @Resource(name="jdbcTemplateOne")
    private JdbcTemplate service;
    AntPathMatcher antPathMatcher = new AntPathMatcher();


    /**
     * 从URL解析获取是否有对应的AUTH，并找出AUTH编码加到数组
     * @param object
     * @return
     * @throws IllegalArgumentException
     */

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        String requestUrl = ((FilterInvocation) object).getRequestUrl();

        // 首先获取菜单列表
        List<Map<String, Object>> menuList = null;
        try {
            menuList = service.queryForList("select distinct auth_resource ,auth_id from comm_auth where "
                    + "auth_resource is not null and auth_resource<>'#' and auth_resource like '/api%'  ");// 权限列表
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //System.out.println("检查权限:"+requestUrl);
        //if(requestUrl.equals("/")) {//对/控制权限吗？？？？
        //	System.out.println("/权限返回......");
        //	return   SecurityConfig.createList("ROLE_USER");//没有权限清单的，登录后可访问
        //}
        if(menuList==null||menuList.size()==0) {
            System.out.println("权限清单为空......................");
        }
        for (Map<String, Object> map : menuList) {
            System.out.println("遍历匹配权限.........");
            String authResource = map.get("auth_resource").toString();
            System.out.println(authResource+"比较"+requestUrl);
            Long authId = new Long(map.get("auth_id").toString());

            List<Map<String, Object>> roleList = null;

            System.out.println("检查匹配:"+authResource+"----"+ requestUrl);
            if (antPathMatcher.match(authResource, requestUrl)) {
                try {
                    System.out.println("正在检查角色权限:，先查角色清单::::::::");
                    System.out.println(authResource);
                    roleList = service.queryForList(
                            "select  a.auth_resource,b.comm_code  from comm_auth a , comm_roles b,"
                                    + "comm_role_auth c where a.auth_id=? and a.auth_id=c.auth_id and c.role_id=b.role_id and a.auth_resource<>'#' and a.auth_resource is not null",
                            new Object[] { authId });

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                String[] strArray = null;
                if (roleList != null && roleList.size() > 0) {
                    strArray = new String[roleList.size()];

                    for (int i = 0; i < strArray.length; i++) {
                        System.out.println("有权的角色："+roleList.get(i).get("comm_code").toString()+"---"+authResource);
                        //System.out.println();
                        strArray[i] = roleList.get(i).get("comm_code").toString();
                    }
                    log.info("返回角色数组。。。。。。。。。。。。。。。。。。。。");
                    return SecurityConfig.createList(strArray);
                }
            }
            System.out.println ("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx11111");
            //返回一个不存在的角色就不能登录了
            //return SecurityConfig.createList("ROLE_AHHJSJASSSAASA");//在权限清单里，但是没授权，则不获得权限，给一个不存在角色
        }
        System.out.println("未在权限清单，返回。。。。。。");
        return  null;// SecurityConfig.createList("ROLE_LOGIN");//没有权限清单的，都可以访问	，如登录访问用ROLE_USER
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
