package com.hao.lib.base.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.hao.lib.R;
import com.hao.lib.base.mvp.APresenter;

import butterknife.BindView;

/**
 * @author Yang Shihao
 */
public abstract class BaseViewPagerFragment<P extends APresenter> extends BaseFragment<P>
        implements IPagerView, ViewPager.OnPageChangeListener {

    @BindView(R.id.base_tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.base_view_pager)
    ViewPager mViewPager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_view_pager;
    }

    @Override
    public void initView() {
        mViewPager.addOnPageChangeListener(this);
        mUIProxy.setView(mTabLayout, mViewPager);
    }

    @Override
    public void initData() {
        mUIProxy.setViewPagerData(getTitles(), getFragments());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
