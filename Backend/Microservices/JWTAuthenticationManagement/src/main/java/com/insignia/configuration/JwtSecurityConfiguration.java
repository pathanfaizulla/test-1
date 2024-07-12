package com.insignia.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.insignia.filter.JwtRequestFilter;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("deprecation")
@EnableWebSecurity
@Configuration
@Slf4j
public class JwtSecurityConfiguration {

    @Autowired
    private UserDetailsService myUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager configProviders(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider());
        return authenticationConfiguration.getAuthenticationManager();
    }

    private AuthenticationProvider customAuthenticationProvider() {
        return new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                // TODO: move the controller logic here
                return authentication;
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return true;
            }
        } ;
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((req) -> {
                    req.requestMatchers("/actuator/prometheus").permitAll();
                    req.requestMatchers("/admin").hasAnyAuthority("admin");
                    req.requestMatchers("/user").hasAnyAuthority("admin",
                            "user");
                    req.requestMatchers("/authenticate", "/oauth1authenticate"
                                    , "/updatePassword", "/checkTokenValidity" +
                                            "/{customerSequeceNumber}")
                            .permitAll();
                    req.anyRequest().authenticated();
                });

        httpSecurity.addFilterBefore(jwtRequestFilter,
                UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

}
