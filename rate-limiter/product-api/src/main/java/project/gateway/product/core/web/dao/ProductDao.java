package project.gateway.product.core.web.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;
import project.gateway.product.core.domain.entity.Product;
import project.gateway.product.core.domain.repository.ProductRepository;

@Repository
public class ProductDao implements ProductRepository {

    private final Map<Long, Product> factory;

    public ProductDao() {
        factory = new HashMap<>();
        factory.put(1L, new Product(1L, "스터디 카페 1주일 이용권", new BigDecimal(10000L)));
        factory.put(2L, new Product(2L, "스터디 카페 2주일 이용권", new BigDecimal(15000L)));
        factory.put(3L, new Product(3L, "스터디 카페 3주일 이용권", new BigDecimal(18000L)));
    }

    public Product findById(Long productId) {
        return factory.get(productId);
    }
}
