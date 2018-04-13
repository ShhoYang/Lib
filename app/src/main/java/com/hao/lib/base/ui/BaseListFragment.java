package com.hao.lib.base.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.hao.lib.R;
import com.hao.lib.base.mvp.AListPresenter;
import com.hao.lib.base.proxy.ListUIProxy;
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
public abstract class BaseListFragment<P extends AListPresenter> extends MyFragment<P,ListUIProxy>
        implements OnRefreshListener, OnLoadMoreListener, MultiItemTypeAdapter.OnItemClickListener {

    private static final String TAG = "BaseListFragment";

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
    public int getLayoutId() {
        return R.layout.activity_base_list;
    }

    @Override
    public void initView() {
        Log.d(TAG, "initView: "+ mUIProxy.toString());
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
