package project.springcloud.product.core.web.exception;

import lombok.Getter;
import project.springcloud.common.codeandmessage.ErrorCodeAndMessage;
import project.springcloud.common.exception.BusinessException;

@Getter
public class ProductNotFoundException extends BusinessException {

    public ProductNotFoundException(ErrorCodeAndMessage codeAndMessage) {
        super(codeAndMessage);
    }
}
