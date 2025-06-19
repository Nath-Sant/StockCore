package com.stockcore.config

import com.stockcore.security.JWTAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(private val jwtFilter: JWTAuthenticationFilter) {

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable()
            .authorizeHttpRequests {

                it.requestMatchers("/auth/login").permitAll()

                it.requestMatchers(HttpMethod.POST, "/usuarios/**").hasRole("ADM")
                it.requestMatchers(HttpMethod.PUT, "/usuarios/**").hasRole("ADM")
                it.requestMatchers(HttpMethod.DELETE, "/usuarios/**").hasRole("ADM")
                it.requestMatchers(HttpMethod.GET, "/usuarios/**").hasAnyRole("ADM", "FUNCIONARIO")

                it.requestMatchers(HttpMethod.DELETE, "/produtos/**").hasRole("ADM")
                it.requestMatchers(HttpMethod.DELETE, "/tipoprodutos/**").hasRole("ADM")
                it.requestMatchers(HttpMethod.DELETE, "/movimentacoes/**").hasRole("ADM")

                it.requestMatchers("/produtos/**").hasAnyRole("ADM", "FUNCIONARIO")
                it.requestMatchers("/tipoprodutos/**").hasAnyRole("ADM", "FUNCIONARIO")
                it.requestMatchers("/movimentacoes/**").hasAnyRole("ADM", "FUNCIONARIO")

                it.anyRequest().authenticated()
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}
