package pers.lwb.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import pers.lwb.interceptor.LoginCheckInterceptor;
import pers.lwb.json.JacksonObjectMapper;

import java.util.List;

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

    //扩展 SpringMVC 的消息转换器，统一对日期类型进行格式化处理
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 新建 SpringMVC 消息转换器
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        // 设置日期类型到 json 的格式映射
        converter.setObjectMapper(new JacksonObjectMapper());
        // 将自定义的消息转换器添加到框架的转换器中，并设置优先级最高
        converters.add(0, converter);
    }
}
