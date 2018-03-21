package com.hao.lib.activity.ui;

import com.hao.lib.R;
import com.hao.lib.activity.contract.MainContract;
import com.hao.lib.activity.presenter.MainPresenter;
import com.hao.lib.adapter.MainAdapter;
import com.hao.lib.bean.News;
import com.hao.lib.base.activity.BaseListActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;

public class MainActivity extends BaseListActivity<MainContract.Presenter>
implements MainContract.View{

    @Override
    protected void initMVP() {
        mPresenter = new MainPresenter();
        mPresenter.onCreate(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected CommonAdapter getAdapter() {
        return new MainAdapter(this, R.layout.item_main,new ArrayList<News.DataBean>());
    }
}
