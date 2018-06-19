package com.hao.lib.demo.behavior;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hao.lib.R;
import com.hao.lib.adapter.NewsAdapter;
import com.hao.lib.base.ui.BaseActivity;
import com.hao.lib.bean.News;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ScrollBehaviorActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scroll_behavior;
    }

    @Override
    public void initInject() {
    }

    @Override
    public void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<News> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(new News(i + ""));
        }

        mRecyclerView.setAdapter(new NewsAdapter(R.layout.item_news, list));
    }

    @Override
    public void initData() {

    }
}
