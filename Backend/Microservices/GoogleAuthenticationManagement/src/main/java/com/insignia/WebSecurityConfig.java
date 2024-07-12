package com.insignia;

import com.insignia.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private CustomOAuth2UserService oauthUserService;

    @Bean
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/", "/login", "/oauth/**").permitAll();
                    registry.anyRequest().authenticated();
                })
                .formLogin(loginConfigurer -> {
                    loginConfigurer.loginPage("/login").permitAll();
                })
                .oauth2Login(auth2LoginConfigurer -> {
                    auth2LoginConfigurer.loginPage("/login").userInfoEndpoint(userInfoEndpointConfig -> {
                        userInfoEndpointConfig.userService(oauthUserService);
                    });
                });
    }
}
