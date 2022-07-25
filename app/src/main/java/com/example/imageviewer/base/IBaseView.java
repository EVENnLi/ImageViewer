package com.example.imageviewer.base;

public interface IBaseView {
    /**
     * 显示加载动画，提升用户体验
     */
    void showLoading();

    /**
     * 加载完毕隐藏动画
     */
    void hideLoading();

    /**
     * Toast提示
     *
     * @param msg 显示的字符串
     */
    void showToast(String msg);

}
