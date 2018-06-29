package com.hao.lib.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hao.lib.ui.FragmentCreator;

import java.util.List;

/**
 * @author Yang Shihao
 * <p>
 * Fragment和TabLayout配合时
 */

public class FragmentWithTitleAdapter2 extends FragmentPagerAdapter {

    private static final String TAG = "FragmentWithTitleAdapter2";

    private List<String> mTitles;
    private List<FragmentCreator> mFragmentCreators;

    public FragmentWithTitleAdapter2(FragmentManager fm, List<String> titles, List<FragmentCreator> fragmentCreators) {
        super(fm);
        mTitles = titles;
        mFragmentCreators = fragmentCreators;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentCreators.get(position).createFragment();
    }

    @Override
    public int getCount() {
        return mFragmentCreators.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
