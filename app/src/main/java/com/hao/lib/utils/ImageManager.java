package com.hao.lib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.hao.lib.R;


/**
 * @author Yang Shihao
 * <p>
 * Glide图片管理类
 */

public class ImageManager {

    private RequestOptions requestOptions = new RequestOptions()
            //.placeholder(R.mipmap.placeholder)
            .error(R.mipmap.placeholder)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

    private RequestOptions requestCircleOptions = new RequestOptions()
            //.placeholder(R.mipmap.placeholder)
            .error(R.mipmap.placeholder)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .transform(new CircleCrop());

    public static ImageManager getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 加载普通图片
     */
    public void loadImage(Context context, Object url, ImageView iv) {
        loadImage(context, url, requestOptions, iv);
    }

    public void loadImage(Context context, Object url, int placeholder, ImageView iv) {
        RequestOptions options = new RequestOptions()
                // .placeholder(placeholder)
                .error(placeholder)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        loadImage(context, url, options, iv);
    }

    /**
     * 加载圆形图片
     */
    public void loadCircleImage(Context context, Object url, ImageView iv) {
        loadImage(context, url, requestCircleOptions, iv);
    }

    public void loadCircleImage(Context context, Object url, int placeholder, ImageView iv) {
        RequestOptions options = new RequestOptions()
               // .placeholder(placeholder)
                .error(placeholder)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transform(new CircleCrop());
        loadImage(context, url, options, iv);
    }

    /**
     * 加载圆角图片
     */
    public void loadRoundCornerImage(Context context, Object url, int roundingRadius, ImageView iv) {
        RequestOptions options = new RequestOptions()
               // .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transform(new RoundedCorners(roundingRadius));
        Glide.with(context).load(url).apply(options).into(iv);
    }

    public void loadRatioImage(Context context, Object url, final ImageView iv) {
        Glide.with(context).asBitmap().load(url).apply(requestOptions).listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                ViewGroup.LayoutParams params = iv.getLayoutParams();
                params.height = (int) (params.width * 1.0F * resource.getHeight() / resource.getWidth());
                iv.setLayoutParams(params);
                iv.setImageBitmap(resource);
                return false;
            }
        }).into(iv);
    }

    private void loadImage(Context context, Object url, RequestOptions options, ImageView iv) {
        Glide.with(context).asBitmap().load(url).transition(new BitmapTransitionOptions().crossFade(500)).apply(options).into(iv);
    }

    /**
     * 加载Bitmap
     */
    /*public void loadImage(Context context, Bitmap bitmap, ImageView imageView) {
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
    }*/

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
