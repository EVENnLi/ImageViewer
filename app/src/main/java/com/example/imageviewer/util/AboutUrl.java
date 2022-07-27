package com.example.imageviewer.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @decription: 封装了关于url的两个方法
 */
public class AboutUrl {
    private static final int IO_BUFFER_SIZE = 8 * 1024;

    /**
     * 把url转化流
     *
     * @param urlString    想要转换的url
     * @param outputStream 把转化后的流写入其中
     * @return 返回是否写入成功
     */
    public static boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedInputStream inputStream = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            inputStream = new BufferedInputStream(urlConnection.getInputStream(), IO_BUFFER_SIZE);
            BufferedOutputStream out = new BufferedOutputStream(outputStream, IO_BUFFER_SIZE);
            int b;
            while ((b = inputStream.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    /**
     * 从网络下载图片
     */
    public static Bitmap downloadBitmapFromUrl(String urlString) {
        Bitmap bitmap = null;
        HttpURLConnection urlConnection = null;
        BufferedInputStream inputStream = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            //创建具有指定缓冲区大小的BufferedInputStream ，并将其参数（输入流）保存in ，以供以后使用。创建一个长度size的内部缓冲区数组并将其存储在buf中。
            //参数：in – 底层输入流,size - 缓冲区大小
            inputStream = new BufferedInputStream(urlConnection.getInputStream(), IO_BUFFER_SIZE);
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }


}
