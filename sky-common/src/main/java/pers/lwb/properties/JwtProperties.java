package pers.lwb.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "sky.jwt")
public class JwtProperties {
    // 管理员 JWT 令牌配置
    private String adminSecretKey;
    private Long adminTtl;
    private String adminTokenName;

    // wx 用户 JWT 令牌配置
    private String userSecretKey;
    private Long userTtl;
    private String userTokenName;
}
