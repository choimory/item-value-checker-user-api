package com.choimory.itemvaluechecker.api.userapi.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 스프링 시큐리티의 SecurityConfig에서 JWT관련 설정을 분리하여 작성, 이후 SecurityConfig에서 인스턴스 생성하여 적용
 * TODO SecurityConfig에서 인스턴스를 계속 생성하는지? 한번만 생성하는지?
 */
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtUtil jwtUtil;

    public JwtSecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        JwtRequestFilter jwtRequestFilter = new JwtRequestFilter(jwtUtil);
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
