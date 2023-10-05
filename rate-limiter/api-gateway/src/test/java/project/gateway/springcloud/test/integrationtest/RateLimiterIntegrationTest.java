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

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    @DisplayName("모든 최대 요청 횟수(3)를 초과하면 요청 불가(TRUE)를 반환한다.")
    void increase_too_many_request_count_test() {
        String ipAddress = "192.168.0.1";

        assertEquals(Boolean.FALSE, rateLimiter.increaseTooManyRequestAndGet(ipAddress).block());
        assertEquals(Boolean.FALSE, rateLimiter.increaseTooManyRequestAndGet(ipAddress).block());
        assertEquals(Boolean.TRUE, rateLimiter.increaseTooManyRequestAndGet(ipAddress).block());
    }
}
