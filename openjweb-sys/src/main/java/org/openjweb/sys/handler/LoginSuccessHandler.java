package org.openjweb.sys.handler;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.response.ResponseResult;
import org.openjweb.common.util.CMSUtil;
import org.openjweb.common.util.Password;
import org.openjweb.common.util.StringUtil;
import org.openjweb.core.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Component
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${jwt.loginUrl:}") String loginUrl;//前端登录页地址
    @Value("${jwt.loginSuccUrl:}") String loginSuccUrl; //前端登录成功后跳转的地址

    @Autowired
    JwtUtil jwtUtils;

    @Resource(name = "jdbcTemplateOne")
    private JdbcTemplate service;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();

        log.info("success ,add jwt accesstoken to header.........");
        log.info(authentication.getName());


        // 生成JWT，并放置到请求头中
        String jwt = jwtUtils.generateToken(authentication.getName());
        log.info("jwtUtils.getHeader():：：：");
        log.info(jwtUtils.getHeader());
        log.info (jwt);
        httpServletResponse.setHeader(jwtUtils.getHeader(), jwt);
        //if(StringUtils.isEmpty(loginUrl)) {//这种判断不适合同时存在APP登录和网页登录的情况，后面考虑根据什么判断
        if(1==1){//uniapp调试
            //对于app可将
            ResponseResult result = ResponseResult.okResult("SuccessLogin");//登录成功
            String json = JSONUtil.toJsonStr(result);
            outputStream.write(json.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            outputStream.close();
        }
        else{
            log.info("配置了前端页，则登录成功后跳转到指定的地址：");


            String domainNAme = CMSUtil.getDomainName(httpServletRequest);
            //适配旧版的openjweb,在原来的
            //String toUrl = "https://"+domainNAme+loginSuccUrl+"?access_token=OPENJWEB_CLOUD";//前端根据这个从头部获取信息认证
            //String toUrl = "https://"+domainNAme+loginSuccUrl+"?access_token="+jwt;//前端根据这个从头部获取信息认证
            String toUrl = "https://"+domainNAme+loginUrl+"?access_token="+jwt;//前端根据这个从头部获取信息认证
            //适配旧版增加参数
            String validKey = "";//IP+验证私钥:IP+userId并MD5

            String ip = httpServletRequest.getRemoteAddr();//这个需要换一个获取方式？
            String nonce = StringUtil.getUUID();
            String timestamp = String.valueOf(System.currentTimeMillis());
            String key = service.queryForObject("select parm_value from comm_system_parms where parm_name='WeixinSSOSignKey' ",String.class).toString();
            String cookieToken = StringUtil.getUUID();
            String sourceSign = "ip="+ip+"&access_token="+jwt+"&cookie="+cookieToken+"&nonce="+nonce+"&timestamp="+timestamp+"&key="+key;//key放末尾
            String sign = Password.MD5EncodePassEncoding(sourceSign, "utf-8") ;
            String parm = "&cookie="+cookieToken+"&ip="+ip+"&nonce="+nonce+"&timestamp="+timestamp+"&sign="+sign;
            toUrl+= parm;
             log.info(toUrl);
            httpServletResponse.setContentType("text/html");//设置普通的
            httpServletResponse.sendRedirect(toUrl);
            /*String loginId = "";
            try{
                Claims claims = jwtUtils.getClaimsByToken(jwt);
                loginId = claims.getSubject();
            }
            catch (Exception ex){}

            JSONObject dataMap = new JSONObject();
            dataMap.put("access_token", jwt);
            dataMap.put("login_id", loginId);// 登录账号,可能不使用
            outputStream.write(dataMap.toString().getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            outputStream.close();*/

        }
    }
}
