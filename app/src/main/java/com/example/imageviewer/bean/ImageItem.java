package com.example.imageviewer.bean;

import android.graphics.Bitmap;

/**
 * @author EvenLi
 */
public class ImageItem {
    /**
     * 网络请求返回的数据
     */

    String author;

    String downLoadUri;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDownLoadUri() {
        return downLoadUri;
    }

    public void setDownLoadUri(String downLoadUri) {
        this.downLoadUri = downLoadUri;
    }
}
