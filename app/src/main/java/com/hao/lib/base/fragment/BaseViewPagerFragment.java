package com.hao.lib.base.fragment;

import android.annotation.TargetApi;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.hao.lib.R;
import com.hao.lib.adapter.FragmentWithTitleAdapter;
import com.hao.lib.base.mvp.APresenter;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * @author Yang Shihao
 */
public abstract class BaseViewPagerFragment<P extends APresenter> extends BaseFragment<P> {

    @BindView(R.id.base_tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.base_view_pager)
    ViewPager mViewPager;

    @Nullable
    @BindView(R.id.base_ll_empty)
    LinearLayout mLlEmpty;

    protected P mPresenter;

    @Override
    public void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
        super.onDestroyView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_pager;
    }

    @Override
    protected void initUI() {
        super.initUI();
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initData() {
        setViewPagerData(getTitles(), getFragments());
    }

    @Optional
    @OnClick({R.id.base_iv_empty, R.id.base_tv_empty})
    protected void onEmptyViewClicked() {

    }

    protected void setPageLimit(int limit) {
        mViewPager.setOffscreenPageLimit(limit);
    }

    public void setViewPagerData(String[] titles, Fragment[] fragments) {
        if (titles == null || fragments == null) {
            return;
        }
        setViewPagerData(Arrays.asList(titles), Arrays.asList(fragments));
    }

    @TargetApi(17)
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
        mViewPager.setAdapter(new FragmentWithTitleAdapter(getChildFragmentManager(), titles, fragments));
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

    protected abstract String[] getTitles();

    protected abstract Fragment[] getFragments();
}