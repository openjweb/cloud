package org.openjweb.sys.config;



import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * RSA密钥配置类，用于存储和访问RSA公钥和私钥。
 * 该类通过@ConfigurationProperties注解绑定到配置文件中以security.rsa为前缀的属性。
 * https://blog.csdn.net/m0_61606343/article/details/138337298
 */
@Configuration
@ConfigurationProperties(prefix = "security.rsa")
@Data
public class RsaProperties {
    // 存储RSA公钥
    private String publicKey;
    // 存储RSA私钥
    private String privateKey;

}