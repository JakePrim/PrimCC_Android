package com.prim.lib_network;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.prim.lib_network.header.HttpHeaders;
import com.prim.lib_network.https.HttpsUtil;
import com.prim.lib_network.request.GetRequest;
import com.prim.lib_network.request.HttpParams;
import com.prim.lib_network.request.HttpRequest;
import com.prim.lib_network.request.PostRequest;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

/**
 * @author prim
 * @version 1.0.0
 * @desc 通用的HttpClient支持对外扩展
 * @time 2019-09-05 - 18:53
 * @contact https://jakeprim.cn
 * @name PrimFastCC_Android
 */
public class CommonHttpClient {

    private HttpClient httpClient;

    private static CommonRequest commonRequest;

    private Context context;

    //可通用的参数
    private HttpParams params;

    //可通用的请求头
    private HttpHeaders headers;

    private long connectTimeout;

    private long readTimeout;

    private long writeTimeout;

    private String baseUrl;

    private static volatile CommonHttpClient instance;

    private Handler H;

    private SSLSocketFactory sslSocketFactory = HttpsUtil.initSSLSocketFactory();

    private X509TrustManager trustManager = HttpsUtil.initTrustManager();

    public CommonHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
        H = new Handler(Looper.getMainLooper());
    }

    public Handler getHandler() {
        return H;
    }

    //------------------------------------ 初始化操作 -------------------------------------//

    public static CommonHttpClient init(HttpClient httpClient) {
        if (instance == null) {
            synchronized (CommonHttpClient.class) {
                if (instance == null) {
                    instance = new CommonHttpClient(httpClient);
                }
            }
        }
        return instance;
    }

    public static CommonHttpClient getInstance() {
        if (instance == null)
            throw new IllegalArgumentException("Need init(HttpClient)");
        return instance;
    }

    public CommonHttpClient with(Context context) {
        this.context = context;
        return this;
    }

    public CommonHttpClient addRequest(HttpRequest request) {
        commonRequest = CommonRequest.initRequest(request);
        return this;
    }

    public CommonHttpClient addCommonParams(String key, String value) {
        params.put(key, value);
        return this;
    }

    public CommonHttpClient addCommonParams(HttpParams params) {
        params.put(params);
        return this;
    }

    public CommonHttpClient addCommonHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public CommonHttpClient addCommonHeader(HttpHeaders httpHeaders) {
        headers.put(httpHeaders);
        return this;
    }

    public CommonHttpClient setConnectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public CommonHttpClient setReadTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public CommonHttpClient setWriteTimeout(long writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    public CommonHttpClient setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public CommonHttpClient setSslSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
        return this;
    }

    public CommonHttpClient setTrustManager(X509TrustManager trustManager) {
        this.trustManager = trustManager;
        return this;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return sslSocketFactory;
    }

    public X509TrustManager getTrustManager() {
        return trustManager;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void createClient() {
        commonRequest.setHttpClient(this);
        httpClient.createClient(this, commonRequest);
    }

    public CommonRequest getRequset() {
        return commonRequest;
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

    public static GetRequest get(String url) {
        return commonRequest.get(url);
    }

    public static PostRequest post(String url) {
        return commonRequest.post(url);
    }
}
