package com.prim.lib_network;

import com.prim.lib_network.callback.Callback;
import com.prim.lib_network.header.HttpHeaders;
import com.prim.lib_network.request.BaseRequest;
import com.prim.lib_network.request.GetRequest;
import com.prim.lib_network.request.HttpParams;
import com.prim.lib_network.request.HttpRequest;
import com.prim.lib_network.request.PostRequest;

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

    private CommonHttpClient commonHttpClient;

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

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    public void setHttpClient(CommonHttpClient client) {
        this.commonHttpClient = client;
        setHttpClient(client.getHttpClient());
    }

    public HttpParams getParams() {
        if (commonHttpClient == null)
            throw new IllegalArgumentException("Need setHttpClient");
        return commonHttpClient.getParams();
    }

    public HttpHeaders getHeaders() {
        return commonHttpClient.getHeaders();
    }

    public String getBaseUrl() {
        return commonHttpClient.getBaseUrl();
    }

    //-----------------------------------------------------------------//

    @Override
    public void get(String url, HttpParams params, HttpHeaders headers, Callback callback) {
        httpRequest.get(url, params, headers, callback);
    }

    @Override
    public void post(String url, HttpParams params, HttpHeaders headers, Callback callback) {
        httpRequest.post(url, params, headers, callback);
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
    public void download(String url, HttpParams params, HttpHeaders headers, Callback callback) {
        httpRequest.download(url, params, headers, callback);
    }

    @Override
    public void upload(String url, HttpParams params, HttpHeaders headers, Callback callback) {
        httpRequest.upload(url, params, headers, callback);
    }

    @Override
    public void generateRequest(CommonRequest commonRequest, BaseRequest request) {
        httpRequest.generateRequest(commonRequest, request);
    }

    @Override
    public void setHttpClient(HttpClient httpClient) {
        httpRequest.setHttpClient(httpClient);
    }
}
