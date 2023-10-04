package project.gateway.product.core.web.application;

import project.gateway.product.domain.product.entity.Product;

public interface ProductSearchUseCase {

    Product findProductById(Long productId);
}
