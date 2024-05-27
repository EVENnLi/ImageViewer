package com.example.imageviewer.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.imageviewer.ImageContact;
import com.example.imageviewer.base.HttpResponseListener;
import com.example.imageviewer.bean.CatItem;
import com.example.imageviewer.bean.ImageItem;
import com.example.imageviewer.network.OkhttpUtil;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import com.google.gson.JsonParser;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class ImgModel implements ImageContact.ImageMdl {


    @Override
    public void getMore(String uri, HttpResponseListener callback,int limit, int page,
                        String apiKey) {
        //发送网络请求
        Log.d("TAG", "getMore: model");
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(uri)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CatService service=retrofit.create(CatService.class);

//        retrofit2.Call<List<CatItem>> call=service.getTenCat(limit,page,apiKey);
//        call.enqueue(new retrofit2.Callback<List<CatItem>>() {
//            @Override
//            public void onResponse(retrofit2.Call<List<CatItem>> call, retrofit2.Response<List<CatItem>> response) {
//                List<CatItem> newdata=response.body();
//                callback.onSuccess(null,newdata);
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<List<CatItem>> call, Throwable t) {
//                callback.onFailure(null,t.getLocalizedMessage());
//            }
//        });
        service.getTenCat(limit, page, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<List<CatItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(null,e.toString());
                    }

                    @Override
                    public void onNext(List<CatItem> catItems) {
                        callback.onSuccess(null,catItems);
                    }
                });
    }

    void okhttpMethod(String uri, HttpResponseListener callback){
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
                        //small url组装

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
