package project.gateway.product.common.codeandmessage.common;

import project.gateway.product.common.codeandmessage.CodeAndMessage;

public enum SuccessCodeAndMessage implements CodeAndMessage {
    OK(200),
    CREATED(201);

    private final int code;

    SuccessCodeAndMessage(int code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public int getStatusCode() {
        return code;
    }
}
