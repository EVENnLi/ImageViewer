package com.example.imageviewer.bean;

import android.graphics.Bitmap;

/**
 * @author EvenLi
 */
public class ImageItem {
    /**
     * 第一张图的数据
     */
    String uriString1;
    Bitmap bitmap1;
    String author1;


    /**
     * 第二张图的数据
     */

    String uriString2;
    Bitmap bitmap2;
    String author2;

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

    public String getUriString2() {
        return uriString2;
    }

    public void setUriString2(String uriString2) {
        this.uriString2 = uriString2;
    }

    public Bitmap getBitmap2() {
        return bitmap2;
    }

    public void setBitmap2(Bitmap bitmap2) {
        this.bitmap2 = bitmap2;
    }

    public String getAuthor2() {
        return author2;
    }

    public void setAuthor2(String author2) {
        this.author2 = author2;
    }
}
