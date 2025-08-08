package org.openjweb.sys.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.util.CMSUtil;
import org.openjweb.common.util.StringUtil;
import org.openjweb.core.entity.CommCompany;
import org.openjweb.core.service.CommCompanyService;
import org.openjweb.core.service.CommUserService;
import org.openjweb.redis.starter.util.RedisUtil;
import org.openjweb.sys.entity.CommUser;
import org.openjweb.sys.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/ucenter")
@Api(tags = "admin-用户管理")
@Slf4j
public class CommUserApi {

    @Autowired
    private AuthenticationManager authenticationManager; //WebSecurityConfig声明以后这里就不报红了

    @Autowired
    CommUserService sysUserService;

    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    CommCompanyService comService;


    @ApiOperation("用户详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名称", paramType = "query"),
            @ApiImplicitParam(name = "province", value = "所属省份", paramType = "query")
    })

    @GetMapping("info")

    public CommUser getUserInfo(@RequestParam(value = "username")String username, @RequestParam(value = "province")String province){
    //public CommUser getUserInfo( String username, String province){

        CommUser user = new CommUser();
        user.setUserSex("男");
        user.setUsername(username);
        user.setRealName("王先生");
        user.setLoginId("abao");
        return user;
    }

    @RequestMapping(value = "/testlogin1", method = {RequestMethod.POST, RequestMethod.GET}) // ,RequestMethod.GET
    public @ResponseBody
    JSONObject testlogin(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

            //测试
            log.info(request.getParameter("username"));
            log.info(request.getParameter("password"));
            JSONObject tmpJson = new JSONObject();
            tmpJson.put("code",0);
            tmpJson.put("msg","success");
            tmpJson.put("access_token", "12345678");
            tmpJson.put("login_id", "admin");// 登录账号,可能不使用
            return tmpJson;


    }

    @RequestMapping(value="doLogin", method = {RequestMethod.POST     })//匹配VUE前端参数username , produces = "application/json"
    //public @ResponseBody    JSONObject login(@RequestParam String username , @RequestParam String password  ) throws ServletException, IOException {
    public @ResponseBody    JSONObject login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //http://localhost:8001/ucenter/login?username=admin&password=xxx
        String username =   request.getParameter("username");
        String password = request.getParameter("password");


        //log.info("开始接口认证CommUserApi login。。。。。。。。。。。。。");
        //log.info("传入的登录账号和密码：：：");
        //log.info(username);
        //log.info(password);

        //org.openjweb.core.entity.CommUser sysUser = sysUserService.selectUserByLoginId(username);
        //UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(sysUser,password);
        // 生成一个包含账号密码的认证信息
        Authentication token = new UsernamePasswordAuthenticationToken(username,password);
        Authentication authentication = authenticationManager.authenticate(token);
        //如果认证失败，不会向下走，而是跳转到登录页面，除非在WebSecurityConfig开通.authenticationEntryPoint(jwtAuthenticationEntryPoint)
        // 将返回的Authentication存到上下文中
        SecurityContextHolder.getContext().setAuthentication(authentication);//
        org.openjweb.core.entity.CommUser user = (org.openjweb.core.entity.CommUser) authentication.getPrincipal();
        log.info("账号:"+user.getLoginId());

        //ServletContext().
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        loginSuccessHandler.onAuthenticationSuccess(sra.getRequest(),sra.getResponse(),authentication);
        String json = null;
        try{
            json = redisUtil.get("login-"+user.getLoginId() ).toString();
        }
        catch (Exception ex){}
        log.info("认证成功的json is:::");
        log.info(json);


        return JSONObject.parseObject(json);//为什么VUE前端获取的是空值？？？



        //return "登录成功,登录账号为："+user.getLoginId();

    }





    @RequestMapping(value = "/getVueLoginStyle", method = {RequestMethod.POST, RequestMethod.GET}) // ,RequestMethod.GET
    public @ResponseBody
    JSONObject getVueLoginStyle(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
            log.info("SpringBoot调用getVueLoginStyle...........");
            String appId = request.getParameter("appId") == null ? "" : request.getParameter("appId");// 登录账号，可根据登录账号设置登录页样式
            JSONObject dataJson = new JSONObject();
            JSONObject resultJson = new JSONObject();
            String isExcelMenu = "false";// 是否excel传来的
            String cookieValue = "";
            String appName = "";
            String appIcon = "";
            String domainName = CMSUtil.getDomainName(request);
            CommCompany comEnt = null;
            try{
                QueryWrapper wrapper = new QueryWrapper<>();
                wrapper.eq("domain_name",domainName);
                comEnt = comService.getOne(wrapper);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            if(comEnt==null){
                log.info("公司为空!!!!!查询域名：");
                log.info(domainName);
            }
            else{
                log.info("公司不为空");
            }
            if(comEnt==null)comEnt = new CommCompany();

            String logoPath = "";
            String sysName = StringUtil.isEmpty(comEnt.getSysName())?"业务系统":comEnt.getSysName();
            String contactTel  = StringUtil.isEmpty(comEnt.getContactTel())?"":comEnt.getContactTel();
            String contactMail  = StringUtil.isEmpty(comEnt.getEmailAddr())?"":comEnt.getEmailAddr();
            String comName = StringUtil.isEmpty(comEnt.getComName())?"":comEnt.getComName();

            //登录页背景图暂可以不设
            resultJson.put("code", 0);
            resultJson.put("msg", "接口调用成功!");
            dataJson.put("contract", "这里是用户协议........");// 用户协议文本,暂不需要
            dataJson.put("appId", appId);// 参数回传
            dataJson.put("picPath","");//暂不从后台取背景图

            dataJson.put("contactMsg","技术支持："+contactMail);
            dataJson.put("comName", comName);
            dataJson.put("sysName", sysName);
            dataJson.put("showSinaButton", "N");// 是否显示新浪按钮，是为Y N 为否 默认显示
            dataJson.put("showWeixinButton", "Y");// 是否显示微信按钮，是为Y N 为否 默认显示
            dataJson.put("showQQButton", "N");// 是否显示QQ按钮，是为Y N 为否 默认显示
            dataJson.put("sinaQRImage", "");// 新浪二维码扫码图片
            dataJson.put("qqQRImage", "");// QQ二维码扫码图片
            dataJson.put("weixinQRImage", "");// 微信二维码扫码图片
            dataJson.put("sinaLoginUrl", "");// 新浪登录链接（备用）
            dataJson.put("qqLoginUrl", "");// QQ登录链接（备用）
            dataJson.put("weixinLoginUrl", "");// 微信登录链接（备用）
            dataJson.put("logoPath", logoPath);// 登录页图标链接
            dataJson.put("serviceWeixin", "contactTel");// 客服微信
            dataJson.put("telNo", contactTel);// 联系电话
            dataJson.put("domainName", "https://"+domainName);// 域名
            dataJson.put("contactTel", "联系电话:"+contactTel);// 联系信息
            dataJson.put("icpNo", "");// 备案信息京ICP备17048902号  暂不写备案号



        /*
        if (appId.equals("1")) {
                resultJson.put("code", 0);
                resultJson.put("msg", "接口调用成功!");
                dataJson.put("contract", "这里是用户协议........");// 用户协议文本
                dataJson.put("appId", appId);// 参数回传
                //dataJson.put("picPath", "https://"+domainName+"/vue/static/img/background.c7e07de7.jpg");// 背景图片
                //dataJson.put("contactMsg", "客服微信:openjweb");// 联系信息
                dataJson.put("comName", "北京众智益成科技有限公司");
                dataJson.put("sysName", (sysName == null || sysName.trim().length() == 0) ? "后台管理" : sysName);
                dataJson.put("showSinaButton", "N");// 是否显示新浪按钮，是为Y N 为否 默认显示
                dataJson.put("showWeixinButton", "Y");// 是否显示微信按钮，是为Y N 为否 默认显示
                dataJson.put("showQQButton", "N");// 是否显示QQ按钮，是为Y N 为否 默认显示
                dataJson.put("sinaQRImage", "");// 新浪二维码扫码图片
                dataJson.put("qqQRImage", "");// QQ二维码扫码图片
                dataJson.put("weixinQRImage", "");// 微信二维码扫码图片
                dataJson.put("sinaLoginUrl", "");// 新浪登录链接（备用）
                dataJson.put("qqLoginUrl", "");// QQ登录链接（备用）
                dataJson.put("weixinLoginUrl", "");// 微信登录链接（备用）
                dataJson.put("logoPath", logoPath);// 登录页图标链接
                dataJson.put("serviceWeixin", "12345678");// 客服微信
                dataJson.put("telNo", "");// 联系电话
                dataJson.put("domainName", "http://www.zzyicheng.cn");// 域名
                dataJson.put("contactTel", "联系电话:");// 联系信息
                dataJson.put("icpNo", "京ICP备17048902号");// 备案信息

            } else if (appId.equals("2")) {
                resultJson.put("code", 0);
                resultJson.put("msg", "接口调用成功!");
                dataJson.put("contract", "这里是用户协议........");// 用户协议文本
                dataJson.put("appId", appId);// 参数回传
                //dataJson.put("picPath", "https://"+domainName+"/vue/static/img/background.c7e07de7.jpg");// 背景图片
                //dataJson.put("contactMsg", "客服微信:openjweb");// 联系信息
                dataJson.put("contactTel", "联系电话:18600000000");// 联系信息
                dataJson.put("comName", "北京众智益成科技有限公司");
                dataJson.put("sysName", (sysName == null || sysName.trim().length() == 0) ? "微商城系统" : sysName);

                dataJson.put("showSinaButton", "N");// 是否显示新浪按钮，是为Y N 为否 默认显示
                dataJson.put("showWeixinButton", "Y");// 是否显示微信按钮，是为Y N 为否 默认显示
                dataJson.put("showQQButton", "N");// 是否显示QQ按钮，是为Y N 为否 默认显示
                dataJson.put("sinaQRImage", "");// 新浪二维码扫码图片
                dataJson.put("qqQRImage", "");// QQ二维码扫码图片
                dataJson.put("weixinQRImage", "");// 微信二维码扫码图片
                dataJson.put("sinaLoginUrl", "");// 新浪登录链接（备用）
                dataJson.put("qqLoginUrl", "");// QQ登录链接（备用）
                dataJson.put("weixinLoginUrl", "");// 微信登录链接（备用）
                dataJson.put("logoPath", logoPath);// 登录页图标链接
                dataJson.put("contactTel", "联系电话:");// 联系信息
                dataJson.put("serviceWeixin", "12345678");// 客服微信
                dataJson.put("telNo", "");// 联系电话
                dataJson.put("domainName", "http://www.zzyicheng.cn");// 域名
                dataJson.put("icpNo", "京ICP备17048902号");

            } else { // 其他
                resultJson.put("code", 0);
                resultJson.put("msg", "接口调用成功!");
                dataJson.put("contract", "这里是用户协议........");// 用户协议文本
                dataJson.put("appId", appId);// 参数回传
                dataJson.put("picPath", "https://c0001-1.zzyicheng.cn/vue/static/img/background.c7e07de7.jpg");// 背景图片
                dataJson.put("contactMsg", "客服微信:openjweb");// 联系信息
                dataJson.put("contactTel", "联系电话:18600000000");// 联系信息
                dataJson.put("comName", "北京众智益成科技有限公司");
                dataJson.put("sysName", (sysName == null || sysName.trim().length() == 0) ? "业务管理平台" : sysName);

                dataJson.put("showSinaButton", "Y");// 是否显示新浪按钮，是为Y N 为否 默认显示
                dataJson.put("showWeixinButton", "Y");// 是否显示微信按钮，是为Y N 为否 默认显示
                dataJson.put("showQQButton", "Y");// 是否显示QQ按钮，是为Y N 为否 默认显示
                dataJson.put("sinaQRImage", "");// 新浪二维码扫码图片
                dataJson.put("qqQRImage", "");// QQ二维码扫码图片
                dataJson.put("weixinQRImage", "");// 微信二维码扫码图片
                dataJson.put("sinaLoginUrl", "");// 新浪登录链接（备用）
                dataJson.put("qqLoginUrl", "");// QQ登录链接（备用）
                dataJson.put("weixinLoginUrl", "");// 微信登录链接（备用）
                dataJson.put("logoPath", logoPath);// 登录页图标链接
                dataJson.put("contactTel", "联系电话:");// 联系信息
                dataJson.put("serviceWeixin", "12345678");// 客服微信
                dataJson.put("telNo", "");// 联系电话
                dataJson.put("domainName", "http://www.zzyicheng.cn");// 域名
                dataJson.put("icpNo", "京ICP备17048902号");

                // 其他
                resultJson.put("code", 0);
                resultJson.put("msg", "接口调用成功!");
            }*/
            resultJson.put("data", dataJson);

            return resultJson;


    }
}
