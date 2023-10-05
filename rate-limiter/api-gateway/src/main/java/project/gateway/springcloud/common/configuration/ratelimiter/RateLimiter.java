package project.gateway.springcloud.common.configuration.ratelimiter;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class RateLimiter {

    private final ReactiveRedisTemplate<String, String> redisTemplate;
    private final DefaultRedisScript<Long> redisScript;

    @Autowired
    public RateLimiter(ReactiveRedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisScript = new DefaultRedisScript<>(getLuaScript(), Long.class);
    }

    public Mono<Boolean> isAllowed(
            String ipAddress,
            Config config
    ) {
        long currentTimeMillis = Instant.now().toEpochMilli();
        long windowSizeInMillis = config.getWindowSizeInMillis();

        List<String> keys = Collections.singletonList(ipAddress);
        List<String> args = Arrays.asList(
                "0.0",
                String.valueOf(currentTimeMillis - windowSizeInMillis),
                String.valueOf(currentTimeMillis),
                String.valueOf(currentTimeMillis),
                String.valueOf(windowSizeInMillis / 1000)
        );

        return redisTemplate.execute(redisScript, keys, args)
                .next()
                .map(count -> count <= config.getMaxRequests())
                .defaultIfEmpty(true);
    }

    private String getLuaScript() {
        Resource resource = new ClassPathResource("sliding-window.lua");
        try {
            return new String(Files.readAllBytes(Paths.get(resource.getURI())));
        } catch (Exception exception) {
            throw new IllegalStateException();
        }
    }

    public Mono<Boolean> increaseTooManyRequestCount(String ipAddress) {
        String key = getKey(ipAddress);
        return redisTemplate.opsForValue()
                .increment(key)
                .flatMap(count -> redisTemplate.expire(key, Duration.ofMinutes(1))
                        .thenReturn(count))
                .map(count -> count >= 3);
    }

    private String getKey(String ipAddress) {
        return String.format("ip:string:%s", ipAddress);
    }

    @Getter
    public static class Config {

        private int maxRequests = 4;
        private long windowSizeInMillis = 1000;
    }
}
