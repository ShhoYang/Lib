package com.hao.lib.base.mvp;

import com.hao.lib.base.proxy.UIProxy;
import com.hao.lib.base.proxy.ViewPagerUIProxy;
import com.hao.lib.rx.Api;

/**
 * @author Yang Shihao
 */
public class AViewPagerPresenter<V> extends APresenter<V>{
    private static final String TAG = "AViewPagerPresenter";

    protected ViewPagerUIProxy mUIProxy;

    public AViewPagerPresenter(V view, Api api) {
        super(view, api);
    }


    @Override
    public void setUIProxy(UIProxy UIProxy) {
        mUIProxy= (ViewPagerUIProxy) UIProxy;
    }
}
