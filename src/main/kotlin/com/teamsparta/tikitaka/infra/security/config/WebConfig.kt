package com.teamsparta.tikitaka.infra.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig {
    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
                    .allowedOrigins("http://localhost:3000")
                    .allowedOrigins("http://localhost:5173")
                    .allowedOrigins("https://futeamatching.vercel.app")
                    .allowedOrigins("https://www.futeamatching.com")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "HEAD", "OPTIONS")
                    .allowedHeaders("*")
                    .exposedHeaders("Authorization", "RefreshToken")
                    .allowCredentials(true)
            }
        }
    }
}