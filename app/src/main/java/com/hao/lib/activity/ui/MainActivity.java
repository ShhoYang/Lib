package com.hao.lib.activity.ui;

import com.hao.lib.App;
import com.hao.lib.R;
import com.hao.lib.activity.contract.MainContract;
import com.hao.lib.activity.presenter.MainPresenter;
import com.hao.lib.base.activity.BaseListActivity;
import com.hao.lib.di.component.activity.DaggerMainComponent;
import com.hao.lib.di.module.activity.MainModule;

public class MainActivity extends BaseListActivity<MainPresenter>
        implements MainContract.View {

    @Override
    protected void initInject() {
        DaggerMainComponent.builder()
                .appComponent(((App) getApplication()).getAppComponent())
                .mainModule(new MainModule(this))
                .build().inject(this);
    }

    @Override
    protected void initView() {
        showBack(false);
        setTitle(getString(R.string.app_name));
        setDefaultItemDecoration();
    }
}
