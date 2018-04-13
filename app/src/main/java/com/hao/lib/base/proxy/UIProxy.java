package com.hao.lib.base.proxy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * @author Yang Shihao
 */
public class UIProxy {

    protected Activity mActivity;
    protected Fragment mFragment;
    private ProgressDialog mDialog;

    public UIProxy(@NonNull Activity activity) {
        mActivity = activity;
    }

    public UIProxy(@NonNull Fragment fragment) {
        mFragment = fragment;
        mActivity = mFragment.getActivity();
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
        if (mFragment == null) {
            mActivity.startActivity(intent);
        } else {
            mFragment.startActivity(intent);
        }
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
        if (mFragment == null) {
            Intent intent = mActivity.getIntent();
            if (intent == null) {
                return null;
            }
            return intent.getExtras();

        } else {
            return mFragment.getArguments();
        }
    }
}
