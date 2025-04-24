package pers.lwb.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pers.lwb.exception.UploadErrorException;
import pers.lwb.properties.AliyunOSSProperties;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Data
@Component
public class AliyunOSSUtils {

    private String objectPath = "./"; // 默认为 bucket 下的直接路径

    private final AliyunOSSProperties aliyunOSSProperties;

    private final CustomCredentialsProvider customCredentialsProvider;

    public AliyunOSSUtils(AliyunOSSProperties aliyunOSSProperties, CustomCredentialsProvider customCredentialsProvider) {
        this.aliyunOSSProperties = aliyunOSSProperties;
        this.customCredentialsProvider = customCredentialsProvider;
    }

    public String upload(MultipartFile file) {
        String endpoint = aliyunOSSProperties.getEndpoint();
        String region = aliyunOSSProperties.getRegion();
        String bucketName = aliyunOSSProperties.getBucketName();

        // 创建OSSClient实例。
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        OSS ossClient = OSSClientBuilder.create()
                .endpoint(endpoint)
                .credentialsProvider(customCredentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(region)
                .build();

        // 设置唯一文件名
        String filename = file.getOriginalFilename();
        int index = filename.lastIndexOf(".");
        String exName = filename.substring(index);
        String newFileName = UUID.randomUUID().toString() + exName;
        String objectName = objectPath + newFileName;
        log.info("上传文件到：{}", objectName);

        try {
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, file.getInputStream());
            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // 上传文件。
            PutObjectResult result = ossClient.putObject(putObjectRequest);

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } catch (IOException e) {
            throw new UploadErrorException("获取文件出错：" + e.getMessage());
        } finally {
            ossClient.shutdown();
        }

        StringBuilder url = new StringBuilder();
        url.append("https://")
                .append(bucketName)
                .append(".")
                .append(endpoint)
                .append("/")
                .append(objectName);

        return url.toString();
    }
}
