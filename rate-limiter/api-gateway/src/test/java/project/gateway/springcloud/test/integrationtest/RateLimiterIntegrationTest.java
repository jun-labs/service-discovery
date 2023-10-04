package project.gateway.springcloud.test.integrationtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import project.gateway.springcloud.common.configuration.ratelimiter.RateLimiter;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("[IntegrationTest] RateLimiter 통합 테스트")
class RateLimiterIntegrationTest {

    @Autowired
    private RateLimiter rateLimiter;

    @Autowired
    private ReactiveRedisTemplate<String, String> redisTemplate;

    @BeforeEach
    void setUp() {
        redisTemplate.delete("192.168.0.1").block();
    }

    @Test
    @DisplayName("일정 기간 동안 최대 요청 개수를 초과하면, 다음 요청이 거절된다.")
    void rate_limit_test() {
        RateLimiter.Config config = new RateLimiter.Config();
        Flux<Boolean> resultsFlux = Flux.range(0, 5)
                .delayElements(Duration.ofMillis(1))
                .flatMap(i -> rateLimiter.isAllowed("192.168.0.1", config), 1);

        StepVerifier.create(resultsFlux)
                .expectNext(true, true, true, true)
                .expectNext(false)
                .verifyComplete();
    }
}
