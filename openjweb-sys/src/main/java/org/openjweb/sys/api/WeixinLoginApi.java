package org.openjweb.sys.api;


import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.util.CMSUtil;
import org.openjweb.common.util.StringUtil;
import org.openjweb.core.entity.CommApiKey;
import org.openjweb.core.entity.CommUser;
import org.openjweb.core.module.params.CommApiKeyParam;
import org.openjweb.core.service.CommApiKeyService;
import org.openjweb.core.service.CommUserService;
import org.openjweb.core.util.JwtUtil;
import org.openjweb.sys.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @Resource
    JwtUtil jwtUtil;



    @Resource(name = "jdbcTemplateOne")
    private JdbcTemplate service;

    @Resource
    CommApiKeyService commApiKeyService;

    @Autowired
    LoginSuccessHandler loginSuccessHandler;



    //根据域名
    //https://c0001-1.zzyicheng.cn/clouds/api/weixin/login


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Object wxLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //根据域名查找公司
        String domainName= CMSUtil.getDomainName(request);
        String headToken = request.getHeader("Authorization");
        JSONObject dataMap = new JSONObject();

        if(headToken!=null&&headToken.trim().length()>0){
            log.info("已经登录过，返回dataMap............");
            //如果已经有了登录的token
            dataMap.put("access_token", headToken);
            String  loginId = null;
            Claims claims = jwtUtil.getClaimsByToken(headToken);
            try{
                loginId = claims.getSubject();
            }
            catch(Exception ex){}

            dataMap.put("login_id",loginId);// 登录账号,可能不使用
            dataMap.put("code",0);
            dataMap.put("msg","登录成功!");//国际化的话不能直接这么写
            /*JSONObject tmpJson = new JSONObject();
            //下面是个性化设置,也可以不设置
            dataMap.put("menu_layout", "vertical");
            dataMap.put("menu_color", "dark");
            dataMap.put("theme_color", "#1890ff");
            dataMap.put("menu_pic", "");//背景图
            dataMap.put("data",tmpJson); //背景图

             */


            return dataMap;


        }
        else{
            log.info("没有登录，开始新的登录..............");
        }
        //
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
            log.info("此微信ID为新用户，插入新微信用户................");
            this.sysUserService.addWeixinUser(comId,openID);//增加新的微信用户，同时授予角色
            log.info("插入新用户完毕.............");
            //插入成功后，给用户授予角色权限
            CommUser insertUser = this.sysUserService.selectUserByLoginId(openID);
            Long userId = insertUser.getUserId();
            log.info("新用户的userId::"+String.valueOf(userId));
            //插入用户角色
            long workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr()) >> 16 & 31; // 获取工作节点ID
            long dataCenterId = 1L; // 设置数据中心ID
            Snowflake snowflake = IdUtil.createSnowflake(workerId, dataCenterId); // 创建雪花算法ID生成器
            long uniqueId = snowflake.nextId(); // 生成唯一ID
            log.info("生成用户角色关系开始........");
            service.update("insert into comm_user_role values(?,?,?,?,?)",new Object[]{uniqueId,userId,505715L,StringUtil.getCurrentDateTime(),"system"});
            log.info("生成用户角色关系结束........");

        }
        else{
            log.info("有微信用户,不插入新用户..........");
        }
        log.info("openID为:::::::");
        log.info(openID);
        List<CommUser> sysUserList = this.sysUserService.selectUserByWxOpenId(openID);
        if(sysUserList!=null&&sysUserList.size()==1){
            log.info("微信用户开始自动登录.........");
            CommUser sysUser = sysUserList.get(0);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(sysUser.getLoginId(), null, sysUserService.getUserAuthority(sysUser.getLoginId()));
            SecurityContextHolder.getContext().setAuthentication(token);
            ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            try {
                loginSuccessHandler.onAuthenticationSuccess(sra.getRequest(), sra.getResponse(), token);
                if(1==1){
                    response.sendRedirect("https://"+domainName+"/vue/#/index");
                }
            }
            catch(Exception ex){}
            //location.href='https://c0001-1.zzyicheng.cn/portal/common/weixinloginpcssl.jsp?ssourl=https%3A%2F%2Fc0001-1.zzyicheng.cn%2Fportal%2Fcommon%2Fweixinloginpcsso.jsp&callbackurl=&bizLoginUrl=https%3A%2F%2Fc0001-1.zzyicheng.cn%2Fvue%2F%23%2Flogin';
        }
        else{
            log.info("没有查到这个用户.......................");
            response.sendRedirect("https://"+domainName+"/vue/#/login");
        }
        return null;

    }
    @RequestMapping(value = "/getVueMenu", method = {RequestMethod.POST, RequestMethod.GET}) // ,RequestMethod.GET
    public @ResponseBody
    Object getVueMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("getVueMenu 从头部获得的accessToken::::");
        String accessToken = request.getHeader("Authorization");
        log.info(accessToken);
        String loginId = null;
        Claims claims = jwtUtil.getClaimsByToken(accessToken);
        try{
            loginId = claims.getSubject();
        }
        catch(Exception ex){}
        log.info("根据accessToken解析的登录账号:::::");
        log.info(loginId);
        //创建功能菜单
        log.info("创建功能菜单：：：：：：：");
        JSONObject json = new JSONObject();
        json.put("code",-1);
        json.put("msg","获取菜单失败..............");




        //查询此accessToken对应的loginId

        return json;
    }




}
