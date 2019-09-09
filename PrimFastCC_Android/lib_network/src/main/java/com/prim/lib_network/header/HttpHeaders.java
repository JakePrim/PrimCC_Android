package com.prim.lib_network.header;

import java.util.LinkedHashMap;

/**
 * @author prim
 * @version 1.0.0
 * @desc 请求头
 * @time 2019-09-05 - 22:58
 * @contact https://jakeprim.cn
 * @name PrimFastCC_Android
 */
public class HttpHeaders {
    public LinkedHashMap<String, String> commonHeaders;

    public HttpHeaders() {
        init();
    }

    public HttpHeaders(String key, String value) {
        init();
        commonHeaders.put(key, value);
    }

    public void put(String key, String value) {
        if (key != null && value != null) {
            commonHeaders.put(key, value);
        }
    }

    public void put(HttpHeaders httpHeaders) {
        if (httpHeaders != null) {
            if (httpHeaders.commonHeaders != null && !httpHeaders.commonHeaders.isEmpty()) {
                commonHeaders.putAll(httpHeaders.commonHeaders);
            }
        }
    }

    public void remove(String key) {
        commonHeaders.remove(key);
    }

    public void clear() {
        commonHeaders.clear();
    }

    private void init() {
        commonHeaders = new LinkedHashMap<>();
    }

    public LinkedHashMap<String, String> getHeaders() {
        return commonHeaders;
    }
}
