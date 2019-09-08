package com.prim.lib_network;

import com.prim.lib_network.request.CommonRequest;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-09-05 - 18:53
 * @contact https://jakeprim.cn
 * @name PrimFastCC_Android
 */
public interface HttpClient {
    void createClient(CommonHttpClient httpClient, CommonRequest request);

    Object getClient();
}
