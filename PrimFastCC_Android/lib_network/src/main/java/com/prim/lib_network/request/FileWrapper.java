package com.prim.lib_network.request;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import okhttp3.MediaType;

/**
 * @author prim
 * @version 1.0.0
 * @desc 上传文件的包装类
 * @time 2019-09-05 - 19:22
 * @contact https://jakeprim.cn
 * @name PrimFastCC_Android
 */
public class FileWrapper {
    public File file;                      //文件
    public long size;                      //文件大小
    public String fileName;                //文件名
    public transient MediaType mediaType;  //文件的类型

    public FileWrapper(File file, String fileName, MediaType mediaType) {
        this.file = file;
        this.size = file.length();
        this.fileName = fileName;
        this.mediaType = mediaType;
    }

    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();
        outputStream.writeObject(mediaType.toString());
    }

    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        mediaType = MediaType.parse(String.valueOf(inputStream.readObject()));
    }
}
