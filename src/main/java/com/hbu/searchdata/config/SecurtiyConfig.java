package com.hbu.searchdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @program: searchdata
 * @description: SpringSecurity安全配置
 * @author: Chensiming
 * @create: 2018-01-29 13:24
 **/
@Configuration
public class SecurtiyConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder getPasswordEncoder()
    {
        return  new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/templates/login.html")
                .loginProcessingUrl("/login/form")
                .and()
                .authorizeRequests()
                .antMatchers("/templates/login.html").permitAll()
                .antMatchers("/static/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .headers().frameOptions().disable()
                .and().csrf().disable();
    }
}
