package com.hao.lib.mvp.ui.fragment;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.hao.lib.App;
import com.hao.lib.Constant;
import com.hao.lib.R;
import com.hao.lib.base.ui.BaseListFragment;
import com.hao.lib.di.component.fragment.DaggerNewsComponent;
import com.hao.lib.di.module.fragment.FragmentCommonModule;
import com.hao.lib.di.module.fragment.NewsModule;
import com.hao.lib.mvp.contract.fragment.NewsContract;
import com.hao.lib.mvp.ui.activity.DetailsActivity;

public class NewsFragment extends BaseListFragment<NewsContract.Presenter>
        implements NewsContract.View {

    public static NewsFragment newInstance(String type) {
        NewsFragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.EXTRA_STRING_1, type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void initInject() {
        DaggerNewsComponent.builder()
                .appComponent(App.getAppComponent())
                .fragmentCommonModule(new FragmentCommonModule(this))
                .newsModule(new NewsModule(this))
                .build().inject(this);
    }

    @Override
    public void initView() {
        super.initView();
        mUIProxy.setDefaultItemDecoration();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.EXTRA_BEAN_1, mPresenter.getDataList().get(position));
        Bundle options = ActivityOptions.makeSceneTransitionAnimation(mActivity, view, getString(R.string.transition_name)).toBundle();
        Intent intent = new Intent(mActivity, DetailsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent, options);
    }
}
