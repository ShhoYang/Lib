package com.yl.library.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * @author Yang Shihao
 *         <p>
 *         Fragment和TabLayout配合时
 */

public class FragmentWithTitleAdapter extends FragmentPagerAdapter {

    private List<String> mTitles;
    private List<Fragment> mFragments;

    public FragmentWithTitleAdapter(FragmentManager fm, List<String> titles, List<Fragment> fragments) {
        super(fm);
        mTitles = titles;
        mFragments = fragments;

    }

    public FragmentWithTitleAdapter(FragmentManager fm, String[] titles, Fragment[] fragments) {
        super(fm);
        mTitles = Arrays.asList(titles);
        mFragments = Arrays.asList(fragments);

    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
