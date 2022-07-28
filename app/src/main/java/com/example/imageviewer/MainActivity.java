package com.example.imageviewer;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.imageviewer.adapter.LoadMoreWrapper;
import com.example.imageviewer.adapter.MultiTypeRecyclerAdapter;
import com.example.imageviewer.base.BaseActivity;
import com.example.imageviewer.bean.ImageItem;
import com.example.imageviewer.listener.LoadingOnScrollListener;
import com.example.imageviewer.presenter.ImgPresenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ImageContact.ImagePtr> implements ImageContact.ImageUI {

    private List<ImageItem> dataList=new ArrayList<>();
    private LoadMoreWrapper wrapper;
    private MultiTypeRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * 获得数据成功后,把新数据放进数据表里，并通知加载成功
     * @param newData
     */
    @Override
    public void getMoreSucceed(List<ImageItem> newData) {
        dataList.addAll(newData);
        wrapper.setLoadState(LoadMoreWrapper.LOAD_COMPLETE);
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
              //通知适配器开始加载
              wrapper.setLoadState(LoadMoreWrapper.LOADING);
              //调用p层方法获得更多图片
              getPresenter().getMore();
          }
      });
    }

    @Override
    public void initData() {
        //进入应用先加载几张图

    }


    @Override
    public ImageContact.ImagePtr onBindPresenter() {
        return new ImgPresenter(this);
    }
}