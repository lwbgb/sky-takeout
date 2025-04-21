package pers.lwb.interceptor;

import com.alibaba.druid.util.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import pers.lwb.constant.JwtClaimsConstant;
import pers.lwb.context.LocalContext;
import pers.lwb.properties.JwtProperties;
import pers.lwb.utils.JwtUtils;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final JwtProperties properties;

    public LoginCheckInterceptor(JwtProperties properties) {
        this.properties = properties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断当前拦截到的是 Controller 的方法还是其他资源，拦截到的不是动态方法，直接放行
        if (!(handler instanceof HandlerMethod))
            return true;

        String url = request.getRequestURL().toString();
        log.info("登录校验 URL：{}", url);

        // 获取令牌 token
        String jwe = request.getHeader(properties.getAdminTokenName());

        // 判断 jwe 是否为空（客户端还未获取 JWT 令牌）
        if (StringUtils.isEmpty(jwe)) {
            log.info("客户端尚未获取 JWT 令牌！");
            response.setStatus(401);
            return false;
        }
        log.info("开始校验 JWT 令牌：{}", jwe);

        // 解析令牌
        Claims claims = null;
        try {
            claims = JwtUtils.parseJwe(jwe, properties.getAdminSecretKey());
        } catch (JwtException e) {
            log.info("JWT 令牌校验失败！");
            response.setStatus(401);
            return false;
        }

        log.info("JWT 令牌校验通过！");
//        claims.forEach((key, value) -> {
//            log.info("{}: {}", key, value);
//        });

        // 从 JWE 令牌中获取 empId 并存入 ThreadLocal
        Long empId = Long.valueOf(claims.get(JwtClaimsConstant.EMP_ID).toString());
        LocalContext.setCurrentId(empId);

        return true;
    }
}
