package com.hao.lib.base.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hao.lib.base.mvp.APresenter;
import com.socks.library.KLog;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Yang Shihao
 */
public abstract class BaseFragment<P extends APresenter> extends Fragment {

    protected static String TAG;

    @Nullable
    @Inject
    protected P mPresenter;
    protected Activity mActivity;
    private View mRootView;
    private Unbinder mUnbinder;
    private ProgressDialog mDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        TAG ="Fragment lifecycle "+ getClass().getSimpleName();
        KLog.d(TAG, "onAttach: ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        KLog.d(TAG, "onCreateView: ");
        mRootView = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        mActivity = getActivity();
        initInject();
        onInitView();
        initView();
        initData();
        return mRootView;
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
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        KLog.d(TAG, "onViewStateRestored: ");
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
    public void onPause() {
        super.onPause();
        KLog.d(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        KLog.d(TAG, "onStop: ");
        dismissDialog();
        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }

    @Override
    public void onDestroyView() {
        KLog.d(TAG, "onDestroyView: ");
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }

        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroyView();
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

    protected void onInitView() {

    }

    /**
     * 加载对话框------------------------------------------------------------------------------------
     */
    public void showDialog() {
        showDialog("正在加载...");
    }

    public void showDialog(String message) {
        if (mDialog == null) {
            mDialog = new ProgressDialog(mActivity);
        }
        mDialog.setMessage(message);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    public void dismissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    /**
     * 吐司-----------------------------------------------------------------------------------------
     */
    private Toast mToast;

    public void toast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void toast(@StringRes int resId) {
        toast(mActivity.getString(resId));
    }

    /**
     * Activity跳转------------------------------------------------------------------------------------
     */
    public void startActivity(Class<?> cls) {
        startActivity(null, cls);
    }

    public void startActivity(Bundle bundle, Class<?> cls) {
        Intent intent = new Intent(mActivity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void startActivityAndFinish(Class<?> cls) {
        startActivity(null, cls);
        mActivity.finish();

    }

    public void startActivityAndFinish(Bundle bundle, Class<?> cls) {
        startActivity(bundle, cls);
        mActivity.finish();
    }

    public void finishActivity() {
        mActivity.finish();
    }

    public Bundle getBundle() {
        return getArguments();
    }

    /**
     * 抽象方法
     */
    protected abstract @LayoutRes
    int getLayoutId();

    protected abstract void initInject();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        KLog.d(TAG, "onSaveInstanceState: ");
    }
}
