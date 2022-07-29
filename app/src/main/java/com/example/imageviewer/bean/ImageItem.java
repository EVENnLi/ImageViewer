package com.example.imageviewer.bean;

import android.graphics.Bitmap;

/**
 * @author EvenLi
 */
public class ImageItem { @Override
    public String toString() {
        return "ImageItem{" +
                "author='" + author + '\'' +
                ", downLoad_Uri='" + download_url + '\'' +
                '}';
    }

    /**
     * 网络请求返回的数据
     */

    String author;

    String download_url;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDownLoadUri() {
        return download_url;
    }

    public void setDownLoadUri(String downLoadUri) {
        this.download_url = downLoadUri;
    }
}
