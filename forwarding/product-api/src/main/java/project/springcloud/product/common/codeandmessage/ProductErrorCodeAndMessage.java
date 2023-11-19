package project.springcloud.product.common.codeandmessage;

import project.springcloud.common.codeandmessage.ErrorCodeAndMessage;

public enum ProductErrorCodeAndMessage implements ErrorCodeAndMessage {
    PRODUCT_NOT_FOUND(
        404,
        "상품을 찾을 수 없습니다.",
        "Can not find product."
    );

    private final int code;
    private final String krErrorMessage;
    private final String engErrorMessage;

    ProductErrorCodeAndMessage(
        int code,
        String krErrorMessage,
        String engErrorMessage
    ) {
        this.code = code;
        this.krErrorMessage = krErrorMessage;
        this.engErrorMessage = engErrorMessage;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public int getStatusCode() {
        return code;
    }

    @Override
    public String getKrErrorMessage() {
        return krErrorMessage;
    }

    @Override
    public String getEnErrorMessage() {
        return engErrorMessage;
    }
}
