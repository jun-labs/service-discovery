package project.springcloud.common.codeandmessage.common;

import java.util.Arrays;

import project.springcloud.common.codeandmessage.ErrorCodeAndMessage;

public enum CommonErrorCodeAndMessage implements ErrorCodeAndMessage {
    INVALID_CLIENT_REQUEST(
        400,
        "올바르지 않은 요청입니다.",
        "Invalid client request."
    ),
    API_SPEC_UN_MATCHED(
        400,
        "외부 서버 API 스펙과 매핑을 확인해주세요.",
        "Please check the external server API specification and mapping."
    ),
    INVALID_PATH_VARIABLE(
        400,
        "올바른 PATH 경로를 확인해주세요.",
        "Please check the correct path."
    ),
    INVALID_QUERY_PARAMETERS(
        400,
        "올바른 파라미터를 입력해주세요.",
        "Please enter the correct parameters."
    ),
    UN_AUTHORIZED(
        401,
        "권한이 존재하지 않습니다.",
        "Permission does not exist."
    ),
    URL_NOTFOUND(
        404,
        "존재하지 않는 URL 입니다.",
        "URL does not exist."
    ),
    TOO_MANY_REQUEST(
        429,
        "너무 많은 요청을 보냈습니다.",
        "Too many request."
    ),
    INTERNAL_SERVER_ERROR(500,
        "서버 내부 오류입니다.",
        "Internal server error."
    ),
    BAD_GATEWAY(502,
        "외부 서버와 통신하는 과정에서 오류가 발생했습니다.",
        "An error occurred while communicating with the external server."
    );

    private final int code;
    private final String krErrorMessage;
    private final String engErrorMessage;

    CommonErrorCodeAndMessage(
        int code,
        String krErrorMessage,
        String engErrorMessage
    ) {
        this.code = code;
        this.krErrorMessage = krErrorMessage;
        this.engErrorMessage = engErrorMessage;
    }

    public static CommonErrorCodeAndMessage findByStatusCode(int code) {
        return Arrays.stream(values())
            .filter(statusCode -> statusCode.code == code)
            .findAny()
            .orElseGet(() -> CommonErrorCodeAndMessage.INTERNAL_SERVER_ERROR);
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
