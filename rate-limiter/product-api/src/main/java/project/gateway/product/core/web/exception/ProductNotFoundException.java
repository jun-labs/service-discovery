package project.gateway.product.core.web.exception;

import lombok.Getter;
import project.gateway.product.common.codeandmessage.ErrorCodeAndMessage;
import project.gateway.product.common.exception.DomainException;

@Getter
public class ProductNotFoundException extends DomainException {

    public ProductNotFoundException(ErrorCodeAndMessage codeAndMessage) {
        super(codeAndMessage);
    }
}
