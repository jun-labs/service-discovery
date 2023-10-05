package project.gateway.product.core.web.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.gateway.product.common.response.ApiResponse;
import project.gateway.product.core.web.dto.ProductResponse;

import static project.gateway.product.common.codeandmessage.common.SuccessCodeAndMessage.OK;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductSearchAPI {

    @GetMapping("/{productId}")
    public ApiResponse<ProductResponse> findProductById(
            @PathVariable("productId") Long productId
    ) {
        log.info("[Product/Presentation]----xx> Search Request: {}", productId);
        return ApiResponse.of(OK, new ProductResponse(1L));
    }
}
