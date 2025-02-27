package project.springcloud.product.core.web.application.service;

import static project.springcloud.product.common.codeandmessage.ProductErrorCodeAndMessage.PRODUCT_NOT_FOUND;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.springcloud.product.core.web.application.ProductSearchUseCase;
import project.springcloud.product.core.web.exception.ProductNotFoundException;
import project.springcloud.product.core.domain.entity.Product;
import project.springcloud.product.core.domain.repository.ProductRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductSearchService implements ProductSearchUseCase {

    private final ProductRepository productRepository;

    @Override
    public Product findProductById(Long productId) {
        log.info("[Product/Service]----xx> Search Request: {}", productId);
        Product findProduct = productRepository.findById(productId);
        if (findProduct == null) {
            throw new ProductNotFoundException(PRODUCT_NOT_FOUND);
        }
        return productRepository.findById(productId);
    }
}
