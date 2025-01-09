package org.openjweb.core.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.core.service.CommUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/comm/user")
@RestController
@Slf4j
public class UCenterApi {
    @Autowired
    CommUserService userService;

    //http://localhost:8001/api/comm/user/login

    //https://c0001-1.zzyicheng.cn/clouds/api/comm/user/login
    //http://c0001-1.zzyicheng.cn:8001/api/comm/user/login 可以

    @RequestMapping("login")
    public String login(String loginId){
        //在这里获取前端传入的登录账号、密码、图片验证码等

        return "hi";
    }

    @RequestMapping(value = "/getUserInfo2", method = {RequestMethod.GET,
            RequestMethod.POST}, produces = "application/json")
    public @ResponseBody
    JSONObject getUserInfo2(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        //先测试.............

        JSONObject resultJson = new JSONObject();
        log.info("springboot 获取accessToken........");
        String accessToken = request.getHeader("Authorization");
        if(StringUtils.isEmpty(accessToken)){
            log.info("头部没取到accessToken改为从参数获取::::::::::::");
            accessToken = request.getParameter("access_token");

        }
        log.info("springboot 获取的accessToken为：：：");
        log.info(accessToken);
        JSONArray permArray = new JSONArray();// 角色列表
        permArray.add("guest");
        JSONObject map = new JSONObject();

        map.put("code", 0);
        map.put("msg", "");
        Map datamap = new HashMap();
        //datamap.put("username", "");
        datamap.put("username", "游客");
        datamap.put("realName", "");
        datamap.put("mobile", "");
        datamap.put("comName", "");
        datamap.put("createDt", "");
        datamap.put("lastLoginDt", "");
        datamap.put("psnPhotoPath", "");
        datamap.put("avatar", "");
        datamap.put("remarks", "");
        datamap.put("isRandSkin", "");


        datamap.put("permission", permArray);//

        map.put("data", datamap);
        //datamap.put("permission", permArray);
        return map;





    }




}
