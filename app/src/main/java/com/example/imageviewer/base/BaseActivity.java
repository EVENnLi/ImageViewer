package com.example.imageviewer.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

public abstract class BaseActivity<P extends IBaseXPresenter> extends BaseXActivity<P> implements IBaseView {
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public abstract void initView();
    public abstract void initListener();
    public abstract void initData();
/*
    @Override
    public void showLoading() {
        mProgressBar=new ProgressBar(this);
        mProgressBar.setVisibility(View.VISIBLE);
    }*/

  /*  @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }
*/
    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        hideLoading();
        super.onDestroy();
    }
}
