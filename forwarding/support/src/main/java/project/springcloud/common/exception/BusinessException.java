package project.springcloud.common.exception;

import lombok.Getter;
import project.springcloud.common.codeandmessage.ErrorCodeAndMessage;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCodeAndMessage codeAndMessage;

    public BusinessException(ErrorCodeAndMessage codeAndMessage) {
        super(codeAndMessage.getKrErrorMessage());
        this.codeAndMessage = codeAndMessage;
    }
}
