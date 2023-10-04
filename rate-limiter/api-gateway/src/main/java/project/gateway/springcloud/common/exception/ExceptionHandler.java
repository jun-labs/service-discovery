package project.gateway.springcloud.common.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.Order;
import org.springframework.core.codec.Hints;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import project.gateway.product.common.codeandmessage.common.CommonErrorCodeAndMessage;
import project.gateway.product.common.response.ErrorResponse;
import reactor.core.publisher.Mono;

@Component
@Order(-1)
public class ExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    @Autowired
    public ExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(
        ServerWebExchange exchange,
        Throwable ex
    ) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ErrorResponse errorResponse;
        if (ex instanceof ResponseStatusException exception) {
            HttpStatusCode statusCode = exception.getStatusCode();
            response.setStatusCode(statusCode);
            CommonErrorCodeAndMessage codeAndMessage =
                CommonErrorCodeAndMessage.findByStatusCode(statusCode.value());
            errorResponse = new ErrorResponse(codeAndMessage);
        } else {
            response.setStatusCode(HttpStatus.valueOf(500));
            errorResponse = new ErrorResponse(CommonErrorCodeAndMessage.INTERNAL_SERVER_ERROR);
        }

        DataBufferFactory bufferFactory = response.bufferFactory();
        return response.writeWith(
            new Jackson2JsonEncoder(objectMapper).encode(
                Mono.just(errorResponse),
                bufferFactory,
                ResolvableType.forInstance(errorResponse),
                MediaType.APPLICATION_JSON,
                Hints.from(Hints.LOG_PREFIX_HINT, exchange.getLogPrefix())
            )
        );
    }
}
