package com.choimory.itemvaluechecker.api.userapi.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class TokenManager {
    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.expire-time}")
    private int expiredTime;

    public String generateToken(String identity){
        Date now = new Date();
        Date expiredAt = new Date(now.getTime() + expiredTime);

        return Jwts.builder()
                .setSubject(identity)
                .setIssuedAt(now)
                .setExpiration(expiredAt)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String findIdentityFromToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }

        return false;
    }

    public boolean isTokenExpired(String token){
        return false;
    }
}
