package project.gateway.product.core.web.application;

import project.gateway.product.core.domain.entity.Product;

public interface ProductSearchUseCase {

    Product findProductById(Long productId);
}
