package pers.lwb.handler;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.lwb.constant.MessageConstant;
import pers.lwb.exception.BaseException;
import pers.lwb.result.Result;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 自定义异常捕获
     * @param e 自定义异常
     * @return 异常信息
     */
    @ExceptionHandler
    public Result<String> catchExp(BaseException e) {
        log.info("GlobalExceptionHandler caught exception：{}", e.getMessage());
        return Result.error(e.getMessage());
    }

    /**
     * 用户名重复异常
     * @param ex    捕获的异常
     * @return      异常信息
     */
    @ExceptionHandler
    public Result<String> catchExp(SQLIntegrityConstraintViolationException ex) {
        String exMessage = ex.getMessage();
        log.info(exMessage);
        if (exMessage.contains("Duplicate entry")) {
            String[] split = exMessage.split(" ");
            String username = split[2];
            String msg = username + MessageConstant.USERNAME_REPEAT;
            return Result.error(msg);
        } else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }

    /**
     * JWT 令牌异常
     * @param ex    捕获的异常
     * @return      异常信息
     */
    @ExceptionHandler
    public Result<String> catchExp(JwtException ex) {
        log.info(ex.getMessage());
        return Result.error("JWT 令牌不匹配");
    }

    /**
     * 阿里云 OSS 上传文件异常
     * @param ex 捕获的异常
     * @return 异常信息
     */
    @ExceptionHandler(exception = {ClientException.class, OSSException.class})
    public Result<String> catchExp(Exception ex) {
        log.info("阿里云 OSS 上传文件异常：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }
}









