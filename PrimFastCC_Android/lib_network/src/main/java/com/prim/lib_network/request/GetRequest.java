package com.prim.lib_network.request;

import com.prim.lib_network.header.HttpHeaders;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-09-05 - 23:17
 * @contact https://jakeprim.cn
 * @name PrimFastCC_Android
 */
public class GetRequest<T, R extends GetRequest> extends BaseRequest<T, R> {

    public GetRequest(CommonRequest request, String url) {
        this(request, url, Method.GET);
    }

    private GetRequest(CommonRequest request, String url, Method method) {
        super(request, url, method);
    }

    @Override
    public void enqueue() {
        request.generateRequest(request, this);
        request.get(realUrl, params, headers);
    }
}
