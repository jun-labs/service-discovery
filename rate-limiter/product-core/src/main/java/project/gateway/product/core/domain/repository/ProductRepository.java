package project.gateway.product.core.domain.repository;

import project.gateway.product.core.domain.entity.Product;

public interface ProductRepository {

    Product findById(Long productId);
}
