package project.gateway.product.core.web.dto;

import java.math.BigDecimal;
import lombok.Getter;
import project.gateway.product.domain.product.entity.Product;

@Getter
public class ProductResponse {

    private Long id;
    private String title;
    private BigDecimal price;

    private ProductResponse() {
    }

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
