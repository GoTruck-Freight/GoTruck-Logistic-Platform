package com.gotruck.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

//package com.gotruck.apigateway.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//@Configuration
//@EnableWebFluxSecurity
//public class SecurityConfig {
//
//    private static final String[] SWAGGER_WHITELIST = {
//            "/v3/api-docs/**",
//            "/swagger-ui/**",
//            "/swagger-ui.html",
//    };
//
//    @Bean
//    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
//        http
//                .authorizeExchange(exchange -> exchange
//                        .anyExchange().authenticated()
//                )
//                .oauth2Login(Customizer.withDefaults())
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(Customizer.withDefaults())
//                );
//
//        return http.build();
//    }
//}
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

}
