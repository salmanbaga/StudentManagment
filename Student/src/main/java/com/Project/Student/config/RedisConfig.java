package com.Project.Student.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
    @EnableCaching
    public class RedisConfig {

        // ✅ Cache configuration (JSON serializer + TTL)
        @Bean
        public RedisCacheConfiguration cacheConfiguration() {
            return RedisCacheConfiguration.defaultCacheConfig()
                    // TTL (Time To Live) -> 10 minutes
                    .entryTtl(Duration.ofMinutes(10))

                    // null values cache nahi karega
                    .disableCachingNullValues()

                    // JSON serialization use karega (IMPORTANT)
                    .serializeValuesWith(
                            RedisSerializationContext.SerializationPair
                                    .fromSerializer(new GenericJackson2JsonRedisSerializer())
                    );
        }

        // ✅ Cache Manager (Spring ko batata hai kaise cache handle karna hai)
        @Bean
        public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
            return RedisCacheManager.builder(connectionFactory)
                    .cacheDefaults(cacheConfiguration())
                    .build();
        }
}
