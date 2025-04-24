package pers.lwb.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data // 保证要有 get 和 set 方法
@Configuration
@ConfigurationProperties(prefix = "aliyun.oss.credential")
public class AliyunOSSCredentialProperties {

    private String accessKeyId;

    private String accessKeySecrect;

    private String ttl;
}
