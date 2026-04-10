package com.atmos.gateway_service.Config;

import io.lettuce.core.RedisClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisClientConfig {

    @Bean
    public RedisClient redisClient() {

        return RedisClient.create(
                "redis://default:H6thVhIdc0ac57WNK5W8G8EbiIe5Bi7w@redis-14359.crce179.ap-south-1-1.ec2.cloud.redislabs.com:14359");
    }
}