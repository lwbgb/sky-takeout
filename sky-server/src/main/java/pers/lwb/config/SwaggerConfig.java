package pers.lwb.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc // TODO Swagger 配置类生效问题
public class SwaggerConfig {

//    @Bean
//    public GlobalOpenApiCustomizer orderGlobalOpenApiCustomizer() {
//        return openApi -> {
//            if (openApi.getTags() != null) {
//                openApi.getTags().forEach(tag -> {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("x-order", (int) (100 * Math.random()));
//                    tag.setExtensions(map);
//                });
//            }
//            if (openApi.getPaths() != null) {
//                openApi.addExtension("x-test123", "333");
//                openApi.getPaths().addExtension("x-abb", (int) (100 * Math.random() + 1));
//            }
//        };
//    }

    @Bean
    public OpenAPI swaggerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("sky-takeout 系统API")
                        .version("1.0")
                        .description("后端 API 文档生成"))
                .externalDocs(new ExternalDocumentation()
                        .description("Vue 前端")
                        .url("http://127.0.0.1:80"));
    }
}
