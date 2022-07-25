package com.example.imageviewer.base;

import android.app.Activity;

public abstract class BaseXActivity<P extends IBaseXPresenter> extends Activity implements IBaseXView {
    private P mPresenter;

    /**
     * 创建Presenter
     *
     * @return 返回创建的P
     */
    public abstract P onBindPresenter();

    /**
     * 上面是创建，这里是获取Presenter对象，需要时才去创建Presenter，懒加载
     *
     * @return 获取到的P对象
     */
    public P getPresenter() {
        if (mPresenter == null) {
            mPresenter = onBindPresenter();
        }
        return mPresenter;
    }

    @Override
    public Activity getSelfActivity() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在生命周期结束时，将Presenter与View之间的联系断开，防止出现内存泄露
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
