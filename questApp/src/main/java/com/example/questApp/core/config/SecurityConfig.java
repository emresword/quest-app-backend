package com.example.questApp.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.questApp.business.concretes.UserDetailManager;
import com.example.questApp.security.jwt.JwtAuthenticationEntryPoint;
import com.example.questApp.security.jwt.JwtAuthenticationFilter;
import com.example.questApp.security.jwt.JwtTokenProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailManager userDetailManager;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider, @Lazy UserDetailManager userDetailManager) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailManager = userDetailManager;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider, userDetailManager);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .cors()
            .and()
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint())
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .requestMatchers(HttpMethod.GET, "/posts").permitAll()
            .requestMatchers(HttpMethod.GET, "/comments").permitAll()
            .requestMatchers(HttpMethod.GET, "/users").permitAll()
            .requestMatchers(HttpMethod.GET, "/messages").permitAll()
            .requestMatchers(HttpMethod.GET, "/followers").permitAll()
            .requestMatchers(HttpMethod.GET, "/likes").permitAll()
            .requestMatchers(HttpMethod.GET, "/hobbies").permitAll()
            .requestMatchers( "/messages/**").permitAll()
            .requestMatchers( "/hobbies/**").permitAll()
            .requestMatchers( "/followers/**").permitAll()
            .requestMatchers( "/likes/**").permitAll()
            .requestMatchers("/auth/**").permitAll()
            .requestMatchers( "/comments/**").permitAll()
            .requestMatchers( "/posts/**").permitAll()
            .requestMatchers("/followers/isFollowing").authenticated()
            .anyRequest().authenticated();

        httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
