package com.stockcore.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfiguration : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**") // Aplica para todas as rotas
            .allowedOrigins("http://localhost:3000") // Origem do seu frontend
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos comuns para REST + OPTIONS
            .allowedHeaders("*") // <--- ADICIONE ESTA LINHA: Crucial para Authorization e Content-Type
            .allowCredentials(true) // <--- ADICIONE ESTA LINHA: Boa prática para APIs com credenciais/tokens
            .maxAge(3600) // <--- ADICIONE ESTA LINHA: Cache do preflight (1 hora)
    }
}