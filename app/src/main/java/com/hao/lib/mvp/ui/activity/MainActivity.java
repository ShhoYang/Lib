package com.hao.lib.mvp.ui.activity;

import android.support.v4.app.Fragment;

import com.hao.lib.Constant;
import com.hao.lib.base.activity.BaseViewPagerActivity;
import com.hao.lib.mvp.ui.fragment.MainFragment;
import com.hao.lib.widget.StatusBarUtils;

import java.util.Random;

public class MainActivity extends BaseViewPagerActivity {

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        StatusBarUtils.setTranslucent(this);
        showBack(false);
        setTitle("新闻");
    }

    @Override
    protected String[] getTitles() {
        return Constant.CHANNELS;
    }

    @Override
    protected Fragment[] getFragments() {
        int length = Constant.CHANNELS_KEY.length;
        Fragment[] fragment = new Fragment[length];
        for (int i = 0; i < length; i++) {
            fragment[i] = MainFragment.newInstance(Constant.CHANNELS_KEY[i]);
        }
        return fragment;
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        Random random = new Random();
        int color = 0xff000000 | random.nextInt(0xffffff);
        setTitleBackground(color);
        setTabLayoutBackground(color);
    }
}
