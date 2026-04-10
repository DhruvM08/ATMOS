package com.atmos.gateway_service.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Data
@Component
@ConfigurationProperties(prefix = "ratelimit")
public class RateLimitProperties {

    private Map<String, CategoryConfig> categories;

    @Data
    public static class CategoryConfig {
        private long capacity;
        private long refillTokens;
        private long refillDuration;
        private TimeUnit durationUnit;
    }
}
