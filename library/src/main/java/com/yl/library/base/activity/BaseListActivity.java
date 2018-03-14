package com.yl.library.base.activity;

import android.support.annotation.ColorRes;
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

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yl.library.R;
import com.yl.library.base.mvp.AListPresenter;
import com.yl.library.utils.DisplayUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;

/**
 * @author Yang Shihao
 */
public abstract class BaseListActivity<P extends AListPresenter> extends BaseActivity
        implements OnRefreshListener, OnLoadmoreListener, MultiItemTypeAdapter.OnItemClickListener {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    protected P mPresenter;
    private LinearLayout mEmptyView;
    private TextView mTvEmptyView;
    private EmptyWrapper mAdapter;
    private MultiItemTypeAdapter mMultiItemTypeAdapter;
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
    protected void onStop() {
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
    protected int getLayoutId() {
        return R.layout.activity_base_list;
    }

    @Override
    protected void initUI() {
        super.initUI();
        if (mPresenter != null) {
            mPresenter.mContext = this;
        }
        mRefreshLayout = (SmartRefreshLayout) $(R.id.base_refresh_view);
        mRecyclerView = (RecyclerView) $(R.id.base_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMultiItemTypeAdapter = getAdapter();
        mMultiItemTypeAdapter.setOnItemClickListener(this);
        mAdapter = new EmptyWrapper(mMultiItemTypeAdapter);
        mEmptyView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.view_empty, mRecyclerView, false);
        mTvEmptyView = mEmptyView.findViewById(R.id.tv_empty);
        mAdapter.setEmptyView(mEmptyView);
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadmoreListener(this);
    }

    @Override
    protected void initData() {
        mRefreshLayout.autoRefresh();
    }

    protected abstract CommonAdapter getAdapter();

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
        mRefreshLayout.setEnableLoadmore(enable);
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
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
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
     * 如果需要被ScrollView嵌套，用NestedScrollView,然后调用该方法,可解决滑动冲突
     */
    protected void setNestedScrollingEnabled(boolean enable) {
        mRecyclerView.setNestedScrollingEnabled(enable);
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
        int pxL = DisplayUtils.dip2px(this, left);
        int pxT = DisplayUtils.dip2px(this, top);
        int pxR = DisplayUtils.dip2px(this, right);
        int pxB = DisplayUtils.dip2px(this, bottom);
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
        mRefreshLayout.finishRefresh(0);
        mRefreshLayout.finishLoadmore(0, true);
        mAdapter.notifyDataSetChanged();
    }

    public void noMoreData() {
        mRefreshLayout.finishRefresh(0);
        mRefreshLayout.finishLoadmore(0);
        mRefreshLayout.setLoadmoreFinished(true);
    }

    public void noData() {
        mTvEmptyView.setText(mNoDataText);
        mRefreshLayout.finishRefresh(0);
        mRefreshLayout.finishLoadmore(0);
    }

    public void loadError() {
        mRefreshLayout.finishRefresh(0);
        mRefreshLayout.finishLoadmore(0, false);
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
    public void onLoadmore(RefreshLayout refreshlayout) {
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
