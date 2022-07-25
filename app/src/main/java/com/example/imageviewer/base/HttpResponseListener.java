package com.example.imageviewer.base;

/**
 * @param <T> 网络请求后返回的数据
 * @author csj
 * 网络请求响应接口
 */
public interface HttpResponseListener<T> {
    /**
     * 网络请求成功
     *
     * @param tag 请求的标记
     * @param t   返回的数据
     */
    void onSuccess(Object tag, T t);

    /**
     * 网络请求失败
     *
     * @param tag   请求的标记
     * @param error 请求失败返回的错误信息
     */
    void onFailure(Object tag, String error);
}
