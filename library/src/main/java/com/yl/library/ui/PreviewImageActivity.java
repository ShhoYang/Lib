package com.yl.library.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.yl.library.R;
import com.yl.library.adapter.PreviewImageAdapter;
import com.yl.library.base.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;


public class PreviewImageActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private static final String KEY_IMAGE = "KEY_IMAGE";
    private static final String KEY_DESC = "KEY_DESC";
    private static final String KEY_INDEX = "KEY_INDEX";

    private TextView mTvIndex;

    private ViewPager mViewPager;

    private TextView mTvDesc;

    private int mTotalSize;
    private boolean mShowTitle = false;
    private List<String> mDescList;


    /**
     * 打开大图
     *
     * @param context
     * @param images    图片集合
     * @param showIndex 要显示的Index
     */
    public static void start(Context context, List<Integer> images, int showIndex) {
        Intent intent = new Intent(context, PreviewImageActivity.class);
        intent.putIntegerArrayListExtra(KEY_IMAGE, (ArrayList<Integer>) images);
        intent.putExtra(KEY_INDEX, showIndex);
        context.startActivity(intent);
    }

    public static void start(Context context, List<String> images, List<String> desc, int showIndex) {
        Intent intent = new Intent(context, PreviewImageActivity.class);
        intent.putStringArrayListExtra(KEY_IMAGE, (ArrayList<String>) images);
        intent.putStringArrayListExtra(KEY_DESC, (ArrayList<String>) desc);
        intent.putExtra(KEY_INDEX, showIndex);
        context.startActivity(intent);
    }

    @Override
    protected boolean fullScreen() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_preview_image;
    }

    @Override
    protected void initMVP() {

    }

    @Override
    protected void initView() {
        mTvIndex = (TextView) $(R.id.tv_index);
        mViewPager = (ViewPager) $(R.id.vp);
        mTvDesc = (TextView) $(R.id.tv_desc);
        $(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }

        List images = intent.getStringArrayListExtra(KEY_IMAGE);
        if (images == null || images.size() == 0) {
            return;
        }
        mTotalSize = images.size();
        mDescList = intent.getStringArrayListExtra(KEY_DESC);
        int index = intent.getIntExtra(KEY_INDEX, 0);
        if (mDescList == null || mDescList.size() == 0) {
            mTvDesc.setVisibility(View.GONE);
        } else {
            mShowTitle = true;
            mTvDesc.setVisibility(View.VISIBLE);
            showImageDesc(index);
        }

        mViewPager.setAdapter(new PreviewImageAdapter(this, images));
        mViewPager.addOnPageChangeListener(this);
        if (index < mTotalSize) {
            mViewPager.setCurrentItem(index);
            mTvIndex.setText(String.format("%d/%d", (index + 1), mTotalSize));
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mTvIndex.setText(String.format("%d/%d", position + 1, mTotalSize));
        showImageDesc(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void showImageDesc(int position) {
        if (mShowTitle) {
            if (position < mDescList.size()) {
                mTvDesc.setVisibility(View.VISIBLE);
                mTvDesc.setText(mDescList.get(position));
            } else {
                mTvDesc.setVisibility(View.GONE);
            }
        }
    }
}
