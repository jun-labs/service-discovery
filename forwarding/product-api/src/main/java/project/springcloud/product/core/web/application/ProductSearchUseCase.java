package project.springcloud.product.core.web.application;

import project.springcloud.product.core.domain.entity.Product;

public interface ProductSearchUseCase {

    Product findProductById(Long productId);
}
