package com.prim.lib_network.exception;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-09-06 - 11:38
 * @contact https://jakeprim.cn
 * @name PrimFastCC_Android
 */
public class HttpException extends Exception {
    private int errorCode;

    private Object errorMsg;

    public HttpException(int errorCode, Object errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public Object getErrorMsg() {
        return errorMsg;
    }
}
