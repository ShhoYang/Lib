package com.hao.lib.di.module.fragment;

import android.app.Activity;

import com.hao.lib.R;
import com.hao.lib.adapter.MainAdapter;
import com.hao.lib.di.scope.FragmentScope;
import com.hao.lib.mvp.contract.fragment.MainContract;
import com.hao.lib.mvp.presenter.fragment.MainPresenter;
import com.hao.lib.rx.Api;
import com.socks.library.KLog;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * @author Yang Shihao
 */

@Module
public class MainModule {

    private static final String TAG = "MainModule";

    final Activity mActivity;
    final MainContract.View mView;

    public MainModule(Activity activity, MainContract.View view) {
        mActivity = activity;
        mView = view;
    }

    @Provides
    @FragmentScope
    MainPresenter provideMainPresenter(Api api) {
        return new MainPresenter(mView, api);
    }

    @Provides
    @FragmentScope
    MultiItemTypeAdapter provideMainAdapter(MainPresenter mainPresenter) {
        return new MainAdapter(mActivity, R.layout.item_main, mainPresenter.getDataList());
    }
}
