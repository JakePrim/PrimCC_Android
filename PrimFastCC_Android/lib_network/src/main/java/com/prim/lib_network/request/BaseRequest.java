package com.prim.lib_network.request;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.prim.lib_network.CommonRequest;
import com.prim.lib_network.callback.Callback;
import com.prim.lib_network.header.HttpHeaders;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-09-05 - 23:28
 * @contact https://jakeprim.cn
 * @name PrimFastCC_Android
 */
public abstract class BaseRequest<T, R extends BaseRequest> {

    protected CommonRequest request;

    protected String url;

    protected Method method;

    protected HttpParams params;

    protected HttpHeaders headers;

    protected String baseUrl;

    protected HttpUrl httpUrl;

    protected String realUrl;

    protected Handler H;

    public BaseRequest(CommonRequest request, String url, Method method) {
        this.request = request;
        this.url = url;
        this.method = method;
        H = new Handler(Looper.getMainLooper());
        if (request.getParams() != null) {
            this.params = request.getParams();
        } else {
            this.params = new HttpParams();
        }

        if (request.getHeaders() != null) {
            this.headers = request.getHeaders();
        } else {
            this.headers = new HttpHeaders();
        }

        this.baseUrl = request.getBaseUrl();

        try {
            httpUrl = new HttpUrl(url);
            if (baseUrl == null && url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
                baseUrl = httpUrl.getProtocol() + "://" + httpUrl.getHost() + "/";
                realUrl = url;
            } else if (baseUrl != null && url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
                baseUrl = httpUrl.getProtocol() + "://" + httpUrl.getHost() + "/";
                realUrl = url;
            } else {
                realUrl = baseUrl + url;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 支持动态修改base url
     *
     * @param url
     * @return
     */
    public R baseUrl(String url) {
        this.baseUrl = url;
        try {
            if (!TextUtils.isEmpty(url))
                httpUrl = new HttpUrl(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return (R) this;
    }

    public String getUrl() {
        return realUrl;
    }

    public R addParams(String key, String value) {
        params.put(key, value);
        return (R) this;
    }

    public R addParams(String key, File file) {
        params.put(key, file);
        return (R) this;
    }

    public R addParamsFiles(String key, List<File> file) {
        params.putFiles(key, file);
        return (R) this;
    }

    public R addParamsFileWrappers(String key, List<FileWrapper> fileWrappers) {
        params.putFileWrappers(key, fileWrappers);
        return (R) this;
    }

    public R addParams(HttpParams httpParams) {
        params.put(httpParams);
        return (R) this;
    }

    public R addHeader(String key, String value) {
        headers.put(key, value);
        return (R) this;
    }

    public Method getMethod() {
        return method;
    }

    public HttpParams getParams() {
        return params;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    //设置回调
    public abstract void enqueue(Callback callback);
}
