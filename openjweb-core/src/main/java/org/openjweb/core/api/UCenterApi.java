package org.openjweb.core.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.util.CMSUtil;
import org.openjweb.common.util.StringUtil;
import org.openjweb.core.entity.CommUser;
import org.openjweb.core.service.CommUserService;
import org.openjweb.core.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/comm/user")
@RestController
@Slf4j
public class UCenterApi {
    @Autowired
    CommUserService userService;

    @Autowired
    JwtUtil jwtUtils;

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
        log.info("调用getUserInfo2............");


        Enumeration em = request.getParameterNames();
        String sql = " ";
        while (em.hasMoreElements()) {
            String name1 = (String) em.nextElement();
            String value = request.getParameter(name1);
            log.info("getUserInfo2参数:" + name1 + "/" + value);
            if (name1.startsWith("cond")) {
                //作为查询条件
                //sql += " and "+
            }
        }

        Enumeration<?> enum1 = request.getHeaderNames();
        //检查头部信息

        while (enum1.hasMoreElements()) {
            String key = (String) enum1.nextElement();
            String value = request.getHeader(key);
            System.out.println("getUserInfo2参数头部:" + key + "\t" + value);
        }



        //先测试.............

        JSONObject resultJson = new JSONObject();
        log.info("springboot 获取accessToken........");
        String accessToken = request.getHeader("Authorization");
        if(StringUtils.isEmpty(accessToken)){
            log.info("头部没取到accessToken改为从参数获取::::::::::::");
            accessToken = request.getParameter("accessToken");

        }
        if(StringUtil.isEmpty(accessToken)){
            accessToken = request.getHeader("accesstoken");
        }
        log.info("springboot getUserInfo2 获取的accessToken为：：：");
        log.info(accessToken);
        String loginId = "";


        if(accessToken!=null&&accessToken.trim().length()>0){

            Claims claims = jwtUtils.getClaimsByToken(accessToken);
            if(claims!=null){
                log.info("claims非空。。。。。。。。。。。。。");
                loginId = claims.getSubject();
            }
            else{
                log.info("claims为空..........");
            }
        }
        //根据jwt的accessToken获取登录账号
        log.info("getuserInfo2得到的登录账号::::");
        log.info(loginId);

        JSONArray permArray = new JSONArray();// 角色列表
        permArray.add("guest");
        JSONObject map = new JSONObject();

        map.put("code", 0);
        map.put("msg", "");
        Map datamap = new HashMap();
        //CommUser user =
        CommUser user = null;
        try{
            user = userService.selectUserByLoginId(loginId);
        }
        catch (Exception ex){
            log.error("查询用户失败:"+ex.toString());

        }
        if(user!=null){
            log.info("用户非空...........................");

            String sex = user.getUserSex();
            if (sex != null && sex.equals("M")) {
                sex = "男";
            } else if (sex != null && sex.equals("M")) {
                sex = "女";
            } else {
                sex = "";
            }

            datamap.put("nickname", user.getNickName() == null ? loginId : user.getNickName());// 昵称


            if (loginId.length() > 11) {
                String mobile = loginId.substring(loginId.length() - 11);
                datamap.put("username", mobile);// 登录账号

            } else {
                datamap.put("username", loginId);// 登录账号
            }
            datamap.put("realName", user.getRealName() == null ? "" : user.getRealName());
            String mobileNo = user.getUserMobile() == null ? "" : user.getUserMobile();
            if (mobileNo != null && mobileNo.indexOf("_") > -1) {
                mobileNo = mobileNo.substring(mobileNo.indexOf("_") + 1);
            } else {
                if (mobileNo.trim().length() > 11) {
                    mobileNo = "";
                }
            }

            datamap.put("mobile", mobileNo);
            datamap.put("comName", user.getComName() == null ? "" : user.getComName());
            datamap.put("createDt", user.getCreateDt());
            datamap.put("lastLoginDt", user.getLastLoginDt() == null ? "" : user.getLastLoginDt());
            datamap.put("psnPhotoPath", user.getPsnPhotoPath() == null ? "" : user.getPsnPhotoPath());

            String img = user.getPsnPhotoPath() == null ? "" : user.getPsnPhotoPath();
            if (img.length() > 0 && !img.startsWith("http")) {
                img = "https://" + CMSUtil.getDomainName(request) + img;
            }
            datamap.put("avatar", img);// 头像
            datamap.put("remarks", user.getSignMsg() == null ? "" : user.getSignMsg());
            String isRandSkin = user.getIsRandSkin();

            if ("Y".equals(isRandSkin)) {
                datamap.put("isRandSkin", "是");
            } else {
                datamap.put("isRandSkin", "否");
            }

            String mobile = user.getRegistMobile() == null ? "" : user.getRegistMobile();
            datamap.put("cellphone", mobile);// 手机号
            datamap.put("email", user.getUserEmail());// 邮箱
            datamap.put("sex", user.getUserSex() == null ? "" : user.getUserSex());
            datamap.put("idImage1", user.getIdImage1() == null ? "" : user.getIdImage1());
            datamap.put("idImage2", user.getIdImage2() == null ? "" : user.getIdImage2());
            datamap.put("isEmailValid", user.getIsEmailValid() == null ? "N" : user.getIsEmailValid());
            String isValidIdcard = user.getIsValidIdcard();
            if ("1".equals(isValidIdcard)) {
                datamap.put("isValidIdcardName", "是");
            } else {
                datamap.put("isValidIdcardName", "否");
            }

        }
        else {
            log.info("用户为空..............");
            //datamap.put("username", "");
            datamap.put("nickname", "游客");
            datamap.put("username", "游客");//必须的
            datamap.put("realName", "测试");
            datamap.put("mobile", "");
            datamap.put("comName", "");
            datamap.put("createDt", "");
            datamap.put("lastLoginDt", "");
            datamap.put("psnPhotoPath", "");
            datamap.put("avatar", "");
            datamap.put("remarks", "");
            datamap.put("isRandSkin", "");
        }


        datamap.put("permission", permArray);//

        map.put("data", datamap);
        //datamap.put("permission", permArray);
        return map;





    }




}
