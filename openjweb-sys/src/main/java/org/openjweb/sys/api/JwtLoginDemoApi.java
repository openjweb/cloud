package org.openjweb.sys.api;

import lombok.extern.slf4j.Slf4j;
import org.openjweb.core.entity.CommUser;
import org.openjweb.core.service.CommUserService;
import org.openjweb.sys.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 测试URL:http://localhost:8001/demo/jwt/login?loginId=admin&password=Hello0214@
 */
@RestController
@RequestMapping("/demo/jwt")
@Slf4j
public class JwtLoginDemoApi {

    @Autowired
    private AuthenticationManager authenticationManager; //WebSecurityConfig声明以后这里就不报红了

    @Autowired
    CommUserService sysUserService;

    @Autowired
    LoginSuccessHandler loginSuccessHandler;

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

    @RequestMapping("loginv2")//匹配VUE前端参数username
    //是否需要设置跨域，在npm run build后，部署到正式环境登录不了。
    //@CrossOrigin(origins = {"http://localhost","http://c0001-1.zzyicheng.cn"}) //设置为允许跨域
    //@CrossOrigin(origins = "*", allowedHeaders = "*") //设置为允许跨域

    //public String loginv2( @PathVariable("username") String username,@PathVariable("password") String password) throws ServletException, IOException {

    //public  @ResponseBody  String loginv2(  String username , String password ,String clienId,String sysId,String appSecret ) throws ServletException, IOException {
    public ResponseEntity<?> loginv2(@RequestParam String username , @RequestParam String password  ) throws ServletException, IOException {

        //public @ResponseBody String loginv2(  HttpServletRequest request  ) throws ServletException, IOException {
    //public String loginv2(  CommUser param  ) throws ServletException, IOException {

        //String username = request.getHeader("username");
        //String password = request.getHeader("password");
        //String sysId = request.getParameter("sysId");


        //public String loginv2( CommUser param ) throws ServletException, IOException {
        //String username = param.getUsername();
        //String password = param.getPassword();

        log.info("开始接口认证loginv2。。。。。。。。。。。。。。");
        log.info("传入的登录账号和密码：：：");
        log.info(username);
        log.info(password);




        CommUser sysUser = sysUserService.selectUserByLoginId(username);
        //UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(sysUser,password);
        // 生成一个包含账号密码的认证信息

        Authentication token = new UsernamePasswordAuthenticationToken(username,password);


        Authentication authentication = authenticationManager.authenticate(token);
        //如果认证失败，不会向下走，而是跳转到登录页面，除非在WebSecurityConfig开通.authenticationEntryPoint(jwtAuthenticationEntryPoint)


        // 将返回的Authentication存到上下文中
        SecurityContextHolder.getContext().setAuthentication(authentication);//
        CommUser user = (CommUser) authentication.getPrincipal();
        log.info("账号:"+user.getLoginId());
        //ServletContext().
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        loginSuccessHandler.onAuthenticationSuccess(sra.getRequest(),sra.getResponse(),authentication);
        return null;
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




