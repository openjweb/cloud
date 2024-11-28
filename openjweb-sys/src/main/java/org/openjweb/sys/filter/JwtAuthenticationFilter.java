package org.openjweb.sys.filter;

import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.core.entity.CommUser;
import org.openjweb.core.service.CommUserService;
import org.openjweb.core.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    JwtUtil jwtUtils;

    @Autowired
    CommUserService userDetailService;

    @Autowired
    CommUserService sysUserService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwt = null;
        if(jwtUtils==null){
            log.info("jwtUtils is null。。。。。。。。。。");
        }
        else{
            log.info("jwt utils is not null,jwtutils.getHeader:");//非空
            log.info(jwtUtils.getHeader());//空
            String header = jwtUtils.getHeader();//
            if(header!=null) {
                log.info("jwt header:::::"+header );
                jwt = request.getHeader(header);
            }

        }

        // 这里如果没有jwt，继续往后走，因为后面还有鉴权管理器等去判断是否拥有身份凭证，所以是可以放行的
        // 没有jwt相当于匿名访问，若有一些接口是需要权限的，则不能访问这些接口
        if (StrUtil.isBlankOrUndefined(jwt)) {
            log.info("没有jwt信息，继续filter........");
            chain.doFilter(request, response);
            return;
        }
        else{
            log.info("有jwt..........");
        }

        Claims claim = jwtUtils.getClaimsByToken(jwt);
        if (claim == null) {
            throw new JwtException("token 异常");
        }
        if (jwtUtils.isTokenExpired(claim)) {
            throw new JwtException("token 已过期");
        }
        String username = claim.getSubject();
        // 获取用户的权限等信息

        CommUser sysUser = sysUserService.selectUserByLoginId(username);

        // 构建UsernamePasswordAuthenticationToken,这里密码为null，是因为提供了正确的JWT,实现自动登录
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, userDetailService.getUserAuthority(sysUser.getLoginId()));
        SecurityContextHolder.getContext().setAuthentication(token);

        chain.doFilter(request, response);

    }
}
