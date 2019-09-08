package com.prim.lib_network.okhttp;

import com.prim.lib_network.CommonHttpClient;
import com.prim.lib_network.HttpClient;
import com.prim.lib_network.request.CommonRequest;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;

/**
 * @author prim
 * @version 1.0.0
 * @desc 默认使用okhttp 请求网络
 * @time 2019-09-05 - 18:52
 * @contact https://jakeprim.cn
 * @name PrimFastCC_Android
 */
public class UseOkHttpClient implements HttpClient {

    private OkHttpClient okHttpClient;

    @Override
    public void createClient(CommonHttpClient httpClient, CommonRequest request) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });
        builder.connectTimeout(httpClient.getConnectTimeout(), TimeUnit.MILLISECONDS);
        builder.readTimeout(httpClient.getReadTimeout(), TimeUnit.MILLISECONDS);
        builder.writeTimeout(httpClient.getWriteTimeout(), TimeUnit.MILLISECONDS);
        builder.followRedirects(true);
        okHttpClient = builder.build();
    }

    @Override
    public Object getClient() {
        return okHttpClient;
    }
}
