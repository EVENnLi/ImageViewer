package com.example.imageviewer.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.imageviewer.bean.LoaderResult;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageLoader {
    private static final String TAG = "ImageLoader";
    public static final int MESSAGE_POST_RESULT = 1;
    private static final int TAG_KEY_URI = 2;
    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 50;
    private static final int DISK_CACHE_INDEX = 0;
    private boolean mIsDiskLruCacheCreated = false;
    private DiskLruCache mDiskLruCache;
    private final Handler mMainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            //之所以要封装成result对象，就是方便使用Handler来传递消息
            LoaderResult result = (LoaderResult) msg.obj;
            ImageView imageView = result.mImageView;
            imageView.setImageBitmap(result.mBitmap);
            String uri = (String) imageView.getTag(TAG_KEY_URI);
            if (uri.equals(result.uri)) {
                imageView.setImageBitmap(result.mBitmap);
            } else {
                Log.e(TAG, "set image bitmap,but url has changed,ignored !");
            }
        }
    };
    private final ImageResizer mImageResizer = new ImageResizer();
    private final LruCache<String, Bitmap> mMemoryCache;

    private ImageLoader(Context context) {
        Context context1 = context.getApplicationContext();
        //返回 Java 虚拟机将尝试使用的最大内存量。如果没有固有限制，则返回Long.MAX_VALUE值。
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
        File diskCacheDir = getDiskCacheDir(context1, "bitmap");
        if (!diskCacheDir.exists()) {
            //创建由此抽象路径名命名的目录，包括任何必要但不存在的父目录。请注意，如果此操作失败，它可能已成功创建一些必要的父目录。
            diskCacheDir.mkdirs();
        }
        if (diskCacheDir.getUsableSpace() > DISK_CACHE_SIZE) {
            try {
                mDiskLruCache = DiskLruCache.open(diskCacheDir, 1, 1, DISK_CACHE_SIZE);
                mIsDiskLruCacheCreated = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ImageLoader build(Context context) {
        return new ImageLoader(context);
    }

    /**
     * 把图片添加到内存缓存中
     *
     * @param key    MD5之后的key
     * @param bitmap 需要缓存的图片
     */
    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    private Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    /**
     * 异步加载,外部通过调用这个方法来实现图片的三级缓存，异步加载里面实际上还调用了同步加载
     */
    public void bindBitmap(final String uri, ImageView imageView, final int reqWidth, final int reqHeight) {
        //imageView.setTag(TAG_KEY_URI, uri);
        imageView.setTag(uri);
        Bitmap bitmap = loadBitmapFromMemCache(uri);
        Log.d(TAG, "bindBitmap: "+bitmap);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        Runnable loadBitmapTask = () -> {
            Bitmap bitmap1 = loadBitmap(uri, reqWidth, reqHeight);
            Log.d(TAG, "bindBitmap: "+bitmap);
            if (bitmap1 != null) {
                LoaderResult result = new LoaderResult(imageView, uri, bitmap1);
                mMainHandler.obtainMessage(MESSAGE_POST_RESULT, result).sendToTarget();
            }
        };
        ThreadPoolUtil.S_THREAD_POOL_EXECUTOR.execute(loadBitmapTask);
    }

    /**
     * 同步加载
     *
     * @param uri       所需要加载图片的URI
     * @param reqWidth  目标图片的宽
     * @param reqHeight 目标图片的高
     * @return 返回位图
     */
    public Bitmap loadBitmap(String uri, int reqWidth, int reqHeight) {
        Bitmap bitmap = loadBitmapFromMemCache(uri);
        if (bitmap != null) {
            Log.e(TAG, "loadBitmap+url:" + uri);
            return bitmap;
        }
        try {
            bitmap = loadBitmapFromDiskCache(uri, reqWidth, reqHeight);
            if (bitmap != null) {
                Log.e(TAG, "loadBitmap: url:" + uri);
                return bitmap;
            }
            bitmap = loadBitmapFromHttp(uri, reqWidth, reqHeight);
            Log.e(TAG, "loadBitmap: url:" + uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bitmap == null && !mIsDiskLruCacheCreated) {
            Log.e(TAG, "loadBitmap: encounter error,DiskLruCache id not created.");
            bitmap = AboutUrl.downloadBitmapFromUrl(uri);
        }
        return bitmap;
    }

    /**
     * 从内存读取
     *
     * @param url url
     * @return 图片
     */
    private Bitmap loadBitmapFromMemCache(String url) {
        String key = GetHashKey.hashKeyFromUrl(url);
        return getBitmapFromMemCache(key);
    }

    /**
     * 从网络加载图片
     *
     * @param url       url
     * @param reqWidth  图片的宽
     * @param reqHeight 图片的高
     * @return 返回图片
     * @throws IOException IOException
     */
    private Bitmap loadBitmapFromHttp(String url, int reqWidth, int
            reqHeight)
            throws IOException {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("can not visit network from UI Thread.");
        }
        if (mDiskLruCache == null) {
            return null;
        }
        String key = GetHashKey.hashKeyFromUrl(url);
        DiskLruCache.Editor editor = mDiskLruCache.edit(key);
        if (editor != null) {
            OutputStream outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
            if (AboutUrl.downloadUrlToStream(url, outputStream)) {
                editor.commit();
            } else {
                editor.abort();
            }
            mDiskLruCache.flush();
        }
        return loadBitmapFromDiskCache(url, reqWidth, reqHeight);
    }

    /**
     * 从磁盘读取
     *
     * @param url       url
     * @param reqWidth  图片显示的宽
     * @param reqHeight 图片显示的高
     * @return 返回从磁盘获取到的图片
     * @throws IOException 甩锅IO异常
     */
    private Bitmap loadBitmapFromDiskCache(String url, int reqWidth, int reqHeight) throws IOException {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Log.w(TAG, "load bitmap from UI Thread,it's not recommended!");
        }
        if (mDiskLruCache == null) {
            return null;
        }
        Bitmap bitmap = null;
        String key = GetHashKey.hashKeyFromUrl(url);
        DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
        if (snapShot != null) {
            FileInputStream fileInputStream = (FileInputStream) snapShot.
                    getInputStream(DISK_CACHE_INDEX);
            FileDescriptor fileDescriptor = fileInputStream.getFD();
            bitmap = mImageResizer.decodeSampleBitmapFromFileDescriptor(fileDescriptor, reqWidth, reqHeight);
            if (bitmap != null) {
                addBitmapToMemoryCache(key, bitmap);
            }
        }
        return bitmap;
    }

    /**
     * 获取文件的路径
     *
     * @param context    上下文环境
     * @param uniqueName 文件后缀名
     * @return file
     */
    public File getDiskCacheDir(Context context, String uniqueName) {
        boolean externalStorageAvailable = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        final String cachePath;
        if (externalStorageAvailable) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
}


