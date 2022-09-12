package com.choimory.itemvaluechecker.api.userapi.config;

import com.choimory.itemvaluechecker.api.userapi.jwt.*;
import com.choimory.itemvaluechecker.api.userapi.member.code.AuthLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtUtil jwtUtil;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*스프링 시큐리티 기본 로그인 비활성화*/
        //http.httpBasic().disable();
        http.formLogin().disable();

        /*스프링 시큐리티 csrf 비활성화*/
        //REST API의 경우 stateless하므로 CSRF를 비활성화하여도 괜찮다.
        http.csrf().disable();

        /*인증설정*/
        //permitAll > hasAuthority 순으로 적용해야 화이트, 블랙리스트가 모두 적용됨
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/member").permitAll()
                .antMatchers(HttpMethod.PUT, "/member").permitAll()
                .antMatchers(HttpMethod.PATCH, "/member/ban/{\\w+}").hasAuthority(AuthLevel.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/member/{\\w+}").permitAll()
                .antMatchers(HttpMethod.POST, "/member/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                /*Jwt*/
                .and()
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .apply(new JwtSecurityConfig(jwtUtil));
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
