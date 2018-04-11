package com.hao.lib.base.fragment;

import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
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
import com.hao.lib.base.mvp.AListPresenter;
import com.hao.lib.utils.DisplayUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author Yang Shihao
 */
public abstract class BaseListFragment<P extends AListPresenter> extends BaseFragment<P>
        implements OnRefreshListener, OnLoadMoreListener, MultiItemTypeAdapter.OnItemClickListener {

    @BindView(R.id.base_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.base_refresh_view)
    SmartRefreshLayout mRefreshLayout;

    @Inject
    MultiItemTypeAdapter mMultiItemTypeAdapter;
    private TextView mTvEmptyView;
    private EmptyWrapper mAdapter;
    private boolean mIsRefresh = false;
    private String mNoDataText = "暂无数据";
    private String mLoadErrorText = "加载失败";

    @Override
    public void onResume() {
        super.onResume();
        if (mIsRefresh) {
            mRefreshLayout.autoRefresh();
            mIsRefresh = false;
        }
    }

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
        return R.layout.activity_base_list;
    }

    @Override
    protected void initUI() {
        super.initUI();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mMultiItemTypeAdapter.setOnItemClickListener(this);
        mAdapter = new EmptyWrapper(mMultiItemTypeAdapter);
        LinearLayout emptyView = (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.empty_view,
                mRecyclerView, false);
        mTvEmptyView = $(emptyView, R.id.base_tv_empty);
        mAdapter.setEmptyView(emptyView);
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
    }

    @Override
    protected void initData() {
        mRefreshLayout.autoRefresh();
    }

    /**
     * 是否可以下拉刷新,默认true
     */
    protected void setRefreshEnabled(boolean enable) {
        mRefreshLayout.setEnableRefresh(enable);
    }

    /**
     * 是否可以上拉加载,默认true
     */
    protected void setLoadMoreEnabled(boolean enable) {
        mRefreshLayout.setEnableLoadMore(enable);
    }

    /**
     * 设置LayoutManager,默认处垂直单列
     */
    protected void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    /**
     * 设置默认分割线
     */
    protected void setDefaultItemDecoration() {
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
    }

    /**
     * 设置自定义分割线
     */
    protected void setItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    /**
     * 设置背景色
     */
    protected void setBackgroundColor(int color) {
        mRecyclerView.setBackgroundColor(color);
    }

    /**
     * 设置Header和Footer的颜色
     */
    protected void setRefreshThemeColor(@ColorRes int colorId) {
        mRefreshLayout.setPrimaryColorsId(colorId);
    }

    /**
     * 设置Margin
     */
    protected void setMargin(int dp) {
        setMargin(dp, dp, dp, dp);
    }

    /**
     * 设置Margin
     */
    protected void setMargin(int left, int top, int right, int bottom) {
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

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        if (mPresenter != null) {
            mPresenter.getPageData(true);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (mPresenter != null) {
            mPresenter.getPageData(false);
        }
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        if (mPresenter != null) {
            mPresenter.onItemClick(view, position);
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        if (mPresenter != null) {
            mPresenter.onItemLongClick(view, position);
        }
        return false;
    }
}
