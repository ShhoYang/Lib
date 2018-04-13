package com.hao.lib.base.ui;

import android.support.annotation.LayoutRes;

/**
 * @author Yang Shihao
 * @date 2018/4/13
 */
public interface IView {

    @LayoutRes
    int getLayoutId();

    void initInject();

    void initView();

    void initData();
}
