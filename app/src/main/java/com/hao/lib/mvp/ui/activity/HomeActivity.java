package com.hao.lib.mvp.ui.activity;

import android.animation.ArgbEvaluator;
import android.support.v4.app.Fragment;

import com.hao.lib.Constant;
import com.hao.lib.R;
import com.hao.lib.base.ui.BaseViewPagerActivity;
import com.hao.lib.di.component.activity.DaggerActivityCommonComponent;
import com.hao.lib.di.module.activity.ActivityCommonModule;
import com.hao.lib.mvp.ui.fragment.NewsFragment;

public class HomeActivity extends BaseViewPagerActivity {

    @Override
    protected boolean hasToolbar() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void setStatsBarColor() {

    }

    @Override
    public void initInject() {
        DaggerActivityCommonComponent.builder()
                .activityCommonModule(new ActivityCommonModule(this))
                .build().inject(this);
    }

    @Override
    public void initView() {
        super.initView();
        //setTitleOffset();
    }

    @Override
    public String[] getTitles() {
        return Constant.CHANNELS;
    }

    @Override
    public Fragment[] getFragments() {
        int length = Constant.CHANNELS_KEY.length;
        Fragment[] fragments = new Fragment[length];
        for (int i = 0; i < length; i++) {
            fragments[i] = NewsFragment.newInstance(Constant.CHANNELS_KEY[i]);
        }
        return fragments;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels);

        int pos = position % 5;

        if (pos == 0) {
            setColor(positionOffset, R.color.holo_blue_light, R.color.holo_green_light);

        } else if (0 < pos && pos < 1) {
            setColor(positionOffset, R.color.holo_green_light, R.color.holo_blue_light);

        } else if (pos == 1) {
            setColor(positionOffset, R.color.holo_green_light, R.color.holo_purple);

        } else if (1 < pos && pos < 2) {
            setColor(positionOffset, R.color.holo_purple, R.color.holo_green_light);

        } else if (pos == 2) {
            setColor(positionOffset, R.color.holo_purple, R.color.holo_orange_light);

        } else if (2 < pos && pos < 3) {
            setColor(positionOffset, R.color.holo_orange_light, R.color.holo_purple);

        } else if (pos == 3) {
            setColor(positionOffset, R.color.holo_orange_light, R.color.holo_red_light);

        } else if (3 < pos && pos < 4) {
            setColor(positionOffset, R.color.holo_red_light, R.color.holo_orange_light);

        } else if (pos == 4) {
            setColor(positionOffset, R.color.holo_red_light, R.color.holo_blue_light);

        } else if (4 < pos && pos < 5) {
            setColor(positionOffset, R.color.holo_blue_light, R.color.holo_red_light);
        }
    }

    private ArgbEvaluator mEvaluator = new ArgbEvaluator();

    public void setColor(float positionOffset, int color, int nextColor) {
        int evaluate = (Integer) mEvaluator.evaluate(positionOffset, getResources().getColor(color), getResources().getColor(nextColor));
        setTitleBackgroundColor(evaluate);
        setTabLayoutBackground(evaluate);
    }
}
