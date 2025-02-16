package org.openjweb.sys.api;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.core.entity.CommUser;
import org.openjweb.core.service.CommUserService;
import org.openjweb.sys.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/demo/oauth2client")
@Slf4j
public class Oauth2DemoApi {
    @Autowired
    CommUserService sysUserService;

    @Autowired
    LoginSuccessHandler loginSuccessHandler;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void thirdLogin(HttpServletResponse response) throws IOException {
        //localhost:8001/demo/oauth2client/login
        //http://localhost:8001/oauth/authorize?response_type=code&client_id=yncw&redirect_uri=http://localhost:8002/token/callBack
        //http://localhost:8003/oauth/authorize?response_type=code&client_id=yncw&redirect_uri=http://localhost:8002/token/callBack

        String url = "http://localhost:8003/oauth/authorize?response_type=code&client_id=zzyc&redirect_uri=http://localhost:8001/demo/oauth2client/callback";
        response.sendRedirect(url);


    }

    @RequestMapping(value = "/login2", method = RequestMethod.GET)
    public void thirdLogin2(HttpServletResponse response) throws IOException {
        //localhost:8001/demo/oauth2client/login2
        //http://localhost:8001/oauth/authorize?response_type=code&client_id=yncw&redirect_uri=http://localhost:8002/token/callBack
        //http://localhost:8003/oauth/authorize?response_type=code&client_id=yncw&redirect_uri=http://localhost:8002/token/callBack

        String url = "http://localhost:8001/oauth/authorize?response_type=code&client_id=zzyc&redirect_uri=http://localhost:8001/demo/oauth2client/callback";
        response.sendRedirect(url);


    }

    @RequestMapping(value="callback",method = RequestMethod.GET)

    public JSONObject callback(String code){

        System.out.println("在回调中callBack中存储返回的值..............");
        System.out.println("得到的code:::");
        System.out.println(code );
        HttpHeaders headers = new HttpHeaders();
        String clientId = "zzyc";
        //注意使用自己企业的ID
        //在请求头中设置client的ID和密码
        headers.add("authorization", "Basic " + new String(Base64.encodeBase64((clientId + ":" + "123456").getBytes())));

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("redirect_uri", "http://localhost:8001/demo/oauth2client/callback");
        String result = null;
        JSONObject resultJson = new JSONObject();
        //=http://localhost:8003/oauth/token
        try {
            log.info("oauth2请求开始..............");
            //result = new RestTemplate().postForObject("http://localhost:8003/oauth/token", new HttpEntity<>(params, headers), String.class);
            //result = new RestTemplate().postForObject("http://localhost:8003/oauth/token", new HttpEntity<>(params, headers), String.class);
            result = new RestTemplate().postForObject("http://localhost:8001/oauth/token", new HttpEntity<>(params, headers), String.class);

            if (result == null)
            {
                System.out.println("请求oauth失败，返回空................");
            }
            else{
                log.info("返回result:"+result);
            }
            JSONObject json1 = JSONObject.parseObject(result);
            String accessToken = json1.getString("access_token");
            String refreshToken = json1.getString("refresh_token");
            HttpHeaders tmpHeaders = new HttpHeaders();
            tmpHeaders.add("authorization", "Bearer " + accessToken);

            try
            {

                result = new RestTemplate().exchange("http://localhost:8001/api/user/info", HttpMethod.GET, new HttpEntity<>(tmpHeaders), String.class).getBody();
                log.info("返回的用户信息result::::::");
                log.info(result);

                //将第三方用户写入本系统中
                    JSONObject json = JSONObject.parseObject(result);//用户基本信息
                //演示refreshtoken
                HttpHeaders tmpHeaders2 = new HttpHeaders();
                tmpHeaders2.add("authorization", "Basic " + new String(Base64.encodeBase64((clientId + ":" + "123456").getBytes())));
                //用这个不行
                //tmpHeaders2.set("Authorization", "Bearer " + accessToken);

                // 准备请求体参数
                Map<String, String> params1 = new HashMap<>();
                params1.put("grant_type", "refresh_token");//refresh_token
                params1.put("refresh_token", refreshToken);
                params1.put("scope", "read"); //scope参数同样用于定义新的访问令牌的权限范围，确保客户端只能访问其被授权的资源‌
                // 发送请求，获取响应
                // 创建请求实体，设置请求头和方法
                HttpEntity<String> requestEntity = new HttpEntity<>(tmpHeaders2);
                ResponseEntity<String> response = new RestTemplate().exchange(
                        "http://localhost:8001/oauth/token?grant_type=refresh_token&refresh_token="+refreshToken,
                        HttpMethod.POST,//不能用GET
                        requestEntity, //请求实体，包含请求头和请求体
                        String.class,
                        params1 //URL中的值,没加成功
                );
                if(response==null){
                    log.info("response为空!!!!!");
                }
                else{
                    log.info("response非空!!!!");
                }

                String newResult = response.getBody();
                log.info("新获取的token:");
                log.info(newResult);

                //本地登录
                String loginId = json.getString("loginId");//登录用户
                CommUser sysUser = sysUserService.selectUserByLoginId(loginId);

                // 构建UsernamePasswordAuthenticationToken,这里密码为null，是因为提供了正确的JWT,实现自动登录
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginId, null, sysUserService.getUserAuthority(sysUser.getLoginId()));
                SecurityContextHolder.getContext().setAuthentication(token);


                ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                try {
                    loginSuccessHandler.onAuthenticationSuccess(sra.getRequest(), sra.getResponse(), token);
                }
                catch(Exception ex){}
                return json;
               /* User user = new User();
                user.setAccount("user_" + System.currentTimeMillis());
                user.setPassword("123456");
                user.setNickName(json.optString("nickName"));
                user.setMobile(json.optString("mobile"));
                user.setThirdAccount(json.optString("account"));//相当于一个openID
                user.setAccessToken(accessToken);
                user.setRefreshToken(refreshToken);
                System.out.println(" userService.addUser(user)..........");

                */
            }
            catch (RestClientResponseException e)
            {
                e.printStackTrace();
            }
        }
        catch (RestClientResponseException e)
        {
            e.printStackTrace();
        }

        return resultJson;

    }


}
//这个简易的oauth2参考下。https://blog.csdn.net/qq_33240556/article/details/141598153
