package com.hao.lib.di.module.activity;

import android.content.Context;

import com.hao.lib.R;
import com.hao.lib.activity.contract.MainContract;
import com.hao.lib.activity.presenter.MainPresenter;
import com.hao.lib.activity.ui.MainActivity;
import com.hao.lib.adapter.MainAdapter;
import com.hao.lib.di.scope.ActivityScope;
import com.hao.lib.rx.Api;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * @author Yang Shihao
 */

@Module
public class MainModule {

    final Context mContext;
    final MainContract.View mView;

    public MainModule(MainActivity view) {
        mView = view;
        mContext = view;
    }

    @Provides
    @ActivityScope
    MainPresenter provideMainPresenter(Api api) {
        return new MainPresenter(mView, api);
    }

    @Provides
    MultiItemTypeAdapter provideMainAdapter(MainPresenter mainPresenter) {
        return new MainAdapter(mContext, R.layout.item_main, mainPresenter.getDataList());
    }
}
