package project.gateway.springcloud.common.configuration.ratelimiter.ipaddress;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Optional;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class IpAddressResolver implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Optional.ofNullable(exchange.getRequest().getRemoteAddress())
                .map(InetSocketAddress::getAddress)
                .map(InetAddress::getHostAddress)
                .map(Mono::just)
                .orElse(Mono.empty());
    }

    @Bean
    public KeyResolver remoteAddressResolver() {
        return exchange -> Mono.just(
                exchange.getRequest().getRemoteAddress()
                        .getAddress().getHostAddress()
        );
    }
}
