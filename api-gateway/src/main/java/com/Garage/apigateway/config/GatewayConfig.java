package com.Garage.apigateway.config;

import io.netty.handler.codec.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

// Example code using Spring Cloud Gateway
@Configuration
public class GatewayConfig {

    @Autowired
    private AuthGatewayFilterFactory authGatewayFilterFactory;


    //
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.addAllowedOrigin("http://localhost:3000"); // Allow requests from frontend URL
        corsConfig.addAllowedHeader("*");
        corsConfig.addAllowedMethod(String.valueOf(HttpMethod.GET));
        corsConfig.addAllowedMethod(String.valueOf(HttpMethod.POST));
        corsConfig.addAllowedMethod(String.valueOf(HttpMethod.PUT));
        corsConfig.addAllowedMethod(String.valueOf(HttpMethod.DELETE));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }




    //

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()





                .route("auth-service", r -> r
                        .path("/auth/**")

                        .uri("lb://auth-service/**"))

                .route("vehicle-owner-service", r -> r
                        .path("/api/owner/**")

                        .uri("lb://vehicle-owner-service/**"))


                .route("appointment-service", r -> r
                        .path("/api/appointments/**")
                        .filters(f -> f.filter(authGatewayFilterFactory.apply(new AuthGatewayFilterFactory.Config())))
                        .uri("lb://appointment-service/**"))

                .route("garage-service", r -> r
                        .path("/api/services/**")

                        .uri("lb://garage-service/**"))

                .route("vehicle-service", r -> r
                        .path("/api/vehicle/**")
                        .filters(f -> f.filter(authGatewayFilterFactory.apply(new AuthGatewayFilterFactory.Config())))
                        .uri("lb://vehicle-service/**"))

//                .route("vehicle-service", r -> r
//                        .path("/api/vehicle/**")
//                        .filters(f -> f.filter(authGatewayFilterFactory.apply(new AuthGatewayFilterFactory.Config(new String[]{"CUSTOMER"}))))
//                        .uri("lb://vehicle-service/**"))




//















                .build();
    }
}


