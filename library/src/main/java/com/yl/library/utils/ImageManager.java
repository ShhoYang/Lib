package com.yl.library.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.yl.library.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * @author Yang Shihao
 *         <p>
 *         Glide图片管理类
 */

public class ImageManager {

    private RequestOptions requestOptions = new RequestOptions()
            .placeholder(R.mipmap.placeholder)
            .error(R.mipmap.placeholder)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

    private RequestOptions requestNoHolderOptions = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

    private RequestOptions requestCircleOptions = new RequestOptions()
            .placeholder(R.mipmap.placeholder)
            .error(R.mipmap.placeholder)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .transform(new CircleCrop());

    private RequestOptions requestCircleNoHolderOptions = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .transform(new CircleCrop());

    public static ImageManager getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 加载普通图片
     */
    public void loadImage(Context context, Object url, ImageView iv) {
        if (url == null) {
            Glide.with(context).load(R.mipmap.placeholder).apply(requestOptions).into(iv);
        } else {
            Glide.with(context).load(url).apply(requestOptions).into(iv);
        }
    }

    public void loadImage(Context context, Object url, int placeholder, ImageView iv) {
        if (url == null) {
            Glide.with(context).load(placeholder).into(iv);
        } else {
            RequestOptions options = new RequestOptions()
                    .placeholder(placeholder)
                    .error(placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
            Glide.with(context).load(url).apply(options).into(iv);
        }
    }

    public void loadImageNoHolder(Context context, Object url, ImageView iv) {
        if (url != null) {
            Glide.with(context).load(url).apply(requestNoHolderOptions).into(iv);
        }
    }


    /**
     * 加载圆形图片
     */
    public void loadCircleImage(Context context, Object url, ImageView iv) {
        if (url == null) {
            Glide.with(context).load(R.mipmap.placeholder).apply(requestCircleOptions).into(iv);
        } else {
            Glide.with(context).load(url).apply(requestCircleOptions).into(iv);
        }
    }

    public void loadCircleImage(Context context, Object url, int placeholder, ImageView iv) {
        if (url == null) {
            Glide.with(context).load(placeholder).apply(requestCircleOptions).into(iv);
        } else {
            RequestOptions options = new RequestOptions()
                    .placeholder(placeholder)
                    .error(placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .transform(new CircleCrop());
            Glide.with(context).load(url).apply(options).into(iv);
        }
    }

    public void loadCircleImageNoHolder(Context context, Object url, ImageView iv) {
        if (url != null) {
            Glide.with(context).load(url).apply(requestCircleNoHolderOptions).into(iv);
        }
    }

    /**
     * 加载圆角图片
     */
    public void loadRoundCornerImage(Context context, Object url, int roundingRadius, ImageView iv) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transform(new RoundedCorners(roundingRadius));
        Glide.with(context).load(url).apply(options).into(iv);
    }

    public void loadRatioImage(Context context, Object url, final ImageView iv) {
        Glide.with(context).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                iv.setImageBitmap(resource);
            }
        });
    }

    /**
     * 加载Bitmap
     */
    public void loadImage(Context context, Bitmap bitmap, ImageView imageView) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 30, baos);
        Glide.with(context).load(baos.toByteArray()).apply(requestOptions).into(imageView);
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
        if (baos != null) {
            try {
                baos.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 释放内存
     */
    public void clearMemory(Context context) {
        try {
            Glide.get(context).clearMemory();
        } catch (RuntimeException e) {

        }
    }

    /**
     * 清除磁盘缓存
     */
    public void clearDiskCache(final Context context) {
        try {
            Glide.get(context).clearDiskCache();
        } catch (RuntimeException e) {

        }
    }

    /**
     * 清除所有缓存
     */
    public void cleanAll(Context context) {
        clearDiskCache(context);
        clearMemory(context);
    }


    public static class Holder {
        public static final ImageManager INSTANCE = new ImageManager();
    }
}
