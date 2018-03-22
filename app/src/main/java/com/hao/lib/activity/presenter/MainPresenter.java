package com.hao.lib.activity.presenter;

import com.hao.lib.activity.contract.MainContract;
import com.hao.lib.rx.Api;

/**
 * @author Yang Shihao
 * @date 2018/3/16
 */

public class MainPresenter extends MainContract.Presenter {

    public MainPresenter(MainContract.View view, Api api) {
        super(view, api);
    }

    @Override
    public void getPageData(boolean isRefresh) {
        super.getPageData(isRefresh);
        setObservable(mApi.getNews("top"));
    }
}
