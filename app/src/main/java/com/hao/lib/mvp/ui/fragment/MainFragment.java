package com.hao.lib.mvp.ui.fragment;

import android.os.Bundle;

import com.hao.lib.App;
import com.hao.lib.Constant;
import com.hao.lib.base.fragment.BaseListFragment;
import com.hao.lib.di.component.fragment.DaggerMainComponent;
import com.hao.lib.di.module.fragment.MainModule;
import com.hao.lib.mvp.contract.fragment.MainContract;
import com.hao.lib.mvp.presenter.fragment.MainPresenter;

public class MainFragment extends BaseListFragment<MainPresenter>
        implements MainContract.View {

    public static MainFragment newInstance(String type) {
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.EXTRA_STRING_1, type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void initInject() {
        DaggerMainComponent.builder()
                .appComponent(App.getAppComponent())
                .mainModule(new MainModule(mActivity,this))
                .build().inject(this);
    }

    @Override
    protected void initView() {
        setDefaultItemDecoration();
    }
}