package ro.sci.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

@Configuration
public class GatewayConfig {

    @Bean
    public GlobalFilter apiKeyHeaderFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            HttpHeaders headers = request.getHeaders();
            if (headers.containsKey("X-Api-Key")) {
                String apiKey = headers.getFirst("X-Api-Key");
                ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                        .header("X-Api-Key", apiKey)
                        .build();
                exchange = exchange.mutate().request(modifiedRequest).build();
            }
            return chain.filter(exchange);
        };
    }
}
