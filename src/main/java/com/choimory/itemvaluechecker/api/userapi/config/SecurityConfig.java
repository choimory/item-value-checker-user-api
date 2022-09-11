package com.choimory.itemvaluechecker.api.userapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
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
                .antMatchers(HttpMethod.PATCH, "/member/ban/{\\w+}").permitAll()
                .antMatchers(HttpMethod.GET, "/member/{\\w+}").permitAll()
                .antMatchers(HttpMethod.POST, "/member/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .cors()
                /*.and()
                .exceptionHandling().authenticationEntryPoint()*/
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
