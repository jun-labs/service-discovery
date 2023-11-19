package project.springcloud.product.core.web.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.springcloud.product.common.response.ApiResponse;
import project.springcloud.product.core.web.application.service.ProductSearchService;
import project.springcloud.product.core.web.dto.ProductResponse;
import project.springcloud.product.core.domain.entity.Product;

import static project.springcloud.common.codeandmessage.common.SuccessCodeAndMessage.OK;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductSearchAPI {

    private final ProductSearchService productSearchService;

    @GetMapping("/{productId}")
    public ApiResponse<ProductResponse> findProductById(
            @PathVariable("productId") Long productId
    ) {
        log.info("[Product/Presentation]----xx> Search Request: {}", productId);
        Product findProduct = productSearchService.findProductById(productId);
        return ApiResponse.of(OK, new ProductResponse(findProduct));
    }
}
