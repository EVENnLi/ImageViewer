package com.example.imageviewer.base;

import androidx.annotation.NonNull;

public abstract class BasePresenter <V extends IBaseView & IBaseXView,T,CONTACT> extends BaseXPresenter<V,CONTACT> implements IBasePresenter,HttpResponseListener<T>{
    public BasePresenter(@NonNull V view) {
        super(view);
    }

    @Override
    public void cancel(@NonNull Object tag) {

    }

    @Override
    public void cancelAll() {

    }

    @Override
    public void onSuccess(Object tag, T t) {

    }

    @Override
    public void onFailure(Object tag, String error) {

    }
}
