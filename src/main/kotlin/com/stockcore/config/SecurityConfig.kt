package com.stockcore.config

import com.stockcore.security.JWTAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(private val jwtFilter: JWTAuthenticationFilter) {

    /*
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable()
            .authorizeHttpRequests {
                it.requestMatchers("/auth/login").permitAll()
                    .anyRequest().authenticated()
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(jwtFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    } */
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable()
            .authorizeHttpRequests {
                it.anyRequest().permitAll()
            }
        return http.build()
    }

}
