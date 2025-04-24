package pers.lwb.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import pers.lwb.annotation.AutoFill;
import pers.lwb.constant.AutoFillConstant;
import pers.lwb.constant.MessageConstant;
import pers.lwb.context.LocalContext;
import pers.lwb.enumeration.OperationType;
import pers.lwb.exception.AutoFillErrorException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Component
@Aspect
public class AutoFillAspect {

    @Before("@annotation(pers.lwb.annotation.AutoFill)")
    public void autoFill(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod(); // 获取目标方法
        log.info("执行 {} 方法，触发 AOP 信息自动填充", method.getName());

        if (method.isAnnotationPresent(AutoFill.class)) {
            AutoFill annotation = method.getAnnotation(AutoFill.class);
            Object[] args = joinPoint.getArgs();

            if (args == null || args.length == 0)
                return;

            if (OperationType.INSERT.equals(annotation.value())) {
                Object entity = args[0];
                Field[] fields = entity.getClass().getDeclaredFields();
                Arrays.stream(fields).forEach(field -> {
                    field.setAccessible(true);
                    try {
                        if (field.getName().equals(AutoFillConstant.CREATE_TIME) ||
                                field.getName().equals(AutoFillConstant.UPDATE_TIME)) {
                            field.set(entity, LocalDateTime.now());
                        } else if (field.getName().equals(AutoFillConstant.CREATE_USER) ||
                                field.getName().equals(AutoFillConstant.UPDATE_USER)) {
                            field.set(entity, LocalContext.getCurrentId());
                        }
                    } catch (IllegalAccessException e) {
                        throw new AutoFillErrorException(MessageConstant.AUTO_FILL_ERROR);
                    }
                });
            } else if (OperationType.UPDATE.equals(annotation.value())) {
                Object entity = args[0];
                Field[] fields = entity.getClass().getDeclaredFields();
                Arrays.stream(fields).forEach(field -> {
                    field.setAccessible(true);
                    try {
                        if (field.getName().equals(AutoFillConstant.UPDATE_TIME)) {
                            field.set(entity, LocalDateTime.now());
                        } else if (field.getName().equals(AutoFillConstant.UPDATE_USER)) {
                            field.set(entity, LocalContext.getCurrentId());
                        }
                    } catch (IllegalAccessException e) {
                        throw new AutoFillErrorException(MessageConstant.AUTO_FILL_ERROR);
                    }
                });
            }
        }
    }
}
