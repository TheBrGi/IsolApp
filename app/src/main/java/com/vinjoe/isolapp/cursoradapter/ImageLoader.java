package com.vinjoe.isolapp.cursoradapter;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by BrGi on 12/06/2015.
 */
public class ImageLoader {

    MemoryCache memoryCache = new MemoryCache();
    private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    ExecutorService executorService;
    Handler handler = new Handler();//handler to display images in UI thread

    public ImageLoader() {
        executorService = Executors.newFixedThreadPool(5);
    }

    //final int stub_id=R.drawable.stub;
    public void DisplayImage(String url, ImageView imageView,Cursor cursor) {
        imageViews.put(imageView, url);
        Bitmap bitmap = memoryCache.get(url);
        if (bitmap != null)
            imageView.setImageBitmap(bitmap);
        else {
            queuePhoto(url, imageView,cursor);
            imageView.setImageBitmap(null);
        }
    }

    private Bitmap getBitmap(Cursor cursor) {
        byte[] blob = cursor.getBlob(cursor.getColumnIndex("Foto"));
        if (blob != null) {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(blob);
            Bitmap b = BitmapFactory.decodeStream(inputStream);
            return b;
        }
        return null;
    }

    private void queuePhoto(String url, ImageView imageView,Cursor cursor) {
        PhotoToLoad p = new PhotoToLoad(url, imageView);
        executorService.submit(new PhotosLoader(p,cursor));
    }

    //Task for the queue
    private class PhotoToLoad {
        public String url;
        public ImageView imageView;

        public PhotoToLoad(String u, ImageView i) {
            url = u;
            imageView = i;
        }
    }

    class PhotosLoader implements Runnable {
        PhotoToLoad photoToLoad;
        Cursor cursor;
        PhotosLoader(PhotoToLoad photoToLoad,Cursor cursor) {
            this.photoToLoad = photoToLoad;
        }

        @Override
        public void run() {
            try {
                if (imageViewReused(photoToLoad))
                    return;
                Bitmap bmp = getBitmap(cursor);
                memoryCache.put(photoToLoad.url, bmp);
                if (imageViewReused(photoToLoad))
                    return;
                BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
                handler.post(bd);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    boolean imageViewReused(PhotoToLoad photoToLoad) {
        String tag = imageViews.get(photoToLoad.imageView);
        if (tag == null || !tag.equals(photoToLoad.url))
            return true;
        return false;
    }

    //Used to display bitmap in the UI thread
    class BitmapDisplayer implements Runnable {
        Bitmap bitmap;
        PhotoToLoad photoToLoad;

        public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
            bitmap = b;
            photoToLoad = p;
        }

        public void run() {
            if (imageViewReused(photoToLoad))
                return;
            if (bitmap != null)
                photoToLoad.imageView.setImageBitmap(bitmap);
            else
                photoToLoad.imageView.setImageBitmap(null);
        }
    }

    public void clearCache() {
        memoryCache.clear();
    }

}