package com.prim.music_app;

import android.app.Application;

import com.prim.lib_network.CommonHttpClient;
import com.prim.lib_network.callback.CommonCallback;
import com.prim.lib_network.exception.HttpException;
import com.prim.lib_network.okhttp.UseOkHttpClient;
import com.prim.lib_network.okhttp.OkHttpRequest;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-09-06 - 11:22
 * @contact https://jakeprim.cn
 * @name PrimFastCC_Android
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CommonHttpClient.init(new UseOkHttpClient())
                .addRequest(new OkHttpRequest())
                .addCommonHeader("User-Agent", "Music")
                .addCommonParams("device", "huawei")
                .setConnectTimeout(30000)
                .setReadTimeout(30000)
                .setWriteTimeout(30000)
                .setBaseUrl("")
                .createClient();

        CommonHttpClient.get("")
                .addParams("", "")
                .addParams("", "")
                .addHeader("", "")
                .enqueue(new CommonCallback<String>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(String s) {

                    }

                    @Override
                    public void onError(HttpException exception) {

                    }
                });

    }
}
