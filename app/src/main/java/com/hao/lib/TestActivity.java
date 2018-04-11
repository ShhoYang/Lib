package com.hao.lib;

import com.hao.lib.base.activity.BaseActivity;
import com.hao.lib.mvp.ui.activity.PreviewImageActivity;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        List<Integer> integers = new ArrayList<>();
        integers.add(R.mipmap.motto);
        integers.add(R.mipmap.ic_launcher);
        integers.add(R.mipmap.motto);
        integers.add(R.mipmap.ic_launcher);
        integers.add(R.mipmap.motto);
        integers.add(R.mipmap.ic_launcher);
        integers.add(R.mipmap.motto);
        PreviewImageActivity.start(this, integers, 0);
    }
}
