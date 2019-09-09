package com.prim.lib_network.request;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.MediaType;

/**
 * @author prim
 * @version 1.0.0
 * @desc 请求参数的包装类
 * @time 2019-09-05 - 19:18
 * @contact https://jakeprim.cn
 * @name PrimFastCC_Android
 */
public class HttpParams {
    public static final MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");
    public static final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");

    /**
     * 普通参数
     */
    private ConcurrentHashMap<String, String> params;

    /**
     * 文件参数
     */
    private ConcurrentHashMap<String, List<FileWrapper>> fileParams;

    public HttpParams() {
        init();
    }

    private void init() {
        params = new ConcurrentHashMap<>();
        fileParams = new ConcurrentHashMap<>();
    }

    public HttpParams(String key, String value) {
        init();
        put(key, value);
    }

    public ConcurrentHashMap<String, String> getParams() {
        return params;
    }

    public ConcurrentHashMap<String, List<FileWrapper>> getFileParams() {
        return fileParams;
    }

    public void put(String key, String value) {
        if (key != null && value != null) {
            params.put(key, value);
        }
    }

    public void put(String key, File file) {
        if (key != null && file != null) {
            List<FileWrapper> fileWrapperList = fileParams.get(key);
            if (fileWrapperList == null) {
                fileWrapperList = new ArrayList<>();
                fileParams.put(key, fileWrapperList);
            }
            fileWrapperList.add(new FileWrapper(file, file.getName(), guessMimeType(file.getName())));
        }
    }

    public void put(HttpParams params) {
        if (params != null) {
            if (params.params != null && !params.params.isEmpty()) {
                this.params.putAll(params.params);
            }
            if (params.fileParams != null && !params.fileParams.isEmpty()) {
                fileParams.putAll(params.fileParams);
            }
        }
    }

    public void put(String key, FileWrapper wrapper) {
        if (key != null && wrapper != null) {
            put(key, wrapper.file);
        }
    }

    public void putFiles(String key, List<File> files) {
        if (key != null && !files.isEmpty()) {
            for (File file : files) {
                put(key, file);
            }
        }
    }

    public void putFileWrappers(String key, List<FileWrapper> files) {
        if (key != null && !files.isEmpty()) {
            for (FileWrapper file : files) {
                put(key, file);
            }
        }
    }

    public static MediaType guessMimeType(String fileName) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        fileName = fileName.replace("#", "");   //解决文件名中含有#号异常的问题
        String contentType = fileNameMap.getContentTypeFor(fileName);
        if (contentType == null) {
            return MEDIA_TYPE_STREAM;
        }
        return MediaType.parse(contentType);
    }

    public void remove(String key) {
        params.remove(key);
        fileParams.remove(key);
    }

    public void clear() {
        params.clear();
        fileParams.clear();
    }
}
