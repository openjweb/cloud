package org.openjweb.sys.api;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
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
import org.openjweb.redis.starter.util.RedisUtil;
import org.openjweb.sys.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试URL:http://localhost:8001/demo/jwt/login?loginId=admin&password=Hello0214@
 */
@RestController
//@RequestMapping("/demo/jwt") //怀疑这个有重复的？
@RequestMapping("/rest/ucenterapi/")
@Slf4j
public class JwtLoginDemoApi {

    @Autowired
    private AuthenticationManager authenticationManager; //WebSecurityConfig声明以后这里就不报红了

    @Autowired
    CommUserService sysUserService;

    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    CommApiKeyService commApiKeyService;

    @Resource
    JwtUtil jwtUtil;


    @Resource(name = "jdbcTemplateOne")
    private JdbcTemplate service;

    @RequestMapping("login")

    public String login(String loginId, String password) throws ServletException, IOException {
        CommUser sysUser = sysUserService.selectUserByLoginId(loginId);
        //UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(sysUser,password);
        // 生成一个包含账号密码的认证信息
        log.info("开始接口认证login。。。。。。。。。。。。。。");
        log.info("传入的登录账号和密码：：：");
        log.info(loginId);
        log.info(password);
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
        return "登录成功,登录账号为："+user.getLoginId();

    }


    /**
     * 这个方法vue前端生产模式调用一直有问题，有可能是/demo开头的接口有什么特殊的问题，而且vue前端生产模式
     * 调用返回{"code":200,"msg":"success","data":{"accessToken":"admin-accessToken"}，比较诡异。
     * 改为复制到CommUserApi的doLogin方法。另外vue前端的utils/request.js可使用qs.stringify(config.data)
     * 表单参数模式调用到doLogin这个接口
     *
     * @return
     * @throws ServletException
     * @throws IOException
     */

    //@RequestMapping(value="loginv2", method = {RequestMethod.POST,RequestMethod.GET    }, consumes = "application/x-www-form-urlencoded;charset=UTF-8")//匹配VUE前端参数username , produces = "application/json"
    //@RequestMapping(value="loginv2", method = {RequestMethod.POST,RequestMethod.GET    },consumes = "application/x-www-form-urlencoded;charset=UTF-8")//匹配VUE前端参数username , produces = "application/json"
    //@RequestMapping(value="loginv2", method = {RequestMethod.POST,RequestMethod.GET    },consumes = "application/x-www-form-urlencoded;charset=UTF-8")//匹配VUE前端参数username , produces = "application/json"
    // @RequestMapping(value="loginv2", method = {RequestMethod.POST,RequestMethod.GET    },consumes = "application/json;charset=UTF-8")//匹配VUE前端参数username , produces = "application/json"
    //@RequestMapping(value="loginv2", method = {RequestMethod.POST,RequestMethod.GET    } ,consumes = "application/x-www-form-urlencoded;charset=UTF-8")//匹配VUE前端参数username , produces = "application/json"
    @RequestMapping(value="jwtLogin", method = {RequestMethod.POST,RequestMethod.GET    } ,consumes = "application/x-www-form-urlencoded;charset=UTF-8")//匹配VUE前端参数username , produces = "application/json"

    //是否需要设置跨域，在npm run build后，部署到正式环境登录不了。
    //@CrossOrigin(origins = {"http://localhost","http://c0001-1.zzyicheng.cn"}) //设置为允许跨域
    //@CrossOrigin(origins = "*", allowedHeaders = "*") //设置为允许跨域
    //http://localhost:8001/demo/jwt/loginv2?username=admin&password=xxx

    //public String loginv2( @PathVariable("username") String username,@PathVariable("password") String password) throws ServletException, IOException {

