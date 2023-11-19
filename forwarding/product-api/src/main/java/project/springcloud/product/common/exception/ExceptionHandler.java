package project.springcloud.product.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.springcloud.common.codeandmessage.ErrorCodeAndMessage;
import project.springcloud.common.exception.BusinessException;
import project.springcloud.common.response.ErrorResponse;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> catchDomainException(BusinessException businessException) {
        ErrorCodeAndMessage codeAndMessage = businessException.getCodeAndMessage();
        return ResponseEntity.status(codeAndMessage.getStatusCode())
            .body(new ErrorResponse(codeAndMessage));
    }
}
