package com.bluto.utils.exception;

/**
 * ${file_name}
 *
 * @author bluto
 * @Description: ${todo}
 * @date 2020/5/21 20:46
 */
public class BltUtilException extends RuntimeException {

    private ErrorInfo errorInfo;

    public BltUtilException(ErrorInfo errorInfo) {
        super(errorInfo.toString());
        this.errorInfo = errorInfo;
    }

    public BltUtilException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo.toString(), cause);
        this.errorInfo = errorInfo;
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
