package com.example.imageviewer.util;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileDescriptor;

/**
 * @decription: 压缩图片
 */
public class ImageResizer {
    private static final String TAG = "ImageResizer";

    public Bitmap decodeSampleBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public Bitmap decodeSampleBitmapFromFileDescriptor(FileDescriptor fd, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        //如果需要的宽高的是0，采样率返回1
        if (reqWidth == 0 || reqHeight == 0) {
            return 1;
        }
        int height = options.outHeight;
        int width = options.outWidth;
        Log.e(TAG, "origin,w=" + width + "h=" + height);
        int inSampleSize = 1;
        //宽高应该同时满足目标宽高，所以使用或运算
        while (height / inSampleSize >= reqHeight || width / inSampleSize >= reqWidth) {
            inSampleSize *= 2;
        }
        Log.e(TAG, "InSampleSize: " + inSampleSize);
        return inSampleSize;
    }
}
