package project.springcloud.product.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.springcloud.common.codeandmessage.common.SuccessCodeAndMessage;

public class ApiResponse<T> extends ResponseEntity<ResponsePayload<T>> {

    public ApiResponse(
        T data,
        HttpStatus status,
        String message
    ) {
        super(new ResponsePayload<>(data, status, message), status);
    }

    public static <T> ApiResponse<T> of(SuccessCodeAndMessage codeAndMessage) {
        return new ApiResponse<>(
            null,
            HttpStatus.valueOf(codeAndMessage.getStatusCode()),
            codeAndMessage.getCode()
        );
    }

    public static <T> ApiResponse<T> of(
        SuccessCodeAndMessage codeAndMessage,
        T data
    ) {
        return new ApiResponse<>(
            data,
            HttpStatus.valueOf(codeAndMessage.getStatusCode()),
            codeAndMessage.name()
        );
    }

    @Override
    public String toString() {
        return String.format(
            "data: %s, code: %s, message: %s",
            getBody().getData(),
            getStatusCode(),
            getBody().getMessage()
        );
    }
}
