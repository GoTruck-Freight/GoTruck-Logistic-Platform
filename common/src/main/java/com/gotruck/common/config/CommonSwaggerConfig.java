package com.gotruck.common.config;

import com.gotruck.common.config.properties.SwaggerProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;


import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableOpenApi
@Import({BeanValidatorPluginsConfiguration.class})
@ConditionalOnProperty(prefix = "common.swagger", name = "enabled")
public class CommonSwaggerConfig {

    private final SwaggerProperties properties;

    @Bean
    public Docket docket() {
        log.debug("Starting Swagger");
        final StopWatch watch = new StopWatch();
        watch.start();

        final Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(properties.getBasePackage()))
                .paths(regex(properties.getPaths()))
                .build()
                .apiInfo(apiInfo())
                .forCodeGeneration(true)
                .securityContexts(List.of(securityContext()))
                .securitySchemes(List.of(apiKey()));

        watch.stop();
        log.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
        return docket;
    }

    private ApiKey apiKey() {
        return new ApiKey(HttpHeader.AUTHORIZATION, HttpHeader.AUTHORIZATION, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    List<SecurityReference> defaultAuth() {
        final AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference(HttpHeader.AUTHORIZATION, authorizationScopes));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                properties.getTitle(),
                properties.getDescription(),
                properties.getVersion(),
                properties.getTermsOfServiceUrl(),
                new Contact(properties.getContactName(), properties.getContactUrl(), properties.getContactEmail()),
                properties.getLicense(),
                properties.getLicenseUrl(),
                Collections.emptyList());

    }

    @Bean
    public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return new BeanPostProcessor() {

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof WebMvcRequestHandlerProvider) {
                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
                }
                return bean;
            }

            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
                List<T> copy = mappings.stream()
                        .filter(mapping -> mapping.getPatternParser() == null)
                        .toList();
                mappings.clear();
                mappings.addAll(copy);
            }

            @SuppressWarnings("unchecked")
            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
                try {
                    Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
                    assert field != null;
                    field.setAccessible(true);
                    return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        };
    }

    public static class HttpHeader {
        public static final String AUTHORIZATION = "Authorization";
    }
}