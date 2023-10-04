package project.gateway.product.common.exception;

import lombok.Getter;
import project.gateway.product.common.codeandmessage.ErrorCodeAndMessage;

@Getter
public class DomainException extends RuntimeException {

    private final ErrorCodeAndMessage codeAndMessage;

    public DomainException(ErrorCodeAndMessage codeAndMessage) {
        super(codeAndMessage.getKrErrorMessage());
        this.codeAndMessage = codeAndMessage;
    }
}
