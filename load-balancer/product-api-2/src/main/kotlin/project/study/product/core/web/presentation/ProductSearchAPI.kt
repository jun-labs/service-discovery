package project.study.product.core.web.presentation

import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
@RequestMapping("/api/products")
class ProductSearchAPI {

    val log: Logger = LoggerFactory.getLogger(ProductSearchAPI::class.java)

    @GetMapping("/{productId}")
    fun findProductById(@PathVariable("productId") productId: Long): ResponseEntity<ProductResponse> {
        log.info("----x> SERVER-2")
        return ResponseEntity.ok(ProductResponse(1L, "퍼즐"))
    }
}

data class ProductResponse(
    val productId: Long,
    val name: String
)
