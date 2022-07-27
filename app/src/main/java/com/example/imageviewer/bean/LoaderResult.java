package com.example.imageviewer.bean;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class LoaderResult {
    public final ImageView mImageView;
    public final String uri;
    public final Bitmap mBitmap;

    public LoaderResult(ImageView imageView, String uri, Bitmap bitmap) {
        this.mImageView = imageView;
        this.uri = uri;
        this.mBitmap = bitmap;
    }
}
