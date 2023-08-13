package com.fakeapi.FakeStore.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class JwtTokenizerTest {

    @Value("${jwt.secretKey}")
    String accessSecret;
    public final Long ACCESS_TOKEN_EXPIRE_COUNT = 30*60*1000L;

    private static String JwtToken;


    @Test
    public void createAndParseToken() throws Exception {
        createToken();
        parseToken();
    }
//    @Test
//    @Order(1) // 첫 번째로 실행될 테스트 메서드
    public void createToken() throws Exception{
        String email = "gkswlcjs2@naver.com";
        List<String> roles = List.of("ROLE_USER");
        Long id = 1L;
        Claims claims = Jwts.claims().setSubject(email); // JWT 토큰의 payload에 들어갈 내용(claims)을 설정.
        claims.put("roles", roles);
        claims.put("memberId", id);

        byte[] accessSecret = this.accessSecret.getBytes(StandardCharsets.UTF_8);

        JwtToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + this.ACCESS_TOKEN_EXPIRE_COUNT))
                .signWith(Keys.hmacShaKeyFor(accessSecret)) // 결과에 서명까지 포함시킨 JwtBuilder
                .compact();

        System.out.println(JwtToken);
    }


//    @Test
//    @Order(2) // 첫 번째로 실행될 테스트 메서드
    public void parseToken() throws Exception{
        byte[] accessSecret = this.accessSecret.getBytes(StandardCharsets.UTF_8);
        //use JwtToken that created from the above code
//        String JwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJna3N3bGNqczJAbmF2ZXIuY29tIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sIm1lbWJlcklkIjoxLCJpYXQiOjE2OTE4OTQ4NDUsImV4cCI6MTY5MTg5NjY0NX0.xJ7It4ifi18R4LX-8mhFSN9bjDEJEc6TB2-GxIsKXZc";

        Claims claims = Jwts.parserBuilder() // JwtParserBuilder를 반환.
                .setSigningKey(Keys.hmacShaKeyFor(accessSecret))
                .build()
                .parseClaimsJws(JwtToken)
                .getBody();
        System.out.println(claims.getSubject());
        System.out.println(claims.get("roles"));
        System.out.println(claims.get("memberId"));
        System.out.println(claims.getIssuedAt());
        System.out.println(claims.getExpiration());
    }
}
