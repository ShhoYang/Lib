package com.hao.lib.mvp.ui.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.hao.lib.R;
import com.hao.lib.base.activity.BaseActivity;

import butterknife.BindView;

public class DetailsActivity extends BaseActivity {

    @BindView(R.id.base_rl_title)
    RelativeLayout mBaseRlTitle;
    @BindView(R.id.ctl)
    CollapsingToolbarLayout mCtl;
    @BindView(R.id.abl)
    AppBarLayout mAbl;
    @BindView(R.id.wv_content)
    WebView mWvContent;

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
    protected void initView() {
        setTitle("测试");
        mAbl.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (mAbl.getBottom() > mBaseRlTitle.getBottom()) {
                    mBaseRlTitle.setBackgroundColor(getResources().getColor(R.color.shadow));
                } else {
                    mBaseRlTitle.setBackgroundResource(R.color.colorPrimary);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }
}
