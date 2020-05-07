package com.bluto.fc.exception;

import com.bluto.fc.model.request.bo.ErrorCodeEnum;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ErrorInfo errorInfo;

    public BusinessException(ErrorInfo errorInfo) {
        super(errorInfo.toString());
        this.errorInfo = errorInfo;
    }

    public BusinessException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo.toString(), cause);
        this.errorInfo = errorInfo;
    }


    public BusinessException(ErrorCodeEnum errorCodeEnum, MessageSource messageSource, Object... params) {
        String errorCode = errorCodeEnum.getErrorCode();
        String errorMsg = messageSource.getMessage(errorCode, params, errorCode, Locale.SIMPLIFIED_CHINESE);
        errorInfo = new ErrorInfo(errorCode, errorMsg);
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorInfo.getErrCode();
    }

    public String getErrorMsg() {
        return errorInfo.getErrMsg();
    }
}

