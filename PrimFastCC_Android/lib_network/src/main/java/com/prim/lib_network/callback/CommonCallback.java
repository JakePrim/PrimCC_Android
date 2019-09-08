package com.prim.lib_network.callback;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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
        Class<? extends T> geneticClass = getGeneticClass(this);
        T t = new Gson().fromJson(result, geneticClass);
        //重定向到新的success函数
        onSuccess(t);
    }

    protected Class<? extends T> getGeneticClass(Object object) {
        //获得带有泛型的直接父类的type   ModelCallback
        Type genericSuperclass = object.getClass().getGenericSuperclass();
        // ParameterizedType 带参数的类型 泛型
        //getActualTypeArguments 参数的类型 泛型类型
        return (Class<? extends T>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
    }

    public abstract void onSuccess(T t);
}
