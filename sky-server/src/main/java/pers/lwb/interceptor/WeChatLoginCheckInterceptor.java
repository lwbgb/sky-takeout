package pers.lwb.interceptor;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import pers.lwb.constant.JwtClaimsConstant;
import pers.lwb.constant.MessageConstant;
import pers.lwb.context.LocalContext;
import pers.lwb.properties.JwtProperties;
import pers.lwb.result.Result;
import pers.lwb.utils.JwtUtils;

@Slf4j
@Component
public class WeChatLoginCheckInterceptor implements HandlerInterceptor {

    private final JwtProperties properties;

    public WeChatLoginCheckInterceptor(JwtProperties properties) {
        this.properties = properties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断当前拦截到的是 Controller 的方法还是其他资源，拦截到的不是动态方法，直接放行
        if (!(handler instanceof HandlerMethod))
            return true;

        String url = request.getRequestURL().toString();
        log.info("wx 用户登录校验 URL：{}", url);

        // 获取令牌 token
        String jwe = request.getHeader(properties.getUserTokenName());

        // 判断 jwe 是否为空（客户端还未获取 JWT 令牌）
        if (StringUtils.isEmpty(jwe)) {
            log.info("wx 用户尚未获取 JWT 令牌！");
            String res = JSONObject.toJSONString(Result.error(MessageConstant.USER_NOT_LOGIN));
            response.getWriter().write(res);
            return false;
        }
        log.info("开始校验 wx 用户 JWT 令牌：{}", jwe);

        // 解析令牌
        Claims claims = null;
        try {
            claims = JwtUtils.parseJwe(jwe, properties.getUserSecretKey());
        } catch (JwtException e) {
            log.info("用户 JWT 令牌校验失败！");
            String res = JSONObject.toJSONString(Result.error(MessageConstant.PASSWORD_ERROR));
            response.getWriter().write(res);
            return false;
        }

        log.info("用户 JWT 令牌校验通过！");

        // 从 JWE 令牌中获取 userId 并存入 ThreadLocal
        Long userId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
        LocalContext.setCurrentId(userId);

        return true;
    }
}
