package com.hao.lib.base.ui;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.hao.lib.R;
import com.hao.lib.base.mvp.AViewPagerPresenter;
import com.hao.lib.base.proxy.ViewPagerUIProxy;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * @author Yang Shihao
 */
public abstract class BaseViewPagerActivity<P extends AViewPagerPresenter> extends MyActivity<P,ViewPagerUIProxy>
        implements IPagerView, ViewPager.OnPageChangeListener {

    @BindView(R.id.base_tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.base_view_pager)
    ViewPager mViewPager;

    @Nullable
    @BindView(R.id.base_ll_empty)
    LinearLayout mLlEmpty;

    @Override
    public void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
        super.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_view_pager;
    }

    @Override
    public void initView() {
        mViewPager.addOnPageChangeListener(this);
        mUIProxy.setView(mTabLayout, mViewPager, mLlEmpty);
    }

    @Override
    public void initData() {
        mUIProxy.setViewPagerData(getTitles(), getFragments());
    }

    @Optional
    @OnClick({R.id.base_iv_empty, R.id.base_tv_empty})
    protected void onEmptyViewClicked() {

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
