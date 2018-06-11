package com.hao.lib.mvp.presenter.fragment;

import android.os.Bundle;

import com.hao.lib.Constant;
import com.hao.lib.bean.News;
import com.hao.lib.mvp.contract.fragment.NewsContract;
import com.hao.lib.rx.Api;
import com.hao.lib.rx.RxSubscriber;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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
    public void getPageData(boolean isRefresh) {
        super.getPageData(isRefresh);
        //setObservable(mApi.getNews(mType));
        addRx2Destroy(new RxSubscriber<List<News>>(mApi.getNews(mType)) {

            @Override
            protected void _onNext(List<News> news) {
                setDataList(news);
            }

            @Override
            protected void _onError(String code) {
//               if(mView!= null){
//                   mView.loadError();
//               }
                List<News> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    list.add(new News("新闻" + i));
                }
                setDataList(list);
            }
        });
    }
}
