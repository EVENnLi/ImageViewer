package com.example.imageviewer;


import com.example.imageviewer.base.IBasePresenter;
import com.example.imageviewer.base.IBaseView;

/**
 * @author evenli
 */

public interface ImageContact {
    /**
     * view层接口
     */
    interface ImageUI extends IBaseView{

    }

    /**
     * presenter层接口
     */
    interface ImagePtr extends IBasePresenter{

    }

    /**
     * model层接口
     */
    interface ImageMdl{

    }
}
