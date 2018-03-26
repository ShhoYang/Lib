package com.hao.lib.mvp.ui.activity;

import android.animation.ArgbEvaluator;
import android.support.v4.app.Fragment;

import com.hao.lib.Constant;
import com.hao.lib.R;
import com.hao.lib.base.activity.BaseViewPagerActivity;
import com.hao.lib.mvp.ui.fragment.MainFragment;
import com.hao.lib.widget.StatusBarUtils;

public class MainActivity extends BaseViewPagerActivity {


    @Override
    protected void setStatsBarColor() {
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        showBack(false);
        setTitle("新闻");
        setColor(new ArgbEvaluator(), 0, R.color.holo_blue_light, R.color.holo_green_light);
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels);

        int pos = position % 5;

        ArgbEvaluator evaluator = new ArgbEvaluator();
        if (pos == 0) {
            setColor(evaluator, positionOffset, R.color.holo_blue_light, R.color.holo_green_light);

        } else if (0 < pos && pos < 1) {
            setColor(evaluator, positionOffset, R.color.holo_green_light, R.color.holo_blue_light);

        } else if (pos == 1) {
            setColor(evaluator, positionOffset, R.color.holo_green_light, R.color.holo_purple);

        } else if (1 < pos && pos < 2) {
            setColor(evaluator, positionOffset, R.color.holo_purple, R.color.holo_green_light);

        } else if (pos == 2) {
            setColor(evaluator, positionOffset, R.color.holo_purple, R.color.holo_orange_light);

        } else if (2 < pos && pos < 3) {
            setColor(evaluator, positionOffset, R.color.holo_orange_light, R.color.holo_purple);

        } else if (pos == 3) {
            setColor(evaluator, positionOffset, R.color.holo_orange_light, R.color.holo_red_light);

        } else if (3 < pos && pos < 4) {
            setColor(evaluator, positionOffset, R.color.holo_red_light, R.color.holo_orange_light);

        } else if (pos == 4) {
            setColor(evaluator, positionOffset, R.color.holo_red_light, R.color.holo_blue_light);

        } else if (4 < pos && pos < 5) {
            setColor(evaluator, positionOffset, R.color.holo_blue_light, R.color.holo_red_light);
        }
    }

    public void setColor(ArgbEvaluator evaluator, float positionOffset, int color, int nextColor) {
        int evaluate = (Integer) evaluator.evaluate(positionOffset, getResources().getColor(color), getResources().getColor(nextColor));
        StatusBarUtils.setColor(this, evaluate);
        setTitleBackground(evaluate);
        setTabLayoutBackground(evaluate);
    }
}
