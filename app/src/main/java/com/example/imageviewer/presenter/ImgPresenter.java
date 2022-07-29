package com.example.imageviewer.presenter;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.imageviewer.ImageContact;
import com.example.imageviewer.base.BasePresenter;

import com.example.imageviewer.base.HttpResponseListener;
import com.example.imageviewer.bean.ImageItem;
import com.example.imageviewer.model.ImgModel;
import com.example.imageviewer.util.ThreadPoolUtil;


import java.util.List;

public class ImgPresenter extends BasePresenter<ImageContact.ImageUI,List<ImageItem>,ImageContact> implements ImageContact.ImagePtr, HttpResponseListener<List<ImageItem>> {

    private ImgModel mImgMdl;

    /**
     * 加载的图片的页数，每次加载完成自增一次
     */
    private int page;
    /**
     * 每次加载十张图片
     */
    private final int LIMIT=24;


    private Handler mHandler=new Handler(Looper.getMainLooper()){

    };
    public ImgPresenter(@NonNull ImageContact.ImageUI view) {
        super(view);
        page=1;
        //获取model实例
        mImgMdl=new ImgModel();
    }

    @Override
    public void getMore() {
        Log.d("TAG", "getMore: ");
        //获得用于发送网络请求的uri
        String uriString=null;
        //组装一下https://picsum.photos/v2/list?page=2&limit=100
        uriString="https://picsum.photos/v2/list?page="+page+"&limit="+LIMIT;
        page++;


        String finalUriString = uriString;
        Runnable networkTest=(()->{
            //调用m层方法
            mImgMdl.getMore(finalUriString,this);
        });

        ThreadPoolUtil.S_THREAD_POOL_EXECUTOR.execute(networkTest);

    }

    @Override
    public void onSuccess(Object tag, List<ImageItem> dataList) {
        //切到主线程更新数据
        Log.d("TAG", dataList.toString());
        mHandler.post(() -> getView().getMoreSucceed(dataList));
    }

    @Override
    public void onFailure(Object tag, String error) {
        //切到主线程显示加载失败
        mHandler.post(() -> getView().getMoreFailed());
    }
}
