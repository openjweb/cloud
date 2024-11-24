package org.openjweb.core.mybatisplus.handler;



import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        String dt = String.valueOf(System.currentTimeMillis());
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

        this.strictUpdateFill(metaObject, "updateDt", String.class, String.valueOf(System.currentTimeMillis()));

        String userId = getCreateUid();
        log.info(userId);
        this.strictUpdateFill(metaObject, "updateUid", String.class, userId );
    }

    private String getCreateUid() {
        // 这里获取当前登录用户的逻辑，根据实际情况实现
        //怎样才能在这里获取到用户信息
        //怎样在MyMetaObjectHandler获取当前用户
        //怎样在MyMetaObjectHandler获取认证token
        //采用下面的方式获取request,然后从request的header中获取token
        String token = "";
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            token = request.getHeader("token");
            if(StringUtils.isEmpty(token)){
                token = request.getParameter("token");
            }

            // 你可以在这里使用request对象
        }
        //log.info("token::::");
        //log.info(token);
        return "abao2222";
    }

}