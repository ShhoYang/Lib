package com.hao.lib.di.module.fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hao.lib.R;
import com.hao.lib.adapter.NewsAdapter;
import com.hao.lib.di.scope.FragmentScope;
import com.hao.lib.mvp.contract.fragment.NewsContract;
import com.hao.lib.mvp.presenter.fragment.NewsPresenter;
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

    @Provides
    @FragmentScope
    BaseQuickAdapter provideNewsAdapter(NewsContract.Presenter presenter) {
        return new NewsAdapter(R.layout.item_main, presenter.getDataList());
    }
}
