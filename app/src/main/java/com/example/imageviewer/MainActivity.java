package com.example.imageviewer;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;

import com.example.imageviewer.adapter.LoadMoreWrapper;
import com.example.imageviewer.adapter.MultiTypeRecyclerAdapter;
import com.example.imageviewer.base.BaseActivity;
import com.example.imageviewer.bean.CatItem;
import com.example.imageviewer.bean.ImageItem;
import com.example.imageviewer.listener.LoadingOnScrollListener;
import com.example.imageviewer.presenter.ImgPresenter;
import com.example.imageviewer.test.TestActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ImageContact.ImagePtr> implements ImageContact.ImageUI {

//    private List<ImageItem> dataList=new ArrayList<>();
    private List<CatItem> dataList=new ArrayList<>();



    private ImageContact.ImagePtr mPresenter;
    private LoadMoreWrapper wrapper;
    private MultiTypeRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter=getPresenter();
        initView();
        //initData();
        initListener();
        wrapper.setLoadState(LoadMoreWrapper.LOADING);

        mPresenter.getMore();

        adapter.setHasStableIds(true);
    }


    /**
     * 获得数据成功后,把新数据放进数据表里，并通知加载成功
     * @param newData
     */
    @Override
    public void getMoreSucceed(List<CatItem> newData)  {

        //更新数据
        dataList.addAll(newData);
       // wrapper.notifyItemRangeInserted(2,15);
        //通知数据更新
        wrapper.notifyItemRangeInserted(dataList.size()-12,dataList.size());
        wrapper.notifyItemRangeChanged(dataList.size()-12,dataList.size());
        System.out.println( wrapper.getItemCount());

    //    wrapper.notifyDataSetChanged();
        //更新状态为更新完毕
        wrapper.setLoadState(LoadMoreWrapper.LOAD_COMPLETE);



      //  Runnable task=(()->{


     //   });

      //  Handler handler=new Handler(Looper.getMainLooper());
     //   handler.postDelayed(task,1000);

    }


    /**
     * 获得数据失败，通知加载失败
     */
    @Override
    public void getMoreFailed() {
        wrapper.setLoadState(LoadMoreWrapper.LOAD_FAILED);
    }



    @Override
    public void initView() {
        recyclerView=findViewById(R.id.recycler_view);
        adapter=new MultiTypeRecyclerAdapter(dataList,this);
        adapter.setHasStableIds(true);
        wrapper=new LoadMoreWrapper(adapter);

        recyclerView.setAdapter(wrapper);

        GridLayoutManager manager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public void initListener() {
      recyclerView.addOnScrollListener(new LoadingOnScrollListener() {
          @Override
          public void loadMore() {
              Log.d("TAG", "loadMore: ");
              if(wrapper.getLoadState()!=LoadMoreWrapper.LOADING){
                  //通知适配器开始加载
                  wrapper.setLoadState(LoadMoreWrapper.LOADING);
                  //调用p层方法获得更多图片
                  mPresenter.getMore();
              }

          }
      });
    }

    @Override
    public void initData() {
        //进入应用先加载几张图
        Log.d("TAG", "initData: ");

    }


    @Override
    public ImageContact.ImagePtr onBindPresenter() {
        return new ImgPresenter(this);
    }

    @Override
    public void showLoading() {

    }


    @Override
    public void hideLoading() {

    }
}