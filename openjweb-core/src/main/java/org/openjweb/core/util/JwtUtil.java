package org.openjweb.core.util;

import io.jsonwebtoken.*;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 使用@ConfigurationProperties(prefix = "jwt") ，从application-dev.yml中获取配置
 * Application增加@RequiredArgsConstructor
 * 且pom.xml增加spring-boot-configuration-processor 依赖
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtUtil {

    private long expire;
    private String secret;
    private String header;
    public JwtUtil(){}

    /**
     *
     * @param header 头部
     * @param secert 加密私钥
     * @param expire 过期秒数
     */
    public JwtUtil(String header,String secert,long expire){
        this.expire = expire;
        this.header = header;
        this.secret = secert;
    }

    // 生成JWT
    public String generateToken(String username) {

        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + 1000 * expire);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)    // 过期时间
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    // 指定有效时间
    public String generateToken(String username,long seconds) {

        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + 1000 * seconds);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)    // 过期时间
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    // 解析JWT
    public Claims getClaimsByToken(String jwt) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            return null;
        }

    }

    // 判断JWT是否过期
    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

}
