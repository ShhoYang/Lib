package com.hao.lib.base.ui;

import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.hao.lib.R;
import com.hao.lib.adapter.FragmentWithTitleAdapter;
import com.hao.lib.base.mvp.APresenter;
import com.hao.lib.view.EmptyView;

import java.util.Arrays;
import java.util.List;

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

    @Nullable
    @BindView(R.id.base_empty)
    EmptyView mEmptyView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_view_pager;
    }

    @Override
    public void initView() {
        mViewPager.addOnPageChangeListener(this);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void initData() {
        setViewPagerData(getTitles(), getFragments());
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

    public void setOffscreenPageLimit(int limit) {
        mViewPager.setOffscreenPageLimit(limit);
    }

    public void setTabLayoutBackground(@ColorInt int color) {
        mTabLayout.setBackgroundColor(color);
    }

    public void setEmptyViewClickListener(View.OnClickListener emptyViewClickListener) {
        if(mEmptyView!= null){
            mEmptyView.setOnClickListener(emptyViewClickListener);
        }
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

        if(mEmptyView!= null){
            mEmptyView.hide();
        }

        if (fragmentSize == 1) {
            if (mTabLayout.isShown()) {
                mTabLayout.setVisibility(View.GONE);
            }
        } else if (fragmentSize < 5) {
            if (!mTabLayout.isShown()) {
                mTabLayout.setVisibility(View.VISIBLE);
            }
            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            if (!mTabLayout.isShown()) {
                mTabLayout.setVisibility(View.VISIBLE);
            }
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
        mViewPager.setAdapter(new FragmentWithTitleAdapter(getChildFragmentManager(), titles, fragments));
    }

    /**
     * 抽象方法
     */
    public abstract String[] getTitles();

    public abstract Fragment[] getFragments();
}
