package com.example.imageviewer;


import com.example.imageviewer.base.IBasePresenter;
import com.example.imageviewer.base.IBaseView;
import com.example.imageviewer.bean.ImageItem;

import java.util.List;

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

        /**
         * 获得更多图片，这个方法在v层重写的loadmore方法中使用
         * @return 返回的是带有信息的imageItem的list
         */
        List<ImageItem> getMore();
    }

    /**
     * model层接口
     */
    interface ImageMdl{

        /**
         * 网络请求解析后获得对应的数据，此方法在p层的getMore方法中使用，
         * @param uri p层提供的用来发送网络请求的uri
         * @return 返回的是带有数据类的List
         */
        List<ImageItem> getMore(String uri);
    }
}
