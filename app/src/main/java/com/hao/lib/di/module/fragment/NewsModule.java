package com.hao.lib.di.module.fragment;

import android.support.v4.app.Fragment;

import com.hao.lib.R;
import com.hao.lib.adapter.NewsAdapter;
import com.hao.lib.di.scope.FragmentScope;
import com.hao.lib.mvp.contract.fragment.NewsContract;
import com.hao.lib.mvp.presenter.fragment.NewsPresenter;
import com.hao.lib.rx.Api;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * @author Yang Shihao
 */

@Module
public class NewsModule {

    private static final String TAG = "NewsModule";

    final NewsContract.View mView;

    public NewsModule(NewsContract.View view) {
        mView = view;
    }

    @Provides
    @FragmentScope
    Fragment provideFragment() {
        return (Fragment) mView;
    }

    @Provides
    @FragmentScope
    NewsContract.Presenter provideMainPresenter(Api api) {
        return new NewsPresenter(mView, api);
    }

    @Provides
    @FragmentScope
    MultiItemTypeAdapter provideMainAdapter(Fragment fragment,NewsContract.Presenter mainPresenter) {
        return new NewsAdapter(fragment.getContext(), R.layout.item_main, mainPresenter.getDataList());
    }
}