    //public  @ResponseBody  String loginv2(  String username , String password ,String clienId,String sysId,String appSecret ) throws ServletException, IOException {
    //public ResponseEntity<?> loginv2(@RequestParam String username , @RequestParam String password  ) throws ServletException, IOException {
    //public @ResponseBody    JSONObject loginv2(@RequestParam String username , @RequestParam String password  ) throws ServletException, IOException {
    public @ResponseBody    JSONObject loginv2( HttpServletRequest req,HttpServletResponse resp ) throws ServletException, IOException {

            //public @ResponseBody    JSONObject loginv2(  @RequestBody JSONObject requestJson ,HttpServletRequest req,HttpServletResponse resp ) throws ServletException, IOException {
            //public @ResponseBody    JSONObject loginv2( HttpServletRequest req,HttpServletResponse resp ) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        //String username = requestJson.getString("username");
        //String password = requestJson.getString("password");
        log.info("传入的用户名和密码:");
        log.info(username);
        log.info(password);


        //public @ResponseBody    JSONObject loginv2( HttpServletRequest req,HttpServletResponse resp ) throws ServletException, IOException {
        log.info("检测是否参数格式原因...................");
        //String username = "";
        //String password = "";

    //public @ResponseBody JSONObject loginv2(HttpServletRequest request , HttpServletResponse response  ) throws ServletException, IOException {
    //public String loginv2(  CommUser param  ) throws ServletException, IOException {

        //String username = request.getHeader("username");
        //String password = request.getHeader("password");
        //String sysId = request.getParameter("sysId");

        //public String loginv2( CommUser param ) throws ServletException, IOException {
        //String username = param.getUsername();
        //String password = param.getPassword();
        //String username = request.getParameter("username");
        //String password = request.getParameter("password");


        //log.info("开始接口认证loginv2。。。。。。。。。。。。。。");
        //log.info("传入的登录账号和密码：：：");
        //log.info(username);
        //log.info(password);
        log.info("调用SpringBoot登录接口.................");
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        HttpServletResponse response = sra.getResponse();
        String domainName= CMSUtil.getDomainName(request);
        if(username!=null&&username.equals("code")){
            //检查头部是否有jwttoken
            log.info("检查头部是否有token");
            log.info("根据code查询对应的用户...............");

            //这种情况是微信的带access_token登录
            Claims claims = jwtUtil.getClaimsByToken(password);
            String loginId = claims.getSubject();
            log.info("解析的登录账号："+loginId);
            try{

                List<GrantedAuthority> authorities = sysUserService.getUserAuthority(loginId);
                log.info("权限列表:::1::");
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        loginId,
                        null, // 不需要密码
                        authorities
                );
                log.info("权限列表:::2::");
                SecurityContextHolder.getContext().setAuthentication(authentication);
                //CommUser user = (CommUser) authentication.getPrincipal();
                //log.info("通过access_token获得账号:"+user.getLoginId());

                //ServletContext().
                //loginSuccessHandler.onAuthenticationSuccess(request,response,authentication);
                String validKey = "";//IP+验证私钥:IP+userId并MD5
                String ip = request.getRemoteAddr();//这个需要换一个获取方式？
                String nonce = StringUtil.getUUID();
                String timestamp = String.valueOf(System.currentTimeMillis());
                String key = service.queryForObject("select parm_value from comm_system_parms where parm_name='WeixinSSOSignKey' ",String.class).toString();
                String cookieToken = StringUtil.getUUID();
                cn.hutool.json.JSONObject dataMap = new cn.hutool.json.JSONObject();
                dataMap.put("access_token", password);
                dataMap.put("login_id", loginId);// 登录账号,可能不使用
                dataMap.put("code", 0);
                dataMap.put("msg","登录成功");
                //如果使用了随机皮肤，则返回后台随机生成的皮肤参数
                String randLayout = StringUtil.getRandName("vertical,horizontal");
                String randMenuColor = StringUtil.getRandName("dark,light");
                String randThemeColor = StringUtil.getRandName("#1890ff,#211bce,#13c2c2,#722ed1,#3eac12,#f8bc18,#f5811c,#f5222d,#784315,#F08650");
                String randMenuPng = StringUtil.getRandName("https://c0001-1.zzyicheng.cn/background.png, ");
                dataMap.put("menu_layout", randLayout);
                dataMap.put("menu_color", randMenuColor);
                dataMap.put("theme_color", randThemeColor);
                dataMap.put("menu_pic", randMenuPng);//背景图
                redisUtil.set("login-"+loginId,dataMap.toString(),120);
                String json1 = null;
                try{
                    json1 = redisUtil.get("login-"+loginId ).toString();
                }
                catch (Exception ex){}
                log.info("认证成功的json is:::");
                log.info(json1);


                return JSONObject.parseObject(json1);//为什么VUE前端获取的是空值？？？

            }
            catch(Exception ex){
                ex.printStackTrace();

            }
            //CommUser insertUser = this.sysUserService.selectUserByLoginId(openID);
            JSONObject json2 = new JSONObject();
            json2.put("code",-1);
            json2.put("msg","登录失败!!");
            return json2;

        }
        log.info("检查传参是否有access_token。。。。。。。。。。。");

