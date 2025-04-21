package pers.lwb.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import pers.lwb.interceptor.LoginCheckInterceptor;

@Slf4j
@Configuration
public class WebMvcConstruction extends WebMvcConfigurationSupport {

    private final LoginCheckInterceptor loginCheckInterceptor;

    public WebMvcConstruction(LoginCheckInterceptor loginCheckInterceptor) {
        this.loginCheckInterceptor = loginCheckInterceptor;
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册登录校验拦截器...");
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/employee/login");
    }

    // 添加静态注册表
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
