package project.gateway.springcloud.common.configuration.router;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.gateway.springcloud.common.configuration.filter.CustomFilter;
import project.gateway.springcloud.common.configuration.filter.GlobalFilter;

@Configuration
public class RoutingConfiguration {

    private final String productServer;
    private final GlobalFilter globalFilter;
    private final GlobalFilter.Config globalFilterConfig = new GlobalFilter.Config();
    private final CustomFilter customFilter;
    private final CustomFilter.Config customFilterConfig = new CustomFilter.Config();

    public RoutingConfiguration(
            @Value("${locations.product}") String productServer,
            GlobalFilter globalFilter,
            CustomFilter customFilter
    ) {
        this.productServer = productServer;
        this.globalFilter = globalFilter;
        this.customFilter = customFilter;
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(router -> router.path("/api/products/**")
                        .filters(f -> f.filter(globalFilter.apply(globalFilterConfig))
                                .filter(customFilter.apply(customFilterConfig))
                        )
                        .uri(productServer))
                .build();
    }
}