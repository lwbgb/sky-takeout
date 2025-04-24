package pers.lwb.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOSSProperties {

    private String endpoint;

    private String bucketName;

    private String region;

    @Autowired
    private AliyunOSSCredentialProperties credential;
}
