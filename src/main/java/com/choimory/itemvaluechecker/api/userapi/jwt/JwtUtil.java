package com.choimory.itemvaluechecker.api.userapi.jwt;

import com.choimory.itemvaluechecker.api.userapi.member.dto.dto.MemberDto;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 토큰 생성, 검증 등의 토큰 관련 유틸 클래스
 */
@Component
@Slf4j
public class JwtUtil {
    private final String secretKey;
    private final int expiredTime;
    private final JwtMemberService jwtMemberService;

    public JwtUtil(@Value("${jwt.secret-key}") String secretKey,
                   @Value("${jwt.expire-time}") int expiredTime,
                   JwtMemberService jwtMemberService) {
        this.secretKey = secretKey;
        this.expiredTime = expiredTime;
        this.jwtMemberService = jwtMemberService;
    }

    public String generateToken(String identity){
        Date now = new Date();
        Date expiredAt = new Date(now.getTime() + expiredTime);

        Claims claims = Jwts.claims()
                .setSubject(identity);

        return Jwts.builder()
                .setClaims(claims)
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

    public boolean isTokenValid(String token){
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);

            return !claims.getBody()
                    .getExpiration()
                    .before(new Date());
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
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    public boolean isTokenExpired(String token){
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public static String toToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken)
                && StringUtils.startsWithIgnoreCase(bearerToken, "Bearer ")
                && (!bearerToken.contains("undefined") || !bearerToken.contains("null"))){
            return StringUtils.replace(bearerToken,"Bearer ", "");
        }

        return null;
    }

    public Authentication toAuthentication(String token){
        MemberDto member = jwtMemberService.findMemberByIdentity(this.findIdentityFromToken(token));
        if(member == null){
            return null;
        }

        List<? extends GrantedAuthority> authorities = Stream.of(member.getMemberAuthority())
                .map(auth -> new SimpleGrantedAuthority(auth.getAuthLevel().name()))
                .collect(Collectors.toUnmodifiableList());
        return new UsernamePasswordAuthenticationToken(member.getIdentity(), null, authorities);
    }
}
