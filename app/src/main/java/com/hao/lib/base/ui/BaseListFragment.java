package com.hao.lib.base.ui;

import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hao.lib.R;
import com.hao.lib.base.mvp.AListPresenter;
import com.hao.lib.view.EmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;

/**
 * @author Yang Shihao
 */
public abstract class BaseListFragment<P extends AListPresenter> extends BaseFragment<P>
        implements OnRefreshListener, OnLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    private static final String TAG = "BaseListFragment";

    @BindView(R.id.base_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.base_refresh_view)
    SmartRefreshLayout mRefreshLayout;

    BaseQuickAdapter mAdapter;

    private EmptyView mEmptyView;

    private boolean mIsRefresh = false;

    @Override
    public void onResume() {
        super.onResume();
        if (mIsRefresh) {
            mRefreshLayout.autoRefresh();
            mIsRefresh = false;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_base_list;
    }

    @Override
    public void initView() {
        mAdapter = createAdapter();
        mEmptyView = new EmptyView(mActivity);
        mAdapter.setEmptyView(mEmptyView);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
    }

    @Override
    public void initData() {
        mRefreshLayout.autoRefresh();
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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (mPresenter != null) {
            mPresenter.onItemClick(view, position);
        }
    }

    public BaseQuickAdapter getAdapter() {
        return mAdapter;
    }

    private void setEmptyViewClickListener(View.OnClickListener emptyViewClickListener) {
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
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
        mEmptyView.noData();
    }

    public void loadError() {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
        mEmptyView.loadError();
    }

    public void notifyItemRangeInserted(int positionStart, int itemCount) {
        mAdapter.notifyItemRangeInserted(positionStart, itemCount);
    }

    public void notifyItemChanged(int position) {
        mAdapter.notifyItemChanged(position);
    }

    public void notifyItemChanged(int position, Object payload) {
        mAdapter.notifyItemChanged(position, payload);
    }

    public void notifyItemRemoved(int position) {
        mAdapter.notifyItemRemoved(position);
    }

    public abstract BaseQuickAdapter createAdapter();
}
