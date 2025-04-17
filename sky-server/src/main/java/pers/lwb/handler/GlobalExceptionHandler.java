package pers.lwb.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.lwb.result.Result;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<String> catchExp(Exception e) {
//        log.info("全局异常处理器捕获到异常信息：{}", e.getMessage());
        return Result.error(e.getMessage());
    }
}
