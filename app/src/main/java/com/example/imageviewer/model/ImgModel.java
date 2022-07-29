package com.example.imageviewer.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.imageviewer.ImageContact;
import com.example.imageviewer.base.HttpResponseListener;
import com.example.imageviewer.bean.ImageItem;
import com.example.imageviewer.network.OkhttpUtil;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import com.google.gson.JsonParser;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ImgModel implements ImageContact.ImageMdl {

    @Override
    public void getMore(String uri, HttpResponseListener callback) {
        //发送网络请求

        Log.d("TAG", "getMore: model");

            OkhttpUtil.sendOkHttpRequest(uri, new Callback() {
                //分情况使用callback的方法
                //失败了就把错误信息传回去
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    callback.onFailure(null,e.getLocalizedMessage());
                }
                //成功了把数据传回去
                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                   // Log.d("TAG", "onResponse: "+response.body().string());
                    //解析
                    List<ImageItem> newdata=new ArrayList<>();
                    try{
                      //  JSONObject jsonObject=new JSONObject(response.body().string());
                      //  Log.d("TAG", "onResponse: "+jsonObject.toString());
                        JsonParser parser=new JsonParser();

                        JsonArray jsonArray=parser.parse(response.body().string()).getAsJsonArray();

                        Log.d("TAG", jsonArray.toString());

                        JsonElement jb;
                        Gson gson=new Gson();
                        for (int i = 0; i < jsonArray.size() ; i++) {
                            jb=jsonArray.get(i);
                            ImageItem item=gson.fromJson(jb,ImageItem.class);
                            Log.d("TAG", item.toString());

                            newdata.add(item);
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    callback.onSuccess(null,newdata);
                }
            });





    }
}
