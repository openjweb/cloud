package org.openjweb.sys.handler;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.response.ResponseResult;
import org.openjweb.core.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    JwtUtil jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();

        log.info("success ,add jwt accesstoken to header.........");


        // 生成JWT，并放置到请求头中
        String jwt = jwtUtils.generateToken(authentication.getName());
        log.info("jwtUtils.getHeader():");
        log.info(jwtUtils.getHeader());
        log.info(jwt);
        httpServletResponse.setHeader(jwtUtils.getHeader(), jwt);
        ResponseResult result = ResponseResult.okResult("SuccessLogin");//登录成功
        String json = JSONUtil.toJsonStr(result);
        outputStream.write(json.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
