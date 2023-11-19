package project.springcloud.product.core.domain.entity;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
public class Product {

    private Long id;
    private String title;
    private BigDecimal price;

    public Product(
        Long id,
        String title,
        BigDecimal price
    ) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Product product)) {
            return false;
        }
        return getId().equals(product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
