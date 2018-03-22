package com.hao.lib.di.module.activity;

import com.hao.lib.activity.contract.MainContract;
import com.hao.lib.activity.presenter.MainPresenter;
import com.hao.lib.rx.Api;

import dagger.Module;
import dagger.Provides;

/**
 * @author Yang Shihao
 */

@Module
public class MainModule {

    MainContract.View mView;

    public MainModule(MainContract.View view) {
        mView = view;
    }

    @Provides
    MainContract.View provideView() {
        return mView;
    }

    @Provides
    Api provideApi() {
        return new Api(null);
    }

    @Provides
    MainPresenter providePresenter(MainContract.View view, Api api) {
        return new MainPresenter(view, api);
    }
}
