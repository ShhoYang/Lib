package com.hao.lib.base.proxy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hao.lib.R;
import com.hao.lib.adapter.CommonViewPagerAdapter;
import com.hao.lib.adapter.FragmentWithTitleAdapter;
import com.hao.lib.utils.DisplayUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Yang Shihao
 */
public class UIProxy {

    private Activity mActivity;
    private Fragment mFragment;
    private ProgressDialog mDialog;
    private LinearLayout mLlEmpty;
    private TextView mTvEmpty;

    private String mNoDataText = "暂无数据";
    private String mLoadErrorText = "加载失败";

    private View.OnClickListener mEmptyViewClickListener;

    public UIProxy(@NonNull Activity activity) {
        mActivity = activity;
    }

    public UIProxy(@NonNull Fragment fragment) {
        mFragment = fragment;
        mActivity = mFragment.getActivity();
    }

    public void setNoDataText(String noDataText) {
        mNoDataText = noDataText;
    }

    public void setLoadErrorText(String loadErrorText) {
        mLoadErrorText = loadErrorText;
    }

    private void createEmptyView() {
        mLlEmpty = (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.empty_view,
                mRecyclerView, false);
        mTvEmpty = mLlEmpty.findViewById(R.id.base_tv_empty);
        mTvEmpty.setOnClickListener(mEmptyViewClickListener);
        mLlEmpty.findViewById(R.id.base_iv_empty).setOnClickListener(mEmptyViewClickListener);
    }

    public void setEmptyViewClickListener(View.OnClickListener emptyViewClickListener) {
        mEmptyViewClickListener = emptyViewClickListener;
    }

    //--------------------------------------------------------------------------------------------------

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private EmptyWrapper mAdapter;
    private MultiItemTypeAdapter mMultiItemTypeAdapter;

    public void setView(@NonNull MultiItemTypeAdapter adapter,
                        @NonNull SmartRefreshLayout refreshLayout,
                        @NonNull RecyclerView recyclerView) {
        mMultiItemTypeAdapter = adapter;
        mRefreshLayout = refreshLayout;
        mRecyclerView = recyclerView;

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new EmptyWrapper(mMultiItemTypeAdapter);
        if (mLlEmpty == null) {
            createEmptyView();
        }
        // mAdapter.setEmptyView(mLlEmpty);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 是否可以下拉刷新,默认true
     */
    public void setRefreshEnabled(boolean enable) {
        mRefreshLayout.setEnableRefresh(enable);
    }

    /**
     * 是否可以上拉加载,默认true
     */
    public void setLoadMoreEnabled(boolean enable) {
        mRefreshLayout.setEnableLoadMore(enable);
    }

    /**
     * 设置LayoutManager,默认处垂直单列
     */
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    /**
     * 设置默认分割线
     */
    public void setDefaultItemDecoration() {
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
    }

    /**
     * 设置自定义分割线
     */
    public void setItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    /**
     * 设置背景色
     */
    public void setBackgroundColor(int color) {
        mRecyclerView.setBackgroundColor(color);
    }

    /**
     * 设置Header和Footer的颜色
     */
    public void setRefreshThemeColor(@ColorRes int colorId) {
        mRefreshLayout.setPrimaryColorsId(colorId);
    }

    /**
     * 设置Margin
     */
    public void setMargin(int dp) {
        setMargin(dp, dp, dp, dp);
    }

    /**
     * 设置Margin
     */
    public void setMargin(int left, int top, int right, int bottom) {
        int pxL = DisplayUtils.dip2px(mActivity, left);
        int pxT = DisplayUtils.dip2px(mActivity, top);
        int pxR = DisplayUtils.dip2px(mActivity, right);
        int pxB = DisplayUtils.dip2px(mActivity, bottom);
        ViewGroup.LayoutParams params = mRecyclerView.getLayoutParams();
        if (params instanceof SmartRefreshLayout.LayoutParams) {
            ((SmartRefreshLayout.LayoutParams) params).setMargins(pxL, pxT, pxR, pxB);
        } else if (params instanceof LinearLayout.LayoutParams) {
            ((LinearLayout.LayoutParams) params).setMargins(pxL, pxT, pxR, pxB);
        } else if (params instanceof RelativeLayout.LayoutParams) {
            ((RelativeLayout.LayoutParams) params).setMargins(pxL, pxT, pxR, pxB);
        } else if (params instanceof NestedScrollView.LayoutParams) {
            ((NestedScrollView.LayoutParams) params).setMargins(pxL, pxT, pxR, pxB);
        }
        mRecyclerView.setLayoutParams(params);
    }

    /**
     * 结束刷新
     */
    public void finishRefresh() {
        mRefreshLayout.finishRefresh(0);
    }

    /**
     * 更新列表
     */
    public void updateList() {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
        mAdapter.notifyDataSetChanged();
    }

    public void noMoreData() {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMoreWithNoMoreData();
    }

    public void noData() {
        mTvEmpty.setText(mNoDataText);
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
    }

    public void loadError() {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
        mTvEmpty.setText(mLoadErrorText);
    }

    public void notifyItemRangeInserted(int positionStart, int itemCount) {
        if (mAdapter != null) {
            mAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }
    }

    public void notifyItemChanged(int position) {
        if (mAdapter != null) {
            mAdapter.notifyItemChanged(position);
        }
    }

    public void notifyItemChanged(int position, Object payload) {
        if (mAdapter != null) {
            mAdapter.notifyItemChanged(position, payload);
        }
    }

    public void notifyItemRemoved(int position) {
        if (mAdapter != null) {
            mAdapter.notifyItemRemoved(position);
        }
    }

//--------------------------------------------------------------------------------------------------

    private TabLayout mTabLayout;

    private ViewPager mViewPager;

    private CommonViewPagerAdapter mEmptyAdapter;

    public void setView(@NonNull TabLayout tabLayout,
                        @NonNull ViewPager viewPager) {
        mTabLayout = tabLayout;
        mViewPager = viewPager;
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
            loadEmptyAdapter();
            return;
        }
        int fragmentSize = fragments.size();
        int titleSize = titles.size();
        if (fragmentSize == 0 || titleSize == 0 || fragmentSize != titleSize) {
            loadEmptyAdapter();
            return;
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
        mViewPager.setAdapter(new FragmentWithTitleAdapter(getFragmentManager(), titles, fragments));
    }

    private void loadEmptyAdapter() {
        if (mEmptyAdapter == null) {
            if (mLlEmpty == null) {
                createEmptyView();
            }
            List<View> list = new ArrayList<>();
            list.add(mLlEmpty);
            mEmptyAdapter = new CommonViewPagerAdapter(list);
        }
        mTabLayout.setVisibility(View.GONE);
        mViewPager.setAdapter(mEmptyAdapter);
    }

    private FragmentManager getFragmentManager() {
        if (mFragment == null) {
            return ((FragmentActivity) mActivity).getSupportFragmentManager();
        }

        return mFragment.getChildFragmentManager();
    }
}
