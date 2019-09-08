package com.prim.lib_network.okhttp;

import com.prim.lib_network.HttpClient;
import com.prim.lib_network.callback.Callback;
import com.prim.lib_network.header.HttpHeaders;
import com.prim.lib_network.request.BaseRequest;
import com.prim.lib_network.request.CommonRequest;
import com.prim.lib_network.request.HttpParams;
import com.prim.lib_network.request.HttpRequest;

import java.util.Map;

import okhttp3.Headers;
import okhttp3.OkHttpClient;

/**
 * @author prim
 * @version 1.0.0
 * @desc 默认为okhttp的请求
 * @time 2019-09-05 - 23:57
 * @contact https://jakeprim.cn
 * @name PrimFastCC_Android
 */
public class OkHttpRequest implements HttpRequest {
    private OkHttpClient okHttpClient;

    @Override
    public void get(String url, HttpParams params, HttpHeaders headers, Callback callback) {
        StringBuilder urlBuilder = new StringBuilder(url).append("?");
//        for (Map.Entry<String, String> entry : params.params.entrySet()) {
//            //参数遍历
//            urlBuilder.append(entry.getKey()).append("=").append(entry.getValue());
//        }
//
//        Headers.Builder mHeaderBuilder = new Headers.Builder();
//        if (headers != null) {
//            for (Map.Entry<String, String> entry : headers.urlParams.entrySet()) {
//                //请求头遍历
//                mHeaderBuilder.add(entry.getKey(), entry.getValue());
//            }
//        }
    }


    @Override
    public void post(String url, HttpParams params, HttpHeaders headers, Callback callback) {

    }

    @Override
    public void cancelRequest(String tag) {

    }

    @Override
    public void cancelAllRequest() {

    }

    @Override
    public void download(String url, HttpParams params, HttpHeaders headers, Callback callback) {

    }

    @Override
    public void upload(String url, HttpParams params, HttpHeaders headers, Callback callback) {

    }

    @Override
    public void generateRequest(CommonRequest commonRequest, BaseRequest request) {

    }

    @Override
    public void setHttpClient(HttpClient httpClient) {
        okHttpClient = (OkHttpClient) httpClient;
    }
}
