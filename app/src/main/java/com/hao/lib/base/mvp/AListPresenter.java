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

    private static final int PAGE_SIZE = 20;

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
    }

    public List<D> getDataList() {
        return mDataList;
    }

    public void setDataList(final List<D> list) {
        if (mView == null) {
            return;
        }
        if (mDataList.size() == 0 && (list == null || list.size() == 0)) {
            mView.noData();
        } else {
            setPage();
            int end = mDataList.size();
            mDataList.addAll(list);
            if (end == 0) {
                mView.updateList();
            } else {
                mView.notifyItemRangeInserted(end, list.size());
            }
            if (list.size() < PAGE_SIZE) {
                mView.noMoreData();
            }
        }
    }

    public void setObservable(Observable<List<D>> observable) {
        addRx2Destroy(new RxSubscriber<List<D>>(observable) {
            @Override
            protected void _onNext(List<D> ds) {
                setDataList(ds);
            }

            @Override
            protected void _onError(String code) {
                super._onError(code);
                if (mView != null) {
                    mView.loadError();
                }
            }
        });
    }

    protected void clear() {
        if (mView == null) {
            return;
        }
        mDataList.clear();
        mView.updateList();
        mView.noData();
    }

    protected String getPage() {
        if (mIsRefresh) {
            return "1";
        } else {
            return (mPage + 1) + "";
        }
    }

    protected void setPage() {
        if (mIsRefresh) {
            mDataList.clear();
            mPage = 1;
        } else {
            mPage++;
        }
    }

    public static String getPageSize() {
        return PAGE_SIZE + "";
    }

    public void onItemClick(View view, int position) {

    }

    public void onItemLongClick(View view, int position) {
    }
}
