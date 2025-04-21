package pers.lwb.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.*;
import pers.lwb.properties.JwtProperties;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;


public class JwtUtils {

    // 设置算法，生成秘钥
    private static final MacAlgorithm algorithm = Jwts.SIG.HS512;
    private static final SecretKey key = algorithm.key().build();

    private JwtProperties properties;

    /**
     * JWS 令牌生成
     *
     * @param claims  键值对信息
     * @param seconds 令牌生命周期（秒）
     * @return JWS 令牌
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
     *
     * @param jws JWS 令牌
     * @return 键值对信息
     */
    public static Claims parseJws(String jws) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jws)
                .getPayload();
        return claims;
    }

    public static String createJwe(String secretKey, Map<String, Object> claims, long seconds) {
        Password password = Keys.password(secretKey.toCharArray());

        KeyAlgorithm<Password, Password> alg = Jwts.KEY.PBES2_HS512_A256KW; //or PBES2_HS384_A192KW or PBES2_HS256_A128KW

        AeadAlgorithm enc = Jwts.ENC.A256GCM; //or A192GCM, A128GCM, A256CBC-HS512, etc...

        long currentTimeMillis = System.currentTimeMillis();
        Date expiration = new Date(currentTimeMillis + seconds * 1000);

        String jwe = Jwts.builder()
                .claims(claims)
                .encryptWith(password, alg, enc)
                .expiration(expiration)
                .compact();

        return jwe;
    }

    public static Claims parseJwe(String jwe, String secretKey) {
        Password password = Keys.password(secretKey.toCharArray());

        Claims claims = Jwts.parser()
                .decryptWith(password)
                .build()
                .parseEncryptedClaims(jwe)
                .getPayload();

        return claims;
    }
}
