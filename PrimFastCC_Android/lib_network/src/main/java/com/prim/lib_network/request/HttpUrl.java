package com.prim.lib_network.request;

import android.text.TextUtils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author prim
 * @version 1.0.0
 * @desc 对URL的解析处理包装类
 * @time 2019-09-05 - 23:49
 * @contact https://jakeprim.cn
 * @name PrimFastCC_Android
 */
public class HttpUrl {
    private String host;
    private String file;
    private String protocol;
    private int port;

    public HttpUrl(String url) throws MalformedURLException {
        URL urls = new URL(url);
        host = urls.getHost();//host
        file = urls.getFile();// /query?.....
        file = TextUtils.isEmpty(file) ? "/" : file;
        protocol = urls.getProtocol();//http/https
        port = urls.getPort();//端口 如：80
        port = port == -1 ? urls.getDefaultPort() : port;
    }

    public String getHost() {
        return host;
    }

    public String getFile() {
        return file;
    }

    public String getProtocol() {
        return protocol;
    }

    public int getPort() {
        return port;
    }
}
