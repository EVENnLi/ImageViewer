package com.example.imageviewer.base;

/**
 * @author csj
 * @date 2022/7/25
 */
public interface IBaseXPresenter {
    /**
     * 判断是否与view层还存在联系
     * @return 布尔值表示是否还有联系
     */
    boolean isViewAttach();

    /**
     * 解除P层和V层的绑定
     */
    void detachView();
}
