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

    /**
     * the java layer exception, do not same to the logic error
     */
    public static final int NETWORK_ERROR = -1; // the network relative error
    public static final int JSON_ERROR = -2; // the JSON relative error
    public static final int OTHER_ERROR = -3; // the unknow error

    public static final String EMPTY_MSG = "内容为空";

    public static final String JSON_E = "json解析异常";


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
