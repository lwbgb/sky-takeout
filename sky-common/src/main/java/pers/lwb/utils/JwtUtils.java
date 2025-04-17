package pers.lwb.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;


public class JwtUtils {

    // 设置算法，生成秘钥
    private static final MacAlgorithm algorithm = Jwts.SIG.HS512;
    private static final SecretKey key = algorithm.key().build();

    /**
     * JWS 令牌生成
     * @param claims    键值对信息
     * @param seconds   令牌生命周期（秒）
     * @return          JWS 令牌
     */
    public static String createJws(Map<String, Object> claims, long seconds) {

        // 设置令牌过期时间
        long currentTimeMillis = System.currentTimeMillis();
        Date expiration = new Date(currentTimeMillis + seconds * 1000);

        String jws = Jwts.builder()
                .claims(claims)
                .signWith(key)
                .expiration(expiration)
                .compact();

        return jws;
    }

    /**
     * JWS 令牌解析
     * @param jws   JWS 令牌
     * @return      键值对信息
     */
    public static Claims parseJws(String jws) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jws)
                .getPayload();
        return claims;
    }
}
