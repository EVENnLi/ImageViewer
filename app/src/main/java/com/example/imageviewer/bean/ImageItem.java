package com.example.imageviewer.bean;

import android.graphics.Bitmap;

/**
 * @author EvenLi
 */
public class ImageItem {
    /**
     * 图的数据
     */
    String uriString1;
    Bitmap bitmap1;
    String author1;


    public String getUriString1() {
        return uriString1;
    }

    public void setUriString1(String uriString1) {
        this.uriString1 = uriString1;
    }

    public Bitmap getBitmap1() {
        return bitmap1;
    }

    public void setBitmap1(Bitmap bitmap1) {
        this.bitmap1 = bitmap1;
    }

    public String getAuthor1() {
        return author1;
    }

    public void setAuthor1(String author1) {
        this.author1 = author1;
    }

}
