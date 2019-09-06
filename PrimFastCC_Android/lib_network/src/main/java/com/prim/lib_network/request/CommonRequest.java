package com.prim.lib_network.request;

import android.content.Context;

import com.prim.lib_network.header.HttpHeaders;

/**
 * @author prim
 * @version 1.0.0
 * @desc 通用的请求可用于动态切换网络框架
 * @time 2019-09-05 - 23:03
 * @contact https://jakeprim.cn
 * @name PrimFastCC_Android
 */
public class CommonRequest implements HttpRequest {

    private HttpRequest httpRequest;

    private static volatile CommonRequest instance;

    private Context context;

    //可通用的参数
    private HttpParams params;

    //可通用的请求头
    private HttpHeaders headers;

    private long connectTimeout;

    private long readTimeout;

    private long writeTimeout;

    private String baseUrl;

    public CommonRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public static CommonRequest initRequest(HttpRequest httpRequest) {
        if (null == instance) {
            synchronized (CommonRequest.class) {
                if (null == instance) {
                    instance = new CommonRequest(httpRequest);
                }
            }
        }
        return instance;
    }

    public GetRequest get(String url) {
        return new GetRequest(this, url);
    }


    public PostRequest post(String url) {
        return new PostRequest(this, url);
    }

    //----------- 初始化值,添加全局的参数和请求头 ------------//

    public CommonRequest with(Context context) {
        this.context = context;
        return this;
    }

    public CommonRequest addCommonParams(String key, String value) {
        params.put(key, value);
        return this;
    }

    public CommonRequest addCommonParams(HttpParams params) {
        params.put(params);
        return this;
    }

    public CommonRequest addCommonHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public CommonRequest addCommonHeader(HttpHeaders httpHeaders) {
        headers.put(httpHeaders);
        return this;
    }

    public CommonRequest setConnectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public CommonRequest setReadTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public CommonRequest setWriteTimeout(long writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    public CommonRequest setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    public HttpParams getParams() {
        return params;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public long getReadTimeout() {
        return readTimeout;
    }

    public long getWriteTimeout() {
        return writeTimeout;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    //-----------------------------------------------------------------//

    @Override
    public void get(String url, HttpParams params, HttpHeaders headers) {
        httpRequest.get(url, params, headers);
    }

    @Override
    public void post(String url, HttpParams params, HttpHeaders headers) {
        httpRequest.post(url, params, headers);
    }

    @Override
    public void cancelRequest(String tag) {
        httpRequest.cancelRequest(tag);
    }

    @Override
    public void cancelAllRequest() {
        httpRequest.cancelAllRequest();
    }

    @Override
    public void download(String url, HttpParams params, HttpHeaders headers) {
        httpRequest.download(url, params, headers);
    }

    @Override
    public void upload(String url, HttpParams params, HttpHeaders headers) {
        httpRequest.upload(url, params, headers);
    }

    @Override
    public void generateRequest(CommonRequest commonRequest, BaseRequest request) {
        httpRequest.generateRequest(commonRequest, request);
    }
}
