package org.openjweb.sys.config;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
@Configuration
public class DruidMonitorConfiguration {
    @Bean
    public ServletRegistrationBean druidStatViewServlet() {
        //ServletRegistrationBean提供的类注册
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        //添加初始化参数 initParams
        //白名单：
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");//这个以后应该写在配置里
        //黑名单 （共同存在时,deny 优先allow）
        //如满足deny 就提示sorry ,you are not permitted view this page
        servletRegistrationBean.addInitParameter("deny", "193.0.0.1");
        //登录查看信息的账号和密码
        servletRegistrationBean.addInitParameter("loginUsername","admin");
        servletRegistrationBean.addInitParameter("loginPassword","12345678");
        //是否能重置数据
        servletRegistrationBean.addInitParameter("resetEnable","false");
        return servletRegistrationBean ;
    }
    @Bean
    public FilterRegistrationBean druidStatFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        //添加需要忽略的格式信息
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
