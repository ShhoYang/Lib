package com.hao.lib.mvp.presenter.fragment;

import android.os.Bundle;

import com.hao.lib.Constant;
import com.hao.lib.mvp.contract.fragment.MainContract;
import com.hao.lib.rx.Api;

/**
 * @author Yang Shihao
 */

public class MainPresenter extends MainContract.Presenter {

    private String mType;

    public MainPresenter(MainContract.View view, Api api) {
        super(view, api);
    }

    @Override
    public void initBundle() {
        super.initBundle();
        Bundle build = mView.getBundle();
        if (build != null) {
            mType = build.getString(Constant.EXTRA_STRING_1, "");
        }
    }

    @Override
    public void getPageData(boolean isRefresh) {
        super.getPageData(isRefresh);
        setObservable(mApi.getNews(mType));
    }
}
