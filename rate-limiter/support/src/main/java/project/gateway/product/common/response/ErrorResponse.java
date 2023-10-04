package project.gateway.product.common.response;

import lombok.Getter;
import project.gateway.product.common.codeandmessage.ErrorCodeAndMessage;

@Getter
public class ErrorResponse {

    private final int code;
    private final String message;

    private ErrorResponse(
        int code,
        String message
    ) {
        this.code = code;
        this.message = message;
    }

    public ErrorResponse(ErrorCodeAndMessage codeAndMessage) {
        this.code = codeAndMessage.getStatusCode();
        this.message = codeAndMessage.getKrErrorMessage();
    }

    @Override
    public String toString() {
        return String.format("code: %s, message: %s", code, message);
    }
}
