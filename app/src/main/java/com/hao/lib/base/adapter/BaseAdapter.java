package com.hao.lib.base.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yang Shihao
 */
public abstract class BaseAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    public BaseAdapter(int layoutResId) {
        super(layoutResId);
    }

    public void setDataList(List<T> dataList) {
        mData = dataList == null ? new ArrayList<T>() : dataList;
    }
}
