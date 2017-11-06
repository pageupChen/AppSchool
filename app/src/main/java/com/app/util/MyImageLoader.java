package com.app.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.app.activity.MainActivity;
import com.app.activity.sayDynamicActivity;
import com.app.fragmentA.TabA1Fragment;
import com.bumptech.glide.Glide;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/10/15.
 */

public class MyImageLoader {

    private  LruCache<String, Bitmap> headerCache;
    private  LruCache<List<String>, List<Bitmap>> nineCache;
    private  LruCache<String, Bitmap> commentHeaderCache;


//    public static LruCache<String, Bitmap> getCommentHeaderCache() {
//        return commentHeaderCache;
//    }
//
//    public static void setCommentHeaderCache(LruCache<String, Bitmap> commentHeaderCache) {
//        MyImageLoader.commentHeaderCache = commentHeaderCache;
//    }


    public MyImageLoader() {
        int maxMemoryNine = (int) Runtime.getRuntime().maxMemory();
        int cacheSizeNine = maxMemoryNine / 4;
        nineCache = new LruCache<List<String>, List<Bitmap>>(cacheSizeNine) {
            @Override
            protected int sizeOf(List<String> key, List<Bitmap> value) {
                return value.size();
            }
        };

        int maxMemoryHeader = (int) Runtime.getRuntime().maxMemory();
        int cacheSizeHeader = maxMemoryHeader / 8;
        headerCache = new LruCache<String, Bitmap>(cacheSizeHeader) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public  void addBitmapToCacheCommentHeader(String url, Bitmap bitmap) {
        if (getBitmapFromCacheCommentHeader(url) == null) {
            commentHeaderCache.put(url, bitmap);
        }
    }

    public  Bitmap getBitmapFromCacheCommentHeader(String url) {
        return commentHeaderCache.get(url);
    }

    public  void addBitmapToCacheHeader(String url, Bitmap bitmap) {
        if (getBitmapFromCacheHeader(url) == null) {
            headerCache.put(url, bitmap);
        }
    }

    public  Bitmap getBitmapFromCacheHeader(String url) {
        return headerCache.get(url);
    }


    public  void addBitmapToCacheNine(List<String> url, List<Bitmap> bitmap) {
        if (getBitmapFromCacheNine(url) == null) {
            nineCache.put(url, bitmap);
        }

    }

    public  List<Bitmap> getBitmapFromCacheNine(List<String> url) {
        Log.d("getBimapNine", "true" + nineCache.size());

        return nineCache.get(url);
    }


}
