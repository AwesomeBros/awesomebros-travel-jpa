package com.tripgg.server.global.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripgg.server.global.api.Api;
import com.tripgg.server.global.error.ErrorCode;
import com.tripgg.server.global.jwt.JwtAuthenticationFilter;
import com.tripgg.server.global.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final JwtProvider jwtProvider;
  private final ObjectMapper objectMapper;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/posts/**", "/countries/**", "/cities/**", "/districts/**").permitAll()
            .requestMatchers("/files/**", "/uploads/**").permitAll()
            .anyRequest().authenticated())
        .httpBasic(HttpBasicConfigurer::disable)
        .sessionManagement(sessionManagement -> sessionManagement
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(new JwtAuthenticationFilter(jwtProvider),
            UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(exceptionHandling -> exceptionHandling

            .authenticationEntryPoint((request, response, authException) -> {
              response.setStatus(ErrorCode.FAILD_UNAUTHORIZED.getHttpStatusCode());
              response.setContentType(MediaType.APPLICATION_JSON_VALUE);
              response.setCharacterEncoding("UTF-8");
              try {
                Api<Object> errorApi = Api.ERROR(ErrorCode.FAILD_UNAUTHORIZED, request.getRequestURI());
                String errorResponseJson = objectMapper.writeValueAsString(errorApi);
                response.getWriter().write(errorResponseJson);
              } catch (IOException e) {
                response.getWriter().write(
                    "{\"statusCode\":\"500\",\"message\":\"서버 내부 오류\"}");
              }
            })

            .accessDeniedHandler((request, response, accessDeniedException) -> {
              response.setStatus(ErrorCode.FORBIDDEN.getHttpStatusCode());
              response.setContentType(MediaType.APPLICATION_JSON_VALUE);
              response.setCharacterEncoding("UTF-8");

              try {
                Api<Object> errorApi = Api.ERROR(ErrorCode.FORBIDDEN, request.getRequestURI());
                String errorResponseJson = objectMapper.writeValueAsString(errorApi);
                response.getWriter().write(errorResponseJson);
              } catch (IOException e) {
                response.getWriter().write(
                    "{\"statusCode\":\"500\",\"message\":\"서버 내부 오류\"}");
              }
            }));
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
