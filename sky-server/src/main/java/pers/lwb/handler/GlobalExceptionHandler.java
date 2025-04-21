package pers.lwb.handler;

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
}
