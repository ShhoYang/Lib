package com.hao.lib.mvp.ui.activity;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.ImageView;

import com.hao.lib.Constant;
import com.hao.lib.R;
import com.hao.lib.base.activity.BaseActivity;
import com.hao.lib.bean.News;

import butterknife.BindView;

public class DetailsActivity extends BaseActivity {

    @BindView(R.id.iv_bg)
    ImageView mIvBg;

    @BindView(R.id.scroll)
    NestedScrollView mNestedScrollView;

    @BindView(R.id.web)
    WebView mWebView;

    private int mMaxOffset = 0;
    private float mFraction;
    private ArgbEvaluator mEvaluator = new ArgbEvaluator();

    @Override
    protected boolean hasToolbar() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_details;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void setStatsBarColor() {
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initView() {
        setTitleOffset();
        mWebView.setNestedScrollingEnabled(false);
        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                mMaxOffset = mIvBg.getBottom() - getTitleView().getBottom();
                if (mMaxOffset == 0) {
                    setTitleBackground(ContextCompat.getColor(mContext, R.color.colorPrimary));
                } else {
                    mFraction = scrollY * 1.0F / mMaxOffset;
                    if (mFraction > 1) {
                        mFraction = 1;
                    }
                }

                setTitleBackground((Integer) mEvaluator.evaluate(mFraction, Color.TRANSPARENT, ContextCompat.getColor(mContext, R.color.colorPrimary)));
            }
        });
    }


    @Override
    protected void initData() {
        Bundle bundle = getBundle();
        if(bundle!= null){
            News news = bundle.getParcelable(Constant.EXTRA_BEAN);
            if(news!= null){
                if(TextUtils.isEmpty(news.getTitle())){
                    setTitle("详情");
                }else{
                    setTitle(news.getTitle());
                }
            }
        }
        mWebView.loadUrl("http://mini.eastday.com/mobile/180331191333590.html");
    }
}
