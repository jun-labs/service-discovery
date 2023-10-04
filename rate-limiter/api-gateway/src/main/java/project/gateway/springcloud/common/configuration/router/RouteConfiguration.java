package project.gateway.springcloud.common.configuration.router;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.gateway.springcloud.common.configuration.ratelimiter.filter.RateLimiterFilterFactory;
import project.gateway.springcloud.common.configuration.ratelimiter.RateLimiter;

@Configuration
public class RouteConfiguration {

    private final String productServer;
    private RateLimiterFilterFactory factory;

    public RouteConfiguration(
            @Value("${locations.product}") String productServer,
            RateLimiter rateLimiter
    ) {
        this.productServer = productServer;
        this.factory = new RateLimiterFilterFactory(rateLimiter);
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        RateLimiterFilterFactory.Config config = new RateLimiterFilterFactory.Config();
        return builder.routes()
                .route(router -> router.path("/api/products/**")
                        .filters(f -> f.filter(factory.apply(config)))
                        .uri(productServer))
                .build();
    }
}
