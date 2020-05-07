package com.bluto.fc.model.request.bo;

public enum ErrorCodeEnum {
    SYSTEM_ERROR("K9999"),
    REQUEST_PARAM_EMPTY("E0001"),
    INSERT_TB_ERROR("E0002"),
    UPDATE_TB_ERROR("E0003"),
    DELETE_TB_ERROR("E0004"),
    DATA_NO_FOUND("E0005");

    private String errorCode;

    private ErrorCodeEnum(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
