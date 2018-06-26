package com.hao.lib.base.mvp;

/**
 * @author Yang Shihao
 */
public interface IListView extends IView {

    void updateList();

    void finishRefresh();

    void finishLoadMore(boolean isNoMoreData);

    void noData();

    void refreshError();

    void loadMoreError();

    void insert(int positionStart, int itemCount);

    void changeItem(int position);

    void changeItem(int position, String payload);

    void removeItem(int position);
}
