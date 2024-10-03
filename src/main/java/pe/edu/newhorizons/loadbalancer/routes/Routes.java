package pe.edu.newhorizons.loadbalancer.routes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.List;

import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;


@Configuration
public class Routes {
    @Bean
    public RouterFunction<ServerResponse> clienteServiceRoute(){
        return route("APPMONGO")
                .GET("/api/v1/auth/**", http())
                .POST("/api/v1/auth/**", http())
                .filter( lb("APPMONGO") )
                .build();
    }

    @Bean
    public CorsWebFilter corsFilter(){
        CorsConfigurationSource source = new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(ServerWebExchange exchange) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowCredentials(true);
                config.setAllowedOriginPatterns(List.of("*"));
                config.addAllowedHeader("*");
                config.addAllowedMethod("*");
                return config;
            }
        };
        return new CorsWebFilter( source );
    }
}
