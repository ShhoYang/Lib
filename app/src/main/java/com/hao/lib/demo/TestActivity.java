package com.hao.lib.demo;

import com.hao.lib.R;
import com.hao.lib.base.ui.BaseActivity;

public class TestActivity extends BaseActivity {

    private static final String TAG = "TestActivity";


    @Override
    protected boolean hasToolbar() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void initInject() {
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
