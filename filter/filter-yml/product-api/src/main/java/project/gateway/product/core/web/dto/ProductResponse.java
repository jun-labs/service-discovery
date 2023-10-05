package project.gateway.product.core.web.dto;

import lombok.Getter;

@Getter
public class ProductResponse {

    private Long id;

    private ProductResponse() {
    }

    public ProductResponse(Long productId) {
        this.id = productId;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