        //log.info("loginV2前端传入的access_token:::");
        String accessToken = request.getParameter("access_token");
        /*boolean isGetToken = false;
        if(!StringUtil.isEmpty(accessToken)){
            //后面登录成功了通过LoginSuccess自动重新设置accessToken
            isGetToken = true;//如果是get传过来的，设置的5分钟的有效时间，登录成功后重新生成token并替换。
        }*/
        log.info("传入参数中的accessToken::::::");
        log.info(accessToken);
        log.info("头部的accessToken::");
        log.info(request.getHeader("accessToken"));
        log.info("头部的accessToken1::");
        log.info(request.getHeader("Authorization"));

        Claims claims = jwtUtil.getClaimsByToken(accessToken);

        if(claims!=null){
            username = claims.getSubject();
            log.info("根据accessToken解析的username::::");
            log.info(username);

        }
        if(!StringUtil.isEmpty(accessToken)&&claims==null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",-1);
            jsonObject.put("msg","微信登录失败!");
            return jsonObject;

        }
        //log.info(accessToken);
        /*String code = "";
        CommApiKey keyEnt = null;
        String comId = null;
        try{
            comId = service.queryForObject("select pk_id from comm_company where domain_name=?",new Object[]{domainName},String.class).toString();
        }
        catch(Exception ex){}
        if(StringUtil.isEmpty(comId)){
            comId = "C0001";
        }

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
        log.info("转换前的username:"+String.valueOf(username));
        if(username!=null&&username.equals("code")){
            //这个暂时作为微信扫码登录
            code = password;//
            //跳转获取access_token
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

            try{
                username = service.queryForObject("select login_id from comm_user where wx_open_id=?",new Object[]{openID},String.class).toString();
                List<GrantedAuthority> authorities = sysUserService.getUserAuthority(username);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        username,
                        null, // 不需要密码
                        authorities
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                CommUser user = (CommUser) authentication.getPrincipal();
                log.info("账号:"+user.getLoginId());

                //ServletContext().
                loginSuccessHandler.onAuthenticationSuccess(request,response,authentication);
                String json1 = null;
                try{
                    json1 = redisUtil.get("login-"+user.getLoginId() ).toString();
                }
                catch (Exception ex){}
                log.info("认证成功的json is:::");
                log.info(json1);


                return JSONObject.parseObject(json1);//为什么VUE前端获取的是空值？？？

            }
            catch(Exception ex){

            }
            //CommUser insertUser = this.sysUserService.selectUserByLoginId(openID);
            JSONObject json2 = new JSONObject();
            json2.put("code",-1);
            json2.put("msg","登录失败!!");
            return json2;




        }*/


        /*if(true){
            //测试
            JSONObject tmpJson = new JSONObject();
            tmpJson.put("code",0);
            tmpJson.put("msg","success");
            tmpJson.put("access_token", "12345678");
            tmpJson.put("login_id", username);// 登录账号,可能不使用
            return tmpJson;

        }*/
        log.info("SpringSecurity调用loginV2.........");
        //CommUser sysUser = sysUserService.selectUserByLoginId(username);


        CommUser user = null;
        //UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(sysUser,password);
        // 生成一个包含账号密码的认证信息
        if(claims!=null){
            username = claims.getSubject();


            List<GrantedAuthority> authorities = sysUserService.getUserAuthority(username);
            log.info("权限列表:::1::");
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null, // 不需要密码
                    authorities
            );
            log.info("权限列表:::2::");
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //CommUser user = (CommUser) authentication.getPrincipal();
            //log.info("通过access_token获得账号:"+user.getLoginId());

