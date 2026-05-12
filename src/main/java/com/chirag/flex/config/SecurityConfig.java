package com.chirag.flex.config;

import com.chirag.flex.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public JwtFilter jwtFilter(JwtUtil jwtUtil) {
        return new JwtFilter(jwtUtil);
    }

    // ✅ CORS CONFIG (Netlify + frontend allowed)
    @Bean
    public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {

        org.springframework.web.cors.CorsConfiguration config =
                new org.springframework.web.cors.CorsConfiguration();

        config.setAllowedOrigins(List.of(
                "https://flexionsa.in",
                "https://www.flexionsa.in",
                "https://courageous-manatee-c59108.netlify.app"
        ));

        config.setAllowedMethods(java.util.List.of("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowedHeaders(java.util.List.of("*"));
        config.setAllowCredentials(true);

        org.springframework.web.cors.UrlBasedCorsConfigurationSource source =
                new org.springframework.web.cors.UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JwtFilter jwtFilter) throws Exception {

        http
            // ❌ disable CSRF (REST APIs)
            .csrf(csrf -> csrf.disable())

            // ✅ enable CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // ✅ stateless API (IMPORTANT for JWT)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // ✅ AUTH RULES
            .authorizeHttpRequests(auth -> auth

                // allow preflight requests
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // public APIs
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/file/upload").permitAll()
                .requestMatchers("/uploads/**").permitAll()

                // secured APIs
                .requestMatchers("/payment/**").hasRole("USER")
                .requestMatchers("/ads/**").hasRole("USER")
                .requestMatchers("/admin/**").hasRole("ADMIN")

                .anyRequest().authenticated()
            )

            // ✅ JWT FILTER
            .addFilterBefore(jwtFilter,
                    UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}