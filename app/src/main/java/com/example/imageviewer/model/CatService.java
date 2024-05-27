package com.example.imageviewer.model;

import com.example.imageviewer.base.HttpResponseListener;
import com.example.imageviewer.bean.CatItem;
import com.example.imageviewer.bean.ImageItem;

import java.util.List;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface CatService {

//    @GET("/v1/images/search")
//    Call<List<CatItem>> getTenCat(@Query("limit") int limit,@Query("page") int page,
//            @Query("api_key")String apiKey) ;

    @GET("/v1/images/search")
    Observable<List<CatItem>> getTenCat(@Query("limit") int limit, @Query("page") int page,
                                        @Query("api_key")String apiKey) ;
}
