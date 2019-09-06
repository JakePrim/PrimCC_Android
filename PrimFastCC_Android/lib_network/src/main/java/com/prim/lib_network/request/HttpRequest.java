package com.prim.lib_network.request;

import com.prim.lib_network.header.HttpHeaders;

/**
 * @author prim
 * @version 1.0.0
 * @desc 请求封装
 * @time 2019-09-05 - 19:07
 * @contact https://jakeprim.cn
 * @name PrimFastCC_Android
 */
public interface HttpRequest {
    void get(String url, HttpParams params, HttpHeaders headers);

    void post(String url, HttpParams params, HttpHeaders headers);

    void cancelRequest(String tag);

    void cancelAllRequest();

    void download(String url, HttpParams params, HttpHeaders headers);

    void upload(String url, HttpParams params, HttpHeaders headers);

    void generateRequest(CommonRequest commonRequest, BaseRequest request);
}
