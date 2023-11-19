package project.springcloud.product.core.web.dto;

import lombok.Getter;
import project.springcloud.product.core.domain.entity.Product;

import java.math.BigDecimal;

@Getter
public class ProductResponse {

    private Long id;
    private String title;
    private BigDecimal price;

    private ProductResponse() {
    }

    public ProductResponse(
        Long id,
        String title,
        BigDecimal price
    ) {
        this.id = id;
        this.title = title;
        this.price = price;
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
