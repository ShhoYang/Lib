package com.hao.lib.base.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hao.lib.base.mvp.APresenter;
import com.hao.lib.base.proxy.UIProxy;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Yang Shihao
 */
public abstract class BaseFragment<P extends APresenter> extends Fragment
        implements IView {

    private static final String TAG = "BaseFragment";

    @Nullable
    @Inject
    protected P mPresenter;
    protected Activity mActivity;
    private View mRootView;
    private Unbinder mUnbinder;

    @Inject
    protected UIProxy mUIProxy;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        mActivity = getActivity();
        initInject();
        if (mPresenter != null) {
            mPresenter.setUIProxy(mUIProxy);
            mPresenter.initBundle();
        }
        initView();
        initData();
        return mRootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        mUIProxy.dismissDialog();
        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }


    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }

        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroyView();
    }
}
