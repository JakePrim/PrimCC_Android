package com.prim.lib_network.callback;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.prim.lib_network.exception.HttpException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static com.prim.lib_network.exception.HttpException.EMPTY_MSG;
import static com.prim.lib_network.exception.HttpException.JSON_E;

/**
 * @author prim
 * @version 1.0.0
 * @desc 通用的网络会调可以自定义回调
 * @time 2019-09-06 - 11:42
 * @contact https://jakeprim.cn
 * @name PrimFastCC_Android
 */
public abstract class CommonCallback<T> implements Callback {
    @Override
    public void onSuccess(String result) {
        if (TextUtils.isEmpty(result)) {
            onError(new HttpException(HttpException.NETWORK_ERROR, EMPTY_MSG));
            return;
        }
        try {
            Class<? extends T> geneticClass = getGeneticClass(this);
            T t = new Gson().fromJson(result, geneticClass);
            //重定向到新的success函数
            onSuccess(t);
        } catch (Exception e) {
            onError(new HttpException(HttpException.JSON_ERROR, JSON_E));
        }
    }

    private Class<? extends T> getGeneticClass(Object object) {
        //获得带有泛型的直接父类的type   ModelCallback
        Type genericSuperclass = object.getClass().getGenericSuperclass();
        // ParameterizedType 带参数的类型 泛型
        //getActualTypeArguments 参数的类型 泛型类型
        return (Class<? extends T>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
    }

    public abstract void onSuccess(T t);
}
