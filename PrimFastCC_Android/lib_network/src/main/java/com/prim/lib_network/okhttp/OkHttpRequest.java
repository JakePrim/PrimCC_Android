package com.prim.lib_network.okhttp;

import com.prim.lib_network.CommonHttpClient;
import com.prim.lib_network.HttpClient;
import com.prim.lib_network.callback.Callback;
import com.prim.lib_network.header.HttpHeaders;
import com.prim.lib_network.request.BaseRequest;
import com.prim.lib_network.CommonRequest;
import com.prim.lib_network.request.FileWrapper;
import com.prim.lib_network.request.HttpParams;
import com.prim.lib_network.request.HttpRequest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
        for (Map.Entry<String, String> entry : params.getParams().entrySet()) {
            //参数遍历
            urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        String urlBuildStr = urlBuilder.toString();

        String urlStr = urlBuildStr.substring(0, urlBuildStr.length() - 1);

        Headers.Builder mHeaderBuilder = new Headers.Builder();
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.getHeaders().entrySet()) {
                //请求头遍历
                mHeaderBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        Headers header = mHeaderBuilder.build();
        Request request = new Request.Builder()
                .url(urlStr)
                .get()
                .headers(header)
                .build();
        sendRequest(callback, request);
    }


    @Override
    public void post(String url, HttpParams params, HttpHeaders headers, Callback callback) {

        //添加请求头
        Headers.Builder mHeaderBuild = new Headers.Builder();
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.getHeaders().entrySet()) {
                mHeaderBuild.add(entry.getKey(), entry.getValue());
            }
        }
        Headers mHeader = mHeaderBuild.build();

        Request request = null;

        if (params != null) {
            ConcurrentHashMap<String, List<FileWrapper>> fileParams = params.getFileParams();
            if (!fileParams.isEmpty()) {//文件上传请求
                MultipartBody.Builder mMultipartBuild = new MultipartBody.Builder();
                mMultipartBuild.setType(MultipartBody.FORM);
                for (Map.Entry<String, List<FileWrapper>> entry : fileParams.entrySet()) {
                    List<FileWrapper> entryValue = entry.getValue();
                    for (FileWrapper wrapper : entryValue) {
                        mMultipartBuild.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
                                RequestBody.create(wrapper.mediaType, wrapper.file));
                    }
                }
                request = new Request.Builder().url(url).
                        post(mMultipartBuild.build()).
                        headers(mHeader)
                        .build();
            } else {//表单请求
                FormBody.Builder mFormBodyBuild = new FormBody.Builder();
                for (Map.Entry<String, String> entry : params.getParams().entrySet()) {
                    mFormBodyBuild.add(entry.getKey(), entry.getValue());
                }
                FormBody mFormBody = mFormBodyBuild.build();
                request = new Request.Builder().url(url).
                        post(mFormBody).
                        headers(mHeader)
                        .build();
            }
        }
        sendRequest(callback, request);
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

    private void sendRequest(Callback callback, Request request) {
        if (okHttpClient != null) {
            OkHttpCallback httpCallback = new OkHttpCallback(callback);
            httpCallback.onStart();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    CommonHttpClient.getInstance().getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            httpCallback.onFailure(call, e);
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    CommonHttpClient.getInstance().getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                httpCallback.onResponse(call, response);
                            } catch (IOException e) {
                                httpCallback.onFailure(call, e);
                            }

                        }
                    });
                }
            });
        } else {
            throw new IllegalArgumentException("CommonHttpClient need to HttpClient!");
        }
    }
}
