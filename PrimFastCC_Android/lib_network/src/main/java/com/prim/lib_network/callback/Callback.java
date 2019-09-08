package com.prim.lib_network.callback;

import com.prim.lib_network.exception.HttpException;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-09-06 - 11:35
 * @contact https://jakeprim.cn
 * @name PrimFastCC_Android
 */
public interface Callback {
    void onStart();

    void onSuccess(String result);

    void onError(HttpException exception);
}
