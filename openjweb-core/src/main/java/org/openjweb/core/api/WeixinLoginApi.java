package org.openjweb.core.api;


import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.util.CMSUtil;
import org.openjweb.common.util.StringUtil;
import org.openjweb.core.entity.CommApiKey;
import org.openjweb.core.entity.CommUser;
import org.openjweb.core.module.params.CommApiKeyParam;
import org.openjweb.core.service.CommApiKeyService;
import org.openjweb.core.service.CommUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequestMapping("/api/weixin")
@RestController
@Slf4j
public class WeixinLoginApi {

    @Autowired
    private AuthenticationManager authenticationManager; //WebSecurityConfig声明以后这里就不报红了

    @Autowired
    CommUserService sysUserService;



    @Resource(name = "jdbcTemplateOne")
    private JdbcTemplate service;

    @Resource
    CommApiKeyService commApiKeyService;
    //根据域名
    //https://c0001-1.zzyicheng.cn/clouds/api/weixin/login


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void wxLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //根据域名查找公司
        String domainName= CMSUtil.getDomainName(request);
        //微信登录跳转
        //https://open.weixin.qq.com/connect/qrconnect?appid=wxb4231ea4d5494ff1&redirect_uri=https%3A%2F%2Fc0001-1.zzyicheng.cn%2Fclouds%2Fapi%2Fweixin%2Flogin&response_type=code&scope=snsapi_login


        String comId = null;
        try{
          comId = service.queryForObject("select pk_id from comm_company where domain_name=?",new Object[]{domainName},String.class).toString();
        }
        catch(Exception ex){}
        if(StringUtil.isEmpty(comId)){
            comId = "C0001";
        }
        //根据公司ID获取微信开放平台的APPID
        CommApiKey keyEnt = null;

        try{
            CommApiKeyParam param = new CommApiKeyParam();
            param.setComId(comId);
            param.setApiType("OPENWEIXIN");
            List<CommApiKey> list =  commApiKeyService.queryList(param);
            log.info("查到的commApiKey的数量：：：：：");
            log.info(String.valueOf(list.size()));
            keyEnt = list.get(0);
        }
        catch(Exception ex){}
        String appId = "";//微信开放平台APPID
        String appSecret = "";//微信开放平台APP密钥
        if(keyEnt!=null) {
            log.info("查到了微信开放平台配置,appId和密钥：........");
            appId = keyEnt.getAppId()==null?"":keyEnt.getAppId();
            appSecret =  keyEnt.getAppSecret()==null?"":keyEnt.getAppSecret();
            log.info(appId);
            log.info(appSecret);

        }
        else{
            log.info("没查到微信开放平台配置..............");
        }

        String code = request.getParameter("code");//微信登录回传的code
        String weixinUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+"&secret="+appSecret+"&code="+code+"&grant_type=authorization_code";
        String result = HttpRequest.get(weixinUrl)   .timeout(6000).execute().body();
        log.info("微信返回结果：");
        log.info(result);
        JSONObject json = JSONObject.parseObject(result);
        String accessToken = json.getString("access_token");//获取accessToken
        String openID =json.getString("openid");
        log.info("返回的accessToken和openId:::");
        log.info(accessToken);
        log.info(openID);// openId对应comm_user的wx_open_id?
        //sysUserService.
        List<CommUser> userList = this.sysUserService.selectUserByWxOpenId(openID);
        if(userList!=null&&userList.size()==1){
            log.info("有且仅有一个用户............");
        }
        else{
            log.info("没有或有多个..................");
        }
        if(userList==null||userList.size()==0){
            //插入新的微信用户
            this.sysUserService.addWeixinUser(comId,openID);//增加新的微信用户，同时授予角色

        }

        /*CommUser sysUser = sysUserService.selectUserByLoginId(loginId);
        //UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(sysUser,password);
        // 生成一个包含账号密码的认证信息
        log.info("开始接口认证。。。。。。。。。。。。。。");
        Authentication token = new UsernamePasswordAuthenticationToken(loginId,password);


        Authentication authentication = authenticationManager.authenticate(token);
        //如果认证失败，不会向下走，而是跳转到登录页面，除非在WebSecurityConfig开通.authenticationEntryPoint(jwtAuthenticationEntryPoint)


        // 将返回的Authentication存到上下文中
        SecurityContextHolder.getContext().setAuthentication(authentication);//
        CommUser user = (CommUser) authentication.getPrincipal();
        log.info("账号:"+user.getLoginId());
        //ServletContext().
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        loginSuccessHandler.onAuthenticationSuccess(sra.getRequest(),sra.getResponse(),authentication);
        return "登录成功,登录账号为："+user.getLoginId();*/


        //87_eI_XDWwLR8CKcPTqAZZ5Ogj4hDVSB-Kq3-lVjGrXl40GyIIR1qPQFwoaa5siqx2voKXjbZkARI6ozQeh3Onq-3r7m0GdMSb4QsUxursKVGI
        //oAXet0pO4E1EuJn-TUf9LI66zkdU
        //然后在这一步调用oauth认证
        //是否根据扫码的openId自动注册用户??????
        //获得到accessToken和openID之后，传给统一认证
        //https://c0001-1.zzyicheng.cn/clouds/api/comm/user/login

        //构造URL解析accessToken和openid
        //String url = "http://localhost:8001/oauth/authorize?response_type=code&client_id=zzyc&redirect_uri=http://localhost:8001/demo/oauth2client/callback";
        //response.sendRedirect(url);
    }
}
