package com.hao.lib.mvp.ui.activity;

import android.os.Handler;

import com.hao.lib.R;
import com.hao.lib.base.ui.BaseActivity;
import com.hao.lib.di.component.activity.DaggerActivityCommonComponent;
import com.hao.lib.di.module.activity.ActivityCommonModule;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected boolean fullScreen() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initInject() {
        DaggerActivityCommonComponent.builder()
                .activityCommonModule(new ActivityCommonModule(this))
                .build().inject(this);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mUIProxy.startActivityAndFinish(HomeActivity.class);
            }
        }, 1000);
    }
}
