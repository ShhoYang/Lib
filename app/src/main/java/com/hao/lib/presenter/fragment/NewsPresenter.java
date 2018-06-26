package com.hao.lib.presenter.fragment;

import android.os.Bundle;

import com.hao.lib.Constant;
import com.hao.lib.bean.News;
import com.hao.lib.contract.fragment.NewsContract;
import com.hao.lib.rx.Api;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author Yang Shihao
 */

public class NewsPresenter extends NewsContract.Presenter {

    private static final String TAG = "NewsPresenter";

    private String mType;

    @Inject
    public NewsPresenter(NewsContract.View view, Api api) {
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
    public Observable<List<News>> getDataSource() {
        return mApi.getNews(mType);
    }
}
