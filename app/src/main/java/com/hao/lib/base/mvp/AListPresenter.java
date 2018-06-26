package com.hao.lib.base.mvp;

import android.view.View;

import com.hao.lib.base.ui.IListView;
import com.hao.lib.rx.Api;
import com.hao.lib.rx.RxSubscriber;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author Yang Shihao
 */
public abstract class AListPresenter<V extends IListView, D> extends APresenter<V> {
    private static final String TAG = "AListPresenter";

    public static final int PAGE_SIZE = 20;

    protected List<D> mDataList = new ArrayList<>();
    protected int mPage = 1;
    protected boolean mIsRefresh = false;

    public AListPresenter(V view, Api api) {
        super(view, api);
    }

    @Override
    public void onDestroy() {
        mView = null;
        super.onDestroy();
    }

    public void getPageData(boolean isRefresh) {
        mIsRefresh = isRefresh;
        addRx2Destroy(new RxSubscriber<List<D>>(getDataSource()) {
            @Override
            protected void _onNext(List<D> ds) {
                loadSuccessful(ds);
            }

            @Override
            protected void _onError(String code) {
                super._onError(code);
                loadFailed();
            }
        });
    }

    public List<D> getDataList() {
        return mDataList;
    }

    /**
     * 获取数据成功
     */
    public void loadSuccessful(List<D> list) {
        if (mView == null) {
            return;
        }
        if (mIsRefresh) {
            refreshFinished(list);
        } else {
            loadMoreFinished(list);
        }
    }

    /**
     * 获取数据失败
     */
    public void loadFailed() {
        if (mView == null) {
            return;
        }
        if (mIsRefresh) {
            mView.refreshError();
        } else {
            mView.loadMoreError();
        }
    }

    /**
     * 刷新完成
     */
    private void refreshFinished(List<D> list) {
        if (mView == null) {
            return;
        }
        mPage = 1;
        int size = mDataList.size();
        if (size != 0) {
            mDataList.clear();
        }
        if (list == null || list.size() == 0) {
            if (size != 0) {
                mView.updateList();
            }
            mView.noData();
        } else {
            mDataList.addAll(list);
            mView.updateList();
            mView.finishRefresh();
        }
    }

    /**
     * 加载更多完成
     */
    private void loadMoreFinished(List<D> list) {
        if (mView == null) {
            return;
        }
        if (list == null || list.size() == 0) {
            mView.finishLoadMore(true);
        } else {
            int end = mDataList.size();
            mDataList.addAll(list);
            if (end == 0) {
                mView.updateList();
            } else {
                mView.insert(end, list.size());
            }
            mView.finishLoadMore(list.size() < PAGE_SIZE);
            mPage++;
        }
    }

    protected void clear() {
        if (mView == null) {
            return;
        }
        mDataList.clear();
        mView.updateList();
        mView.noData();
    }

    protected int getPage() {
        if (mIsRefresh) {
            return 1;
        } else {
            return mPage + 1;
        }
    }

    public void onItemClick(View view, int position) {

    }

    public void onItemLongClick(View view, int position) {
    }

    public abstract Observable<List<D>> getDataSource();
}
