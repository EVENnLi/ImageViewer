package com.example.imageviewer.base;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;

/**
 * @author csj
 */
public class BaseXPresenter<V extends IBaseXView,CONTACT> extends SuperBase<CONTACT> implements IBaseXPresenter {
    private WeakReference<V> mViewRef;

    public BaseXPresenter(@NonNull V view) {
        attachView(view);
    }

    private void attachView(V view) {
        mViewRef = new WeakReference<>(view);
    }

    /**
     * 获取弱引用对象
     *
     * @return 返回获取到V的弱引用对象
     */
    public V getView() {
        return mViewRef.get();
    }

    /**
     * get()：返回此引用对象的所指对象。如果此引用对象已被程序或垃圾收集器清除，则此方法返回nul
     *
     * @return 是否绑定
     */
    @Override
    public boolean isViewAttach() {
        return mViewRef != null && mViewRef.get() != null;
    }

    /**
     * clear()：清除此引用对象。调用此方法不会导致此对象入队。此方法仅由 Java 代码调用；当垃圾收集器清除引用时，它会直接清除引用，而不调用此方法
     * 这里是手动清除，GC触发不会调用这个方法
     */
    @Override
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
        }
        mViewRef = null;
    }

    @Override
    public CONTACT getContact() {
        return null;
    }
}
