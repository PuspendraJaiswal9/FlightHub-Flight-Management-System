package com.example.api_gateway.config;

import com.example.api_gateway.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(exchanges -> exchanges
                        // Publicly accessible endpoints
                        .pathMatchers("/user/login","/user/register", "/eureka/**", "/swagger-ui.html", "/swagger-ui/**",
                                "/v3/api-docs/**","flight/search", "/swagger-resources/**", "/webjars/**","actuator/**")
                        .permitAll()

                        // Only MANAGER or OWNER can register new users
                        .pathMatchers("/flight/**").hasAnyRole("ADMIN", "USER")
                        .pathMatchers("/bookings/**").hasAnyRole("ADMIN", "USER")
                        .pathMatchers("/fare/**").hasAnyRole("ADMIN", "USER")
                        .pathMatchers("/payment/**").hasAnyRole("ADMIN", "USER")
                        // All others must be authenticated
                        .anyExchange()
                        .authenticated()
                )
                .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }




    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

