package project.gateway.springcloud.common.configuration.ratelimiter.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import project.gateway.springcloud.common.configuration.ratelimiter.RateLimiter;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Primary
@Component
public class RateLimiterFilterFactory extends
        AbstractGatewayFilterFactory<RateLimiterFilterFactory.Config> {

    private final RateLimiter rateLimiter;
    private static final RateLimiter.Config slidingWindowConfig = new RateLimiter.Config();

    public RateLimiterFilterFactory(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String ipAddress = extractIpAddress(exchange);
            return rateLimiter.isAllowed(ipAddress, slidingWindowConfig)
                    .flatMap(isAllowed(exchange, chain));
        };
    }

    private static Function<Boolean, Mono<? extends Void>> isAllowed(
            ServerWebExchange exchange,
            GatewayFilterChain chain
    ) {
        return isAllowed ->
                Boolean.TRUE.equals(isAllowed) ? chain.filter(exchange)
                        : Mono.error(new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS));
    }

    private String extractIpAddress(ServerWebExchange exchange) {
        return exchange.getRequest()
                .getRemoteAddress().getAddress()
                .getHostAddress();
    }

    @Getter
    @Setter
    public static class Config {

        int statusCode;
    }

}
