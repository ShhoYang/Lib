package com.hao.lib.ui.fragment;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hao.lib.App;
import com.hao.lib.Constant;
import com.hao.lib.R;
import com.hao.lib.adapter.NewsAdapter;
import com.hao.lib.base.ui.BaseListFragment;
import com.hao.lib.contract.fragment.NewsContract;
import com.hao.lib.di.component.fragment.DaggerNewsComponent;
import com.hao.lib.di.module.fragment.FragmentCommonModule;
import com.hao.lib.di.module.fragment.NewsModule;
import com.hao.lib.ui.activity.DetailsActivity;
import com.socks.library.KLog;

public class NewsFragment extends BaseListFragment<NewsContract.Presenter>
        implements NewsContract.View {

    private static String TAG = "NewsFragment---------------";

    public static NewsFragment newInstance(String type) {
        NewsFragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.EXTRA_STRING_1, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        KLog.d(TAG, "onAttach: ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        KLog.d(TAG, "onCreateView: ");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        KLog.d(TAG, "onViewCreated: ");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        KLog.d(TAG, "onActivityCreated: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        KLog.d(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        KLog.d(TAG, "onResume: ");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        KLog.d(TAG, "onSaveInstanceState: ");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        KLog.d(TAG, "onViewStateRestored: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        KLog.d(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        KLog.d(TAG, "onStop: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        KLog.d(TAG, "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        KLog.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        KLog.d(TAG, "onDetach: ");
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
        setDefaultItemDecoration();
    }

    @Override
    public BaseQuickAdapter createAdapter() {
        return new NewsAdapter(R.layout.item_news, mPresenter.getDataList());
    }

    @Override
    protected boolean isLazy() {
        return true;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        Intent intent = new Intent(mActivity, DetailsActivity.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constant.EXTRA_BEAN_1, mPresenter.getDataList().get(position));
            Bundle options = ActivityOptions.makeSceneTransitionAnimation(mActivity, view, getString(R.string.transition_name)).toBundle();
            intent.putExtras(bundle);
            startActivity(intent, options);

        } else {
            startActivity(intent);

        }
    }
}
