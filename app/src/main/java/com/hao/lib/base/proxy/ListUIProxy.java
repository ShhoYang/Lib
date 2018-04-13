package com.hao.lib.base.proxy;

import android.app.Activity;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hao.lib.R;
import com.hao.lib.utils.DisplayUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;

/**
 * @author Yang Shihao
 */
public class ListUIProxy extends UIProxy {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private EmptyWrapper mAdapter;
    private MultiItemTypeAdapter mMultiItemTypeAdapter;
    private TextView mTvEmptyView;

    private String mNoDataText = "暂无数据";
    private String mLoadErrorText = "加载失败";

    public ListUIProxy(@NonNull Activity activity) {
        super(activity);
    }

    public ListUIProxy(@NonNull Fragment fragment) {
        super(fragment);
    }

    public void setView(@NonNull MultiItemTypeAdapter adapter,
                        @NonNull SmartRefreshLayout refreshLayout,
                        @NonNull RecyclerView recyclerView) {
        mMultiItemTypeAdapter = adapter;
        mRefreshLayout = refreshLayout;
        mRecyclerView = recyclerView;

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new EmptyWrapper(mMultiItemTypeAdapter);
        LinearLayout emptyView = (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.empty_view,
                mRecyclerView, false);
        mTvEmptyView = emptyView.findViewById(R.id.base_tv_empty);
        mAdapter.setEmptyView(emptyView);
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

    public void setNoDataText(String noDataText) {
        mNoDataText = noDataText;
    }

    public void setLoadErrorText(String loadErrorText) {
        mLoadErrorText = loadErrorText;
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
        mTvEmptyView.setText(mNoDataText);
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
    }

    public void loadError() {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
        mTvEmptyView.setText(mLoadErrorText);
    }

    public void changeItem(int position) {
        if (mAdapter != null) {
            mAdapter.notifyItemChanged(position);
        }
    }

    public void removeItem(int position) {
        if (mAdapter != null) {
            mAdapter.notifyItemRemoved(position);
        }
    }
}
