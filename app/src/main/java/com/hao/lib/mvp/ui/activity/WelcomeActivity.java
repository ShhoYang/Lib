package com.hao.lib.mvp.ui.activity;

import android.os.Handler;

import com.hao.lib.R;
import com.hao.lib.base.activity.BaseActivity;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected boolean fullScreen() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivityAndFinish(MainActivity.class);
            }
        }, 3000);
    }
}
