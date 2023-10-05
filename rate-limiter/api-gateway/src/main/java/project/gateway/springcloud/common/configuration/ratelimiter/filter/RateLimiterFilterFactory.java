package project.gateway.springcloud.common.configuration.ratelimiter.filter;

import lombok.Getter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import project.gateway.springcloud.common.configuration.ratelimiter.RateLimiter;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;
import java.util.function.Function;

public class RateLimiterFilterFactory extends
        AbstractGatewayFilterFactory<RateLimiterFilterFactory.Config> {

    private final RateLimiter rateLimiter;
    private static final RateLimiter.Config slidingWindowConfig = new RateLimiter.Config();
    private static final String TOO_MANY_REQUEST_USER = "RateLimit-Exceeded-User";

    public RateLimiterFilterFactory(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String rateLimitCookie = getRateLimitCookie(exchange);
            if (rateLimitCookie != null) {
                return Mono.error(new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS));
            }

            String ipAddress = extractIpAddress(exchange);
            return rateLimiter.isAllowed(ipAddress, slidingWindowConfig)
                    .flatMap(isAllowed -> {
                        if (!isAllowed) {
                            return increaseTooManyRequestCount(exchange, ipAddress);
                        }
                        return isAllowed(exchange, chain).apply(true);
                    });
        };
    }

    private Mono<Void> increaseTooManyRequestCount(
            ServerWebExchange exchange,
            String ipAddress
    ) {
        return rateLimiter.increaseTooManyRequestCount(ipAddress)
                .flatMap(overRequestCount -> {
                    if (isOverMaxCount(overRequestCount)) {
                        addTooManyRequestCookie(exchange);
                    }
                    return Mono.error(new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS));
                });
    }

    private void addTooManyRequestCookie(ServerWebExchange exchange) {
        ResponseCookie cookie = ResponseCookie.from(TOO_MANY_REQUEST_USER, UUID.randomUUID().toString())
                .path("/")
                .maxAge(Duration.ofMinutes(10))
                .build();
        exchange.getResponse().addCookie(cookie);
    }

    private String getRateLimitCookie(ServerWebExchange exchange) {
        MultiValueMap<String, HttpCookie> cookies = exchange.getRequest().getCookies();

        if (cookies.containsKey(TOO_MANY_REQUEST_USER)) {
            HttpCookie cookie = cookies.getFirst(TOO_MANY_REQUEST_USER);
            return cookie.getValue();
        }
        return null;
    }

    private Function<Boolean, Mono<? extends Void>> isAllowed(
            ServerWebExchange exchange,
            GatewayFilterChain chain
    ) {
        return allowedRequest ->
                isOverMaxCount(allowedRequest) ? chain.filter(exchange)
                        : Mono.error(new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS));
    }

    private String extractIpAddress(ServerWebExchange exchange) {
        return exchange.getRequest()
                .getRemoteAddress().getAddress()
                .getHostAddress();
    }

    private boolean isOverMaxCount(Boolean isTrue) {
        return Boolean.TRUE.equals(isTrue);
    }

    @Getter
    public static class Config {

        int statusCode;
    }

}
