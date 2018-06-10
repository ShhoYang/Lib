package com.hao.lib.base.ui;

/**
 * @author Yang Shihao
 */
public interface IListView extends IView {

    void finishRefresh();

    void updateList();

    void noMoreData();

    void noData();

    void loadError();

    void notifyItemRangeInserted(int positionStart, int itemCount);

    void notifyItemChanged(int position);

    void notifyItemChanged(int position, Object payload);

    void notifyItemRemoved(int position);
}
