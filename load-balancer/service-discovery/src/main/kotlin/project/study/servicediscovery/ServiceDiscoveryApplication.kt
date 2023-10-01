package project.study.servicediscovery

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@EnableEurekaServer
@SpringBootApplication
class ServiceApplication

fun main(args: Array<String>) {
    runApplication<ServiceApplication>(*args)
}
