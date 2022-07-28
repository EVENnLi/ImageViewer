package com.example.imageviewer.network;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @decription: 网络请求的工具类，在外部创建okhttp3.Callback就可以使用在onResponse方法里面解析网络请求到的数据
 */
public class OkhttpUtil {
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();

        try{
                Response response=client.newCall(request).execute();
                callback.onResponse(client.newCall(request),response );
        }catch (IOException e){
             callback.onFailure(client.newCall(request), e);
        }
    }

    /**
     * Add a private constructor to hide the implicit public one.
     * 当只有一个类里面只有静态方法时就报这个warning，意思是添加一个私有构造方法，这样就不能在外部创建对象，规范了代码，节省了内存
     */
    private OkhttpUtil() {
    }

}
