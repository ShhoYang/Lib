package com.yl.library.base.mvp;

/**
 * @author Yang Shihao
 */
public interface IListView extends IView {

    void finishRefresh();

    void updateList();

    void noMoreData();

    void noData();

    void loadError();

    void changeItem(int position);

    void removeItem(int position);
}
