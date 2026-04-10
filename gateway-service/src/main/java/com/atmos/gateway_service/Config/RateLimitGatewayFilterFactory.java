package com.atmos.gateway_service.Config;

import com.atmos.gateway_service.Service.RateLimiterService;
import io.github.bucket4j.distributed.AsyncBucketProxy;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;

@Component
public class RateLimitGatewayFilterFactory extends AbstractGatewayFilterFactory<RateLimitGatewayFilterFactory.Config> {

    @Autowired
    private RateLimiterService rateLimiterService;

    public RateLimitGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("category");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String category = config.getCategory();
            String clientIp = getClientIp(exchange);
            
            AsyncBucketProxy bucket = rateLimiterService.resolveBucket(category, clientIp);
            
            return Mono.fromFuture(bucket.tryConsume(1))
                .flatMap(consumed -> {
                    if (consumed) {
                        return chain.filter(exchange);
                    } else {
                        exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                        String errorBody = "{\"status\": 429, \"error\": \"Too Many Requests\", \"message\": \"Rate limit exceeded for " + category + "\"}";
                        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(errorBody.getBytes())));
                    }
                });
        };
    }

    private String getClientIp(ServerWebExchange exchange) {
        InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
        if (remoteAddress != null) {
            return remoteAddress.getAddress().getHostAddress();
        }
        return "anonymous";
    }

    @Data
    public static class Config {
        private String category;
    }
}
