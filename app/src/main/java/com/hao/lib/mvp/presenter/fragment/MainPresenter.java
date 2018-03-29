package com.hao.lib.mvp.presenter.fragment;

import android.os.Bundle;
import android.util.Log;

import com.hao.lib.Constant;
import com.hao.lib.mvp.contract.fragment.MainContract;
import com.hao.lib.rx.Api;
import com.socks.library.KLog;

/**
 * @author Yang Shihao
 */

public class MainPresenter extends MainContract.Presenter {

    private static final String TAG = "MainPresenter";

    private String mType;

    public MainPresenter(MainContract.View view, Api api) {
        super(view, api);
        KLog.d(TAG, "MainPresenter: " + api.hashCode());
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
