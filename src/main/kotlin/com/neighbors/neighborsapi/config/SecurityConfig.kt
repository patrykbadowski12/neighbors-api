package com.neighbors.neighborsapi.config

import com.neighbors.neighborsapi.jwt.JwtFilter
import com.neighbors.neighborsapi.jwt.JwtService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtService: JwtService,
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/auth/google").permitAll()
                it.anyRequest().authenticated()
            }.addFilterBefore(JwtFilter(jwtService), UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}
