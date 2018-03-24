package com.hao.lib.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.hao.lib.R;
import com.hao.lib.adapter.PreviewImageAdapter;
import com.hao.lib.base.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class PreviewImageActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private static final String EXTRA_IMAGE = "EXTRA_IMAGE";
    private static final String EXTRA_DESC = "EXTRA_DESC";
    private static final String EXTRA_INDEX = "EXTRA_INDEX";
    
    @BindView(R.id.vp)
    ViewPager mViewPager;
    @BindView(R.id.tv_index)
    TextView mTvIndex;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;

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
        intent.putIntegerArrayListExtra(EXTRA_IMAGE, (ArrayList<Integer>) images);
        intent.putExtra(EXTRA_INDEX, showIndex);
        context.startActivity(intent);
    }

    public static void start(Context context, List<String> images, List<String> desc, int showIndex) {
        Intent intent = new Intent(context, PreviewImageActivity.class);
        intent.putStringArrayListExtra(EXTRA_IMAGE, (ArrayList<String>) images);
        intent.putStringArrayListExtra(EXTRA_DESC, (ArrayList<String>) desc);
        intent.putExtra(EXTRA_INDEX, showIndex);
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
    protected void initInject() {

    }

    @Override
    protected void initView() {
        mTvIndex = (TextView) $(R.id.tv_index);
        mViewPager = (ViewPager) $(R.id.vp);
        mTvDesc = (TextView) $(R.id.tv_desc);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }

        List images = intent.getStringArrayListExtra(EXTRA_IMAGE);
        if (images == null || images.size() == 0) {
            return;
        }
        mTotalSize = images.size();
        mDescList = intent.getStringArrayListExtra(EXTRA_DESC);
        int index = intent.getIntExtra(EXTRA_INDEX, 0);
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
