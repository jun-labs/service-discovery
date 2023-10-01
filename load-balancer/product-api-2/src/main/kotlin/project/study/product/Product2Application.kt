package project.study.product

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class Product2Application

fun main(args: Array<String>) {
    runApplication<Product2Application>(*args)
}
