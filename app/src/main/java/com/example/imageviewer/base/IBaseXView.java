package com.example.imageviewer.base;

import android.app.Activity;

/**
 * @author csj
 * @date :2022/7/25
 * 这个是Activity和Fragment需要实现的基类接口
 */
public interface IBaseXView {
    /**
     * 用于在Presenter中需要使用Context对象时调用
     * 这样不直接在Presenter层创建Context对象，最大程度的防止内存泄露
     *
     * @param <V> 参数是Activity的子类
     * @return 返回对应业务的Activity
     */
    <V extends Activity> V getSelfActivity();
}
