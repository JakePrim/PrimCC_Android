package com.prim.lib_network.request;

import com.prim.lib_network.callback.Callback;
import com.prim.lib_network.header.HttpHeaders;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-09-05 - 23:19
 * @contact https://jakeprim.cn
 * @name PrimFastCC_Android
 */
public class PostRequest<T, R extends PostRequest> extends BaseRequest<T, R> {
    public PostRequest(CommonRequest request, String url) {
        this(request, url, Method.POST);
    }

    PostRequest(CommonRequest request, String url, Method method) {
        super(request, url, method);
    }

    @Override
    public void enqueue(Callback callback) {
        request.generateRequest(request, this);
        request.post(realUrl, params, headers, callback);
    }
}
