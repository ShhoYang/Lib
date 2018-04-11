package com.hao.lib.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hao.lib.utils.ImageManager;
import com.hao.lib.view.ZoomImageView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Yang Shihao
 */

public class PreviewImageAdapter extends PagerAdapter {

    private Context mContext;
    private List<String> mImageUrlList;
    private LinkedList<ZoomImageView> mImageViewCacheList = new LinkedList<>();

    public PreviewImageAdapter(Context context, List<String> imageUrlList) {
        mContext = context;
        mImageUrlList = imageUrlList;
    }

    @Override
    public int getCount() {
        return mImageUrlList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ZoomImageView imageView;
        if (mImageViewCacheList.size() > 0) {
            imageView = mImageViewCacheList.remove();
            imageView.reset();
        } else {
            imageView = new ZoomImageView(mContext);
            ViewGroup.LayoutParams imageParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(imageParams);
            imageView.setBackgroundColor(Color.BLACK);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        ImageManager.getInstance().loadImage(mContext, mImageUrlList.get(position), imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ZoomImageView imageView = (ZoomImageView) object;
        container.removeView(imageView);
        mImageViewCacheList.add(imageView);
    }

   /* @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        ZoomImageView imageView = (ZoomImageView) object;
        ImageManager.getInstance().loadImage(mContext, mImageUrlList.get(position), imageView);
    }*/
}
