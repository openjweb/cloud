package org.openjweb.core.mybatisplus.handler;



import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import org.openjweb.common.util.StringUtil;
import org.openjweb.core.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Autowired
    JwtUtil jwtUtils;

    @Override
    public void insertFill(MetaObject metaObject) {
        //String dt =  String.valueOf(System.currentTimeMillis());
        String dt = StringUtil.getCurrentDateTime();
        this.strictInsertFill(metaObject, "createDt", String.class, dt);
        this.strictInsertFill(metaObject, "updateDt", String.class, dt);
        //设置创建人
        String userId = getCreateUid();
        // 根据实际字段名填充
        log.info("插入填充............，:"+userId);
        this.strictInsertFill(metaObject, "createUid", String.class, userId);
        this.strictInsertFill(metaObject, "updateUid", String.class, userId);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("调用了updateFill..........");

        //this.strictUpdateFill(metaObject, "updateDt", String.class, String.valueOf(System.currentTimeMillis()));
        //this.strictUpdateFill(metaObject, "updateDt", String.class, StringUtil.getCurrentDateTime());
        this.setFieldValByName("updateDt",  StringUtil.getCurrentDateTime(), metaObject);
        String userId = getCreateUid();
        log.info(userId);
        //this.strictUpdateFill(metaObject, "updateUid", String.class, userId );
        this.setFieldValByName("updateUid",  userId, metaObject);
    }

    private String getCreateUid() {
        // 这里获取当前登录用户的逻辑，根据实际情况实现
        //怎样才能在这里获取到用户信息
        //怎样在MyMetaObjectHandler获取当前用户
        //怎样在MyMetaObjectHandler获取认证token
        //采用下面的方式获取request,然后从request的header中获取token
        String token = "";
        String loginId = "";
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String accessToken = request.getHeader("Authorization");
            if(StringUtils.isEmpty(accessToken)){
                accessToken = request.getParameter("accessToken");
            }
            if(StringUtil.isEmpty(accessToken)){
                accessToken = request.getHeader("accesstoken");
            }
            //log.info("springboot getUserInfo2 获取的accessToken为：：：");
            //log.info(accessToken);

            if(accessToken!=null&&accessToken.trim().length()>0){

                Claims claims = jwtUtils.getClaimsByToken(accessToken);
                if(claims!=null){

                    loginId = claims.getSubject();
                }

            }
            //log.info("MyMetaObjectHandler获取登录账号为:");
            //log.info(loginId);
            //jwtUtils.getHeader()

            // 你可以在这里使用request对象
        }
        //log.info("token::::");
        //log.info(token);
        return loginId;
    }


}