package com.hao.mylibdemo.activity.ui;

import com.hao.mylibdemo.R;
import com.hao.mylibdemo.activity.contract.MainContract;
import com.hao.mylibdemo.activity.presenter.MainPresenter;
import com.hao.mylibdemo.adapter.MainAdapter;
import com.hao.mylibdemo.bean.News;
import com.yl.library.base.activity.BaseListActivity;
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
