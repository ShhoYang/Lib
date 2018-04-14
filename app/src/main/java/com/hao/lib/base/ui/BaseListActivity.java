package com.hao.lib.base.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hao.lib.R;
import com.hao.lib.base.mvp.AListPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author Yang Shihao
 */
public abstract class BaseListActivity<P extends AListPresenter> extends BaseActivity<P>
        implements OnRefreshListener, OnLoadMoreListener, MultiItemTypeAdapter.OnItemClickListener {

    @BindView(R.id.base_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.base_refresh_view)
    SmartRefreshLayout mRefreshLayout;

    @Inject
    MultiItemTypeAdapter mMultiItemTypeAdapter;

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
        mMultiItemTypeAdapter.setOnItemClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        mUIProxy.setView(mMultiItemTypeAdapter,mRefreshLayout, mRecyclerView);
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
    public void onLoadMore(RefreshLayout refreshLayout) {
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