            //ServletContext().
            //loginSuccessHandler.onAuthenticationSuccess(request,response,authentication);
            /*String validKey = "";//IP+验证私钥:IP+userId并MD5
            String ip = request.getRemoteAddr();//这个需要换一个获取方式？
            String nonce = StringUtil.getUUID();
            String timestamp = String.valueOf(System.currentTimeMillis());
            String key = service.queryForObject("select parm_value from comm_system_parms where parm_name='WeixinSSOSignKey' ",String.class).toString();
            String cookieToken = StringUtil.getUUID();
            cn.hutool.json.JSONObject dataMap = new cn.hutool.json.JSONObject();
            dataMap.put("access_token", password);
            dataMap.put("login_id", username);// 登录账号,可能不使用
            dataMap.put("code", 0);
            dataMap.put("msg","登录成功");
            //如果使用了随机皮肤，则返回后台随机生成的皮肤参数
            String randLayout = StringUtil.getRandName("vertical,horizontal");
            String randMenuColor = StringUtil.getRandName("dark,light");
            String randThemeColor = StringUtil.getRandName("#1890ff,#211bce,#13c2c2,#722ed1,#3eac12,#f8bc18,#f5811c,#f5222d,#784315,#F08650");
            String randMenuPng = StringUtil.getRandName("https://c0001-1.zzyicheng.cn/background.png, ");
            dataMap.put("menu_layout", randLayout);
            dataMap.put("menu_color", randMenuColor);
            dataMap.put("theme_color", randThemeColor);
            dataMap.put("menu_pic", randMenuPng);//背景图
            redisUtil.set("login-"+username,dataMap.toString(),120);*/
            loginSuccessHandler.onAuthenticationSuccess(request, response, authentication);
            String json1 = null;
            try{
                json1 = redisUtil.get("login-"+username ).toString();
            }
            catch (Exception ex){}
            log.info("认证成功的json is:::");
            log.info(json1);


            return JSONObject.parseObject(json1);//为什么VUE前端获取的是空值？？？
        }
        else {


            try {
                Authentication token = new UsernamePasswordAuthenticationToken(username, password);
                Authentication authentication = authenticationManager.authenticate(token);

                //如果认证失败，不会向下走，而是跳转到登录页面，除非在WebSecurityConfig开通.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                // 将返回的Authentication存到上下文中
                SecurityContextHolder.getContext().setAuthentication(authentication);//
                //user = (CommUser) authentication.getPrincipal();
                //log.info("账号:" + user.getLoginId());

                //ServletContext().
                loginSuccessHandler.onAuthenticationSuccess(request, response, authentication);
            } catch (Exception ex) {
                ex.printStackTrace();
                JSONObject json = new JSONObject();
                json.put("code", -1);
                json.put("msg", "登录失败!");
                return json;


            }
        }
        String json = null;
        try{
            //json = redisUtil.get("login-"+user.getLoginId() ).toString();
            json = redisUtil.get("login-"+username ).toString();
        }
        catch (Exception ex){}
        log.info("认证成功的json is:::");
        log.info(json);


        return JSONObject.parseObject(json);//为什么VUE前端获取的是空值？？？



        //return "登录成功,登录账号为："+user.getLoginId();

    }
}

 /*
    *  Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getUsername(),
            loginRequest.getPassword()
        )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = tokenProvider.generateToken(authentication);
    return ResponseEntity.ok(new JwtResponse(jwt));
    * */

//https://www.jb51.net/program/328468x5z.htm
//https://blog.csdn.net/woshichenpi/article/details/143894214 自定义AuthenticationProvider

//没走认证链，还是用http调用的方式走一遍？

//SecurityContextHolder.
//https://www.cnblogs.com/d111991/p/16896151.html
//使用 AuthenticationManager 来认证用户。认证成功后，将认证后的 Authentication 对象存储在 SecurityContextHolder 中，从而实现用户登录。



        /*Authentication authenticate = authenticationManager.authenticate(authenticationToken);
 UsernamePasswordAuthenticationToken authenticationToken =  // 没有前端获取用户数据目前先这样写


        if(Objects.isNull(authenticate)) {
            throw new AuthenticationCredentialsNotFoundException("用户账号或密码错误");
        }
        else{
            log.info("登录成功的用户账号:::::");
            log.info(authenticate.getCredentials().toString());
        }
        log.info("登录成功");*/




