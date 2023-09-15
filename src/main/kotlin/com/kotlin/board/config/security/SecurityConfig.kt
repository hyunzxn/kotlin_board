package com.kotlin.board.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlin.board.auth.jwt.JwtAuthenticationFilter
import com.kotlin.board.auth.jwt.JwtTokenProvider
import com.kotlin.board.config.security.handler.Http401Handler
import com.kotlin.board.config.security.handler.Http403Handler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val objectMapper: ObjectMapper,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .httpBasic { it.disable() }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it.requestMatchers("/api/auth/signup", "/api/auth/login").anonymous()
                    .requestMatchers("/api/users/**").hasRole("MEMBER")
                    .anyRequest().permitAll()
            }
            .addFilterBefore(
                JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .exceptionHandling {
                it.authenticationEntryPoint(Http401Handler(objectMapper))
                it.accessDeniedHandler(Http403Handler(objectMapper))
            }
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }
}