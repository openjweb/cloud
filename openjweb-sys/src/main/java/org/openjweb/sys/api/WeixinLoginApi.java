package org.openjweb.sys.api;


import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.util.CMSUtil;
import org.openjweb.common.util.FileUtil;
import org.openjweb.common.util.StringUtil;
import org.openjweb.core.entity.CommApiKey;
import org.openjweb.core.entity.CommUser;
import org.openjweb.core.module.params.CommApiKeyParam;
import org.openjweb.core.service.CommApiKeyService;
import org.openjweb.core.service.CommAuthService;
import org.openjweb.core.service.CommUserService;
import org.openjweb.core.util.JwtUtil;
import org.openjweb.redis.starter.util.RedisUtil;
import org.openjweb.sys.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.Enumeration;
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

    @Autowired
    CommAuthService commAuthService;



    @Resource(name = "jdbcTemplateOne")
    private JdbcTemplate service;

    @Resource
    CommApiKeyService commApiKeyService;

    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Resource
    private RedisUtil redisUtil;

    @Value("${openjweb.dev.vueMenuTemplatePath:}") private String vueMenuTemplatePath;



    //根据域名
    //https://c0001-1.zzyicheng.cn/clouds/api/weixin/login


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    //public Object wxLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
    public  @ResponseBody JSONObject wxLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //微信登录跳转
        //https://open.weixin.qq.com/connect/qrconnect?appid=wxb4231ea4d5494ff1&redirect_uri=https%3A%2F%2Fc0001-1.zzyicheng.cn%2Fclouds%2Fapi%2Fweixin%2Flogin&response_type=code&scope=snsapi_login


        String domainName= CMSUtil.getDomainName(request);

        String code = request.getParameter("code");//微信登录回传的code
        log.info("微信登录返回的Code1111:::"+String.valueOf(code));


        //根据域名查找公司
        String headToken = request.getHeader("Authorization");
        //if(headToken==null||headToken.trim().length()==0){
        //    headToken = request.getHeader("accessToken");//
        //}
        //if(headToken==null||headToken.trim().length()==0){
        //    headToken = request.getParameter ("accessToken");//

        //}
        //log.info("微信登录接口传入的accessToken:::");
        //log.info(headToken);

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
            //JSONObject tmpJson = new JSONObject();
            ////下面是个性化设置,也可以不设置
            //dataMap.put("menu_layout", "vertical");
            //dataMap.put("menu_color", "dark");
            //dataMap.put("theme_color", "#1890ff");
            //dataMap.put("menu_pic", "");//背景图
            //dataMap.put("data",tmpJson); //背景图



            //return dataMap;
            response.setHeader("accessToken",headToken);
            response.sendRedirect("https://"+domainName+"/vue/#/index");
            return null;



        }
        else{
            log.info("没有登录，开始新的登录..............");
        }
        //

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
        log.info("获取微信code::::");

        String weixinUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+"&secret="+appSecret+"&code="+code+"&grant_type=authorization_code";
        log.info("再次请求URL:::");
        log.info(weixinUrl);
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
        if(false){//暂不插入新用户
        //if(userList==null||userList.size()==0){
            //插入新的微信用户
            log.info("此微信ID为新用户，插入新微信用户................");
            this.sysUserService.addWeixinUser(comId,openID);//增加新的微信用户，同时授予角色
            log.info("插入新用户完毕.............");
            //插入成功后，给用户授予角色权限
            CommUser insertUser = this.sysUserService.selectUserByLoginId(openID);
            if(insertUser!=null){
                log.info("查到登录用户了！！！！！！！！！！！！！！！！！！！");
                log.info(insertUser.getLoginId());
            }
            else{
                log.info("没查到登录用户!!!!!!!");
            }

            Long userId = insertUser.getUserId();
            log.info("新用户的userId::"+String.valueOf(userId));
            //插入用户角色
            long workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr()) >> 16 & 31; // 获取工作节点ID
            long dataCenterId = 1L; // 设置数据中心ID
            Snowflake snowflake = IdUtil.createSnowflake(workerId, dataCenterId); // 创建雪花算法ID生成器
            long uniqueId = snowflake.nextId(); // 生成唯一ID
            log.info("生成用户角色关系开始........");
            try {
                service.update("insert into comm_user_role values(?,?,?,?,?)", new Object[]{uniqueId, userId, 505715L, StringUtil.getCurrentDateTime(), "system"});
            }
            catch(Exception ex){}
            log.info("生成用户角色关系结束........");

        }
        else{
            log.info("有微信用户,不插入新用户....1......");
        }
        log.info("openID为:::::::");
        log.info(openID);
        List<CommUser> sysUserList = this.sysUserService.selectUserByWxOpenId(openID);
        if(sysUserList!=null&&sysUserList.size()==1){
            log.info("微信用户开始自动登录....这里可以先得到一个accessToken返回前端后再做认证.....");
            CommUser sysUser = sysUserList.get(0);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(sysUser.getLoginId(), null, sysUserService.getUserAuthority(sysUser.getLoginId()));
            SecurityContextHolder.getContext().setAuthentication(token);
            log.info("微信用户开始自动登录....2222....");
            ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            try {
                //loginSuccessHandler.onAuthenticationSuccess(sra.getRequest(), sra.getResponse(), token);
                if(1==1){
                    //替换为JwtUtil的access_token??
                    //response.setHeader("accessToken","");
                    //下面生成一个AceccessTOken
                    //String accessToken = jwtUtil.generateToken()
                    log.info("微信用户开始自动登录...3333....");
                    log.info(sysUser.getLoginId());
                    String jwtToken =   jwtUtil.generateToken(sysUser.getLoginId());
                    //response.setHeader("accessToken",jwtToken);
                    //response.setHeader("Authorization",jwtToken);
                    //"http://localhost:81/#/login?access_token="+jwt;
                    //带着username和password可以自动登录
                    log.info("跳转的登录地址为：");
                    String toUrl = "https://"+domainName+"/vue/#/login?access_token="+jwtToken+"&username=code&password="+jwtToken;
                    log.info(toUrl);
                    response.sendRedirect(toUrl);

                    //response.sendRedirect("https://"+domainName+"/vue/#/index");
                    //http://localhost:81/#/login?access_token=
                    //response.sendRedirect("https://"+domainName+"/vue/#/login?access_token="+jwtToken);

                    //这种是有前端页的应该在前端植入access_token

                    /*String json1 = null;
                    try{
                        json1 = redisUtil.get("login-"+sysUser.getLoginId() ).toString();
                    }
                    catch (Exception ex){}
                    log.info("认证成功的json is:::");
                    log.info(json1);
                    return JSONObject.parseObject(json1);//为什么VUE前端获取的是空值？？？

                     */
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
        log.info("SpringBoot  getVueMenu 从头部获得的accessToken::::");

        /*Enumeration<?> enum1 = request.getHeaderNames();
        //检查头部信息

        while (enum1.hasMoreElements()) {
            String key = (String) enum1.nextElement();
            String value = request.getHeader(key);
            System.out.println("getVueMenu头部:" + key + "\t" + value);
        }

        //遍历传入参数
        Enumeration em = request.getParameterNames();
        String sql = " ";
        while (em.hasMoreElements()) {
            String name1 = (String) em.nextElement();
            String value = request.getParameter(name1);
            log.info("getVueMenu参数:" + name1 + "/" + value);

        }

        String accessToken = request.getHeader("Authorization");
        log.info(accessToken);
        if(accessToken==null||accessToken.trim().length()==0){
            accessToken = request.getParameter("accessToken");
            log.info("getVueMenu从请求参数获得accessToken 为:");
            log.info(accessToken);

        }*/
        String accessToken = request.getHeader("accesstoken");
        log.info("getVueMenu的accessToken:::");
        log.info(accessToken);
        if(StringUtil.isEmpty(accessToken)){
            log.info("从参数获取的accessToken:");
            accessToken = request.getParameter("accessToken");
            log.info(accessToken);
        }

        String loginId = null;
        Claims claims = jwtUtil.getClaimsByToken(accessToken);
        try{
            loginId = claims.getSubject();
        }
        catch(Exception ex){}
        log.info("getVueMenu根据accessToken解析的登录账号:::::");
        log.info(loginId);
        //创建功能菜单
        String menuJs = "";
        JSONObject json = null;
        // JSONArray array = new JSONArray();

        // logger.info("检测getMenu3......................");
        String menuColor = "";
        String themeColor = "";
        String menuBackPic = "";
        String layout = "";
        String menuFilePath = "";
        //D:\project\openjweb\webapps\WEB-INF\config

        //获取不需要授权的menu菜单

        //String menuFilePath = request.getRealPath("/") + "/WEB-INF/config/menu-vue-v2.js";

        try {
            // logger.info("文件读取menuJs:::::");
            menuJs = FileUtil.getTextFileContent(vueMenuTemplatePath, "utf-8");

            JSONObject json1 = JSONObject.parseObject(menuJs);
            //logger.info("从数据库查菜单开始..,yonghu......");
            //logger.info(loginId);

            JSONArray tmpArray = commAuthService.getVueMenu(loginId);

            //logger.info("从数据库查菜单结束........");

            if (tmpArray != null && tmpArray.size() > 0) {
                JSONArray originArray = json1.getJSONArray("data");
                for (int i = 0; i < tmpArray.size(); i++) {

                    originArray.add(tmpArray.getJSONObject(i));
                }
                json1.put("data", originArray);
                //
                menuJs = json1.toJSONString();
                //String tmpStr = tmpArray.toJSONString();
                //menuJs = menuJs.replace("//SUB_MENU", ","+tmpStr);
            } else {
                //menuJs = menuJs.replace("//SUB_MENU","");
            }
            try {
                // logger.info("开始固定菜单与权限菜单合并......");
                json = JSONObject.parseObject(menuJs);
                JSONArray array = (JSONArray) json.getJSONArray("data");
                // 系统自己的需要在data项下添加。
                // logger.info("从当前用户获取权限菜单");

                json.put("data", array);// 合并系统菜单和固定菜单
                json.put("code", 0);
                json.put("msg", "调用成功");
                json.put("status", "success");

            } catch (Exception ex) {
            }

            log.info("得到的menuJs:::");
            log.info(menuJs);
            // logger.info("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

        } catch (Exception ex) {
            ex.printStackTrace();
        }







        //查询此accessToken对应的loginId

        return json;
    }




}
