package pers.lwb.utils;

import com.aliyun.oss.common.auth.Credentials;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pers.lwb.properties.AliyunOSSProperties;
import pers.lwb.properties.JwtProperties;

@Slf4j
@Component
@Scope("singleton")
public class CustomCredentialsProvider implements CredentialsProvider {

    private final AliyunOSSProperties aliyunOSSProperties;

    public CustomCredentialsProvider(AliyunOSSProperties aliyunOSSProperties, JwtProperties jwtProperties) {
        this.aliyunOSSProperties = aliyunOSSProperties;
    }

    @Override
    public void setCredentials(Credentials credentials) {
        // TODO setCredentials
    }

    @Override
    public Credentials getCredentials() {
        log.info("正在生成阿里云OSS凭据...");
        // TODO
        // 自定义访问凭证的获取方法

        // 返回长期凭证 access_key_id, access_key_secrect
        return new DefaultCredentials(aliyunOSSProperties.getCredential().getAccessKeyId(),
                aliyunOSSProperties.getCredential().getAccessKeySecrect());

        // 返回 临时凭证 access_key_id, access_key_secrect, token
        // 对于临时凭证，需要根据过期时间，刷新凭证。
//        return new DefaultCredentials(aliyunOSSProperties.getCredential().getAccessKeyId(),
//                aliyunOSSProperties.getCredential().getAccessKeySecrect(), jwe);
    }
}
