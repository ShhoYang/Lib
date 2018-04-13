package com.hao.lib.base.proxy;

import android.app.Activity;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.hao.lib.adapter.FragmentWithTitleAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * @author Yang Shihao
 */
public class ViewPagerUIProxy extends UIProxy {

    private TabLayout mTabLayout;

    private ViewPager mViewPager;

    private LinearLayout mLlEmpty;

    public ViewPagerUIProxy(@NonNull Activity activity) {
        super(activity);
    }

    public ViewPagerUIProxy(@NonNull Fragment fragment) {
        super(fragment);
    }

    public void setView(@NonNull TabLayout tabLayout,
                        @NonNull ViewPager viewPager,
                        @NonNull LinearLayout llEmpty) {
        mTabLayout = tabLayout;
        mViewPager = viewPager;
        mLlEmpty = llEmpty;
        mTabLayout.setupWithViewPager(mViewPager);
    }

    public void setOffscreenPageLimit(int limit) {
        mViewPager.setOffscreenPageLimit(limit);
    }

    public void setTabLayoutBackground(@ColorInt int color) {
        mTabLayout.setBackgroundColor(color);
    }

    public void setViewPagerData(String[] titles, Fragment[] fragments) {
        if (titles == null || fragments == null) {
            return;
        }
        setViewPagerData(Arrays.asList(titles), Arrays.asList(fragments));
    }

    public void setViewPagerData(List<String> titles, List<Fragment> fragments) {
        if (titles == null || fragments == null) {
            return;
        }
        int fragmentSize = fragments.size();
        int titleSize = titles.size();
        if (fragmentSize == 0 || titleSize == 0 || fragmentSize != titleSize) {
            return;
        }

        if (mLlEmpty != null) {
            mLlEmpty.setVisibility(View.GONE);
        }

        mViewPager.setAdapter(new FragmentWithTitleAdapter(getFragmentManager(), titles, fragments));
        if (fragmentSize == 1) {
            mTabLayout.setVisibility(View.GONE);
        } else if (fragmentSize < 5) {
            mTabLayout.setVisibility(View.VISIBLE);
            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            mTabLayout.setVisibility(View.VISIBLE);
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }

    private FragmentManager getFragmentManager() {
        if (mFragment == null) {
            return ((FragmentActivity) mActivity).getSupportFragmentManager();
        }

        return mFragment.getChildFragmentManager();
    }
}
