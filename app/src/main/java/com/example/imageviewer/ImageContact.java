package com.example.imageviewer;


import com.example.imageviewer.base.HttpResponseListener;
import com.example.imageviewer.base.IBasePresenter;
import com.example.imageviewer.base.IBaseView;
import com.example.imageviewer.base.IBaseXView;
import com.example.imageviewer.bean.CatItem;
import com.example.imageviewer.bean.ImageItem;

import java.util.List;

import retrofit2.http.Query;

/**
 * @author evenli
 */

public interface ImageContact {
    /**
     * view层接口
     */
    interface ImageUI extends IBaseView, IBaseXView {

        //void getMoreSucceed(List<ImageItem> newData) throws InterruptedException;
        void getMoreSucceed(List<CatItem> newData) throws InterruptedException;

        void getMoreFailed();


    }

    /**
     * presenter层接口
     */
    interface ImagePtr extends IBasePresenter{

        /**
         * 获得更多图片，这个方法在v层重写的loadmore方法中使用
         */
      void getMore();

    }

    /**
     * model层接口
     */
    interface ImageMdl{

        /**
         * 网络请求解析后获得对应的数据，此方法在p层的getMore方法中使用，
         * @param uri 用于发送网络请求
         * @param callback 回调接口，根据请求成功/失败结果做出相应逻辑
         */
        void getMore(String uri, HttpResponseListener callback,int limit, int page,
                    String apiKey);
    }
}
