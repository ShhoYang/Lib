package com.hao.lib.demo;

import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hao.lib.R;
import com.hao.lib.adapter.NewsAdapter;
import com.hao.lib.base.ui.BaseActivity;
import com.hao.lib.bean.News;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TestActivity extends BaseActivity {

    private static final String TAG = "TestActivity";
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.base_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.scroll)
    NestedScrollView mNestedScrollView;

    @Override
    protected boolean hasToolbar() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void initInject() {

    }

    @Override
    public void initView() {
        List<News> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new News("新闻" + i));
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new NewsAdapter(R.layout.item_news, list));
    }

    @Override
    public void initData() {
    }
}
