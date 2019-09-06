package com.prim.lib_network.okhttp;

import com.prim.lib_network.header.HttpHeaders;
import com.prim.lib_network.request.BaseRequest;
import com.prim.lib_network.request.CommonRequest;
import com.prim.lib_network.request.HttpParams;
import com.prim.lib_network.request.HttpRequest;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-09-05 - 23:57
 * @contact https://jakeprim.cn
 * @name PrimFastCC_Android
 */
public class OkHttpRequest implements HttpRequest {
    @Override
    public void get(String url, HttpParams params, HttpHeaders headers) {

    }

    @Override
    public void post(String url, HttpParams params, HttpHeaders headers) {

    }

    @Override
    public void cancelRequest(String tag) {

    }

    @Override
    public void cancelAllRequest() {

    }

    @Override
    public void download(String url, HttpParams params, HttpHeaders headers) {

    }

    @Override
    public void upload(String url, HttpParams params, HttpHeaders headers) {

    }

    @Override
    public void generateRequest(CommonRequest commonRequest, BaseRequest request) {

    }
}
