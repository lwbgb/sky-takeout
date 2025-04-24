package pers.lwb.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pers.lwb.result.Result;
import pers.lwb.utils.AliyunOSSUtils;

@Slf4j
@Tag(name = "UploadController")
@RestController
@RequestMapping("/admin/common")
public class UploadController {

    private final AliyunOSSUtils aliyunOSSUtils;

    public UploadController(AliyunOSSUtils aliyunOSSUtils) {
        this.aliyunOSSUtils = aliyunOSSUtils;
    }

    @Operation(summary = "上传文件")
    @PostMapping("/upload")
    public Result<Object> uploadFile(MultipartFile file) {
        aliyunOSSUtils.setObjectPath("image/");
        String url = aliyunOSSUtils.upload(file);
        log.info("文件上传成功！URL：{}", url);
        return Result.success((Object) url);
    }
}
