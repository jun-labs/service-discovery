package project.springcloud.product.core.domain.repository;

import project.springcloud.product.core.domain.entity.Product;

public interface ProductRepository {

    Product findById(Long productId);
}
