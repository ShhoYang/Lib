package com.hao.lib.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.hao.lib.Constant;
import com.hao.lib.R;
import com.hao.lib.adapter.PreviewImageAdapter;
import com.hao.lib.base.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class PreviewImageActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.vp)
    ViewPager mViewPager;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;

    private int mTotalSize;
    private boolean mShowTitle = false;
    private List<String> mDescList;

    public static void start(Context context, List<String> images, List<String> desc, int showIndex) {
        Intent intent = new Intent(context, PreviewImageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(Constant.EXTRA_BEAN_1, (ArrayList<String>) images);
        bundle.putStringArrayList(Constant.EXTRA_BEAN_2, (ArrayList<String>) desc);
        bundle.putInt(Constant.EXTRA_INT_1, showIndex);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected boolean fullScreen() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_preview_image;
    }

    @Override
    public void initInject() {

    }

    @Override
    public void initView() {
        setTitleTextSize(14);
        setTitleBackgroundColor(ContextCompat.getColor(this, R.color.translucent));
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }

        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }

        List images = bundle.getStringArrayList(Constant.EXTRA_BEAN_1);
        if (images == null || images.size() == 0) {
            return;
        }
        mTotalSize = images.size();
        mDescList = bundle.getStringArrayList(Constant.EXTRA_BEAN_2);
        int index = bundle.getInt(Constant.EXTRA_INT_1, 0);
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
            setTitle(String.format("%d/%d", (index + 1), mTotalSize));
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setTitle(String.format("%d/%d", position + 1, mTotalSize));
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
