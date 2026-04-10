package com.atmos.gateway_service.Service;

import com.atmos.gateway_service.Config.RateLimitProperties;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.Refill;
import io.github.bucket4j.distributed.AsyncBucketProxy;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimiterService {

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private RateLimitProperties rateLimitProperties;

    private ProxyManager<byte[]> proxyManager;

    @PostConstruct
    public void init() {
        StatefulRedisConnection<byte[], byte[]> connection = redisClient.connect(RedisCodec.of(new ByteArrayCodec(), new ByteArrayCodec()));
        this.proxyManager = LettuceBasedProxyManager.builderFor(connection)
                .build();
    }

    public AsyncBucketProxy resolveBucket(String category, String key) {
        RateLimitProperties.CategoryConfig config = rateLimitProperties.getCategories().get(category);
        if (config == null) {
            // Fallback to a default very restrictive limit if category not found
            config = new RateLimitProperties.CategoryConfig();
            config.setCapacity(1);
            config.setRefillTokens(1);
            config.setRefillDuration(1);
            config.setDurationUnit(java.util.concurrent.TimeUnit.MINUTES);
        }

        BucketConfiguration bucketConfiguration = BucketConfiguration.builder()
                .addLimit(Bandwidth.classic(config.getCapacity(), 
                        Refill.intervally(config.getRefillTokens(), 
                                Duration.of(config.getRefillDuration(), 
                                        config.getDurationUnit().toChronoUnit()))))
                .build();

        return proxyManager.asAsync().builder().build((category + ":" + key).getBytes(), bucketConfiguration);
    }
}
