package com.prim.lib_network.okhttp;

import com.prim.lib_network.CommonHttpClient;
import com.prim.lib_network.exception.HttpException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.prim.lib_network.exception.HttpException.NETWORK_ERROR;

/**
 * @author prim
 * @version 1.0.0
 * @desc okhttp的回调
 * @time 2019-09-09 - 10:41
 * @contact https://jakeprim.cn
 * @name PrimFastCC_Android
 */
public class OkHttpCallback implements Callback {

    private com.prim.lib_network.callback.Callback mCallback;

    public OkHttpCallback(com.prim.lib_network.callback.Callback mCallback) {
        this.mCallback = mCallback;
    }

    public void onStart() {
        if (mCallback != null) {
            mCallback.onStart();
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        CommonHttpClient.getInstance().getHandler().post(new Runnable() {
            @Override
            public void run() {
                if (mCallback != null)
                    mCallback.onError(new HttpException(NETWORK_ERROR, e));
            }
        });

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        CommonHttpClient.getInstance().getHandler().post(new Runnable() {
            @Override
            public void run() {
                if (mCallback != null)
                    mCallback.onSuccess(response.body().toString());
            }
        });
    }
}
