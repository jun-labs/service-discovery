package project.gateway.product.domain.product.repository;

import project.gateway.product.domain.product.entity.Product;

public interface ProductRepository {

    Product findById(Long productId);
}
