package org.openjweb.sys.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.core.service.CommUserService;
import org.openjweb.sys.auth.security.AESPasswordEncoder;
import org.openjweb.sys.auth.security.MD5PasswordEncoder;
import org.openjweb.sys.auth.security.MyAccessDecisionManager;
import org.openjweb.sys.auth.security.MyFilterInvocationSecurityMetadataSource;
import org.openjweb.sys.entry.JwtAuthenticationEntryPoint;
import org.openjweb.sys.filter.JwtAuthenticationFilter;
import org.openjweb.sys.handler.JWTLogoutSuccessHandler;
import org.openjweb.sys.handler.JwtAccessDeniedHandler;
import org.openjweb.sys.handler.LoginFailureHandler;
import org.openjweb.sys.handler.LoginSuccessHandler;
import org.openjweb.sys.provider.MyAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CommUserService userDetailService;

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new AESPasswordEncoder();
    }

    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Autowired
    JWTLogoutSuccessHandler jwtLogoutSuccessHandler;

    @Value("${oauth2.server}")
    private boolean isOAuth2Server = false;

    private static final String[] ALLOW_URL_LIST = {
            //
            "/login",
            "/logout",
            "/captcha",
            "/favicon.ico",
            //"/api/jwt/**",
            "/api/cms/**",
            "/api/b2c/**",
            "/api/b2b2c/**",
            "/api/sns/**",
            "/api/comm/**",
            "/api/cms1/**",
            "/api/store/**",
            "/demo/**",
            "/oauth/**", //允许oauth认证的路径
            "/webjars/**", //webjars js允许的路径
            "/testduoyu",
            "/i18n/**",
            "/**/*.html", //swagger
            "/api/comm/user/login",
            "/api/weixin/login",
            "/api/weixin/getVueMenu",
            "/api/comm/user/getUserInfo2",
            "/front/**",
            "/**/js/**",
            "/**/images/**",
            "/**/css/**",
            "/**/img/**",
            "/api/cms/pub/**"

    };

    //作用？？？暴露AuthenticationManager给其他Bean使用
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
        //return super.authenticationManagerBean();
    }

    //这个和上面的是什么区别？能一起用吗？

    /*@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }*/


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //下面注释掉的是第一阶段的示例
      /*  http.cors().and().csrf().disable()//登录表单
         .formLogin()
                .and()
                .authorizeRequests()
                .antMatchers(ALLOW_URL_LIST).permitAll()
                .anyRequest().authenticated();
       */
        //下面是第二阶段整合了数据库权限控制的示例
        log.info("是否配置了oauth2 server:::::");
        log.info(String.valueOf(this.isOAuth2Server));
        if(this.isOAuth2Server){
            log.info("OAUTH2模式...........");

            http.formLogin()
                    //.loginPage("/login.html")
                    .loginProcessingUrl("/login")
                    .and()
                    .authorizeRequests()
                    .antMatchers("/login.html", "/img/**","/demo/**","/webjars/**",  "/testduoyu","/i18n/**","/api/b2c/b2carea/**","/api/store/**","/**/*.html", "/api/comm/user/login","/api/weixin/login","/api/weixin/getVueMenu","/api/comm/user/getUserInfo2", "/front/**"
                                    ,"/**/js/**",  "/**/images/**",  "/**/css/**","/**/img/**","/api/cms/pub/**"
                    ).permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .csrf().disable();
        }
        else {
            log.info("非OAUTH2模式............");
            http.authorizeRequests()
                    .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                        @Override
                        public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                            object.setSecurityMetadataSource(cfisms());
                            object.setAccessDecisionManager(cadm());

                            return object;
                        }
                    })
                    .and().formLogin()
                    //先注掉这个检查oauth认证
                    //.successHandler(loginSuccessHandler) //登录成功处理
                    .failureHandler(loginFailureHandler) //登录失败处理
                    .loginProcessingUrl("/login").permitAll()
                    //.loginProcessingUrl("/demo/jwt/login").permitAll()

                    .and()
                    .logout()
                    .logoutSuccessHandler(jwtLogoutSuccessHandler)

                    // 禁用session
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                    // 配置拦截规则
                    .and()
                    .authorizeRequests()
                    .antMatchers(ALLOW_URL_LIST).permitAll()
                    .anyRequest().authenticated()


                    // 异常处理器
                    .and()
                    .exceptionHandling()
                    //接口登录模式打开这个
                    //.authenticationEntryPoint(jwtAuthenticationEntryPoint) //这个影响登录，会导致/login登录蔬菜
                    .accessDeniedHandler(jwtAccessDeniedHandler)

                    // 配置自定义的过滤器
                    //这个jwtAuthenticationFilter 不加也执行了，是否增加了会调整多个过滤器的执行顺序
                    .and()
                    .addFilter(jwtAuthenticationFilter())
                    .logout().permitAll().and().csrf().disable();
        }
    }

    /*@Bean
    PasswordEncoder PasswordEncoder() {
        //return md5PasswordEncoder;
        //return aesPasswordEncoder;//这个不行
        return new AESPasswordEncoder();
        //return new BCryptPasswordEncoder();
        //return new Md5PasswordEncoder();

    }*/
   /*
    @Autowired
    MD5PasswordEncoder md5PasswordEncoder;

    @Autowired
    AESPasswordEncoder aesPasswordEncoder;
    */

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        if(true){
            //如果自定义AuthenticationProvider 则不使用这个
            //auth.userDetailsService(userDetailService).passwordEncoder(aesPasswordEncoder);
            //auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setUserDetailsService(userDetailService);
            provider.setPasswordEncoder(passwordEncoder());
            auth.authenticationProvider(provider);
        }
        else{
            //自定义AuthenticationProvider
            auth.authenticationProvider(new MyAuthenticationProvider(userDetailService));
        }
    }

    @Bean
    MyAccessDecisionManager cadm() {
        //System.out.println("加载角色权限设置。。。。。。。。。。。。");
        return new MyAccessDecisionManager();
    }

    @Bean
    MyFilterInvocationSecurityMetadataSource cfisms() {
        //System.out.println("加载权限设置。。。。。。。。。。。。");
        return new MyFilterInvocationSecurityMetadataSource();
    }


    @Bean
    @ConditionalOnExpression("'${oauth2.server}'=='false'")
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager());
        return jwtAuthenticationFilter;
    }
}
