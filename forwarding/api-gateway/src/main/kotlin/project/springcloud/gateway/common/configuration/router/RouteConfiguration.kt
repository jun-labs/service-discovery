//package project.springcloud.gateway.common.configuration.router
//
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.cloud.gateway.route.RouteLocator
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//
//@Configuration
//class RouteConfiguration(
//    @Value("\${location.product}")
//    private val productServerUrl: String
//) {
//    @Bean
//    fun gatewayRoutes(builder: RouteLocatorBuilder): RouteLocator {
//        return builder.routes()
//            .route { router ->
//                router.path("/**")
//                    .uri(productServerUrl)
//            }
//            .build()
//    }
//}