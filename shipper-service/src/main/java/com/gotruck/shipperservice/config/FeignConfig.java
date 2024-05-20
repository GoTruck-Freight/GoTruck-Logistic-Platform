package com.gotruck.shipperservice.config;

import com.gotruck.shipperservice.service.JwtService;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestTokenBearerInterceptor(){
        return requestTemplate -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication != null && authentication.getCredentials() != null){
                String jwt = (String) authentication.getCredentials();
                if (jwt.startsWith("Bearer ")){
                    jwt = jwt.substring(7);
                }
                requestTemplate.header("Authorization", "Bearer " + jwt);
            }
        };
    }
}
