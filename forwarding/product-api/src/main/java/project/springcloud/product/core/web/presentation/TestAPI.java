package project.springcloud.product.core.web.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.springcloud.product.core.web.dto.ProductResponse;

import java.math.BigDecimal;

@RestController
@RequestMapping("/test")
public class TestAPI {

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> searchTest(@PathVariable Long productId) {
        String title = String.format("Title: Product%s", productId);
        ProductResponse payload = new ProductResponse(productId, title, BigDecimal.valueOf(productId * 10000));
        return ResponseEntity.ok(payload);
    }
}
