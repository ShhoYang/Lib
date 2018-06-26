package com.hao.lib.di.module.fragment;

import com.hao.lib.di.scope.FragmentScope;
import com.hao.lib.contract.fragment.NewsContract;
import com.hao.lib.presenter.fragment.NewsPresenter;
import com.hao.lib.rx.Api;

import dagger.Module;
import dagger.Provides;

/**
 * @author Yang Shihao
 */

@Module
public class NewsModule {

    final NewsContract.View mView;

    public NewsModule(NewsContract.View view) {
        mView = view;
    }

    @Provides
    @FragmentScope
    NewsContract.Presenter provideNewsPresenter(Api api) {
        return new NewsPresenter(mView, api);
    }
}
