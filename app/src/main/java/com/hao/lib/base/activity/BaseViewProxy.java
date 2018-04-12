package com.hao.lib.base.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.Toast;

import com.hao.lib.Constant;
import com.hao.lib.base.mvp.IView;

/**
 * @author Yang Shihao
 * @date 2018/4/13
 */
public class BaseViewProxy implements IView {

    private Activity mActivity;
    private Object mObject;
    private ProgressDialog mDialog;

    /**
     * 加载对话框------------------------------------------------------------------------------------
     */
    @Override
    public void showDialog() {
        showDialog("正在加载...");
    }

    @Override
    public void showDialog(String message) {
        if (mDialog == null) {
            mDialog = new ProgressDialog(mActivity);
        }
        mDialog.setMessage(message);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    @Override
    public void dismissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    /**
     * 吐司-----------------------------------------------------------------------------------------
     */
    private Toast mToast;

    @Override
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

    @Override
    public void toast(@StringRes int resId) {
        toast(mActivity.getString(resId));
    }

    /**
     * Activity跳转------------------------------------------------------------------------------------
     */
    public void startActivity(Class<?> cls) {
        startActivity(null, cls);
    }

    @Override
    public void startActivity(Bundle bundle, Class<?> cls) {
        Intent intent = new Intent(mActivity, cls);
        if (bundle != null) {
            intent.putExtra(Constant.EXTRA_BUNDLE, bundle);
        }
        if (mObject instanceof Activity) {
            ((Activity) mObject).startActivity(intent);
        } else if (mObject instanceof Fragment) {
            ((Fragment) mObject).startActivity(intent);
        }
    }

    @Override
    public void startActivityAndFinish(Class<?> cls) {
        startActivity(null, cls);
        mActivity.finish();

    }

    @Override
    public void startActivityAndFinish(Bundle bundle, Class<?> cls) {
        startActivity(bundle, cls);
        mActivity.finish();
    }

    @Override
    public void finishActivity() {
        mActivity.finish();
    }

    @Override
    public Bundle getBundle() {
        if (mObject instanceof Activity) {
            Intent intent = ((Activity) mObject).getIntent();
            if (intent == null) {
                return null;
            }
            return intent.getBundleExtra(Constant.EXTRA_BUNDLE);

        } else if (mObject instanceof Fragment) {
            return ((Fragment) mObject).getArguments();

        }

        return null;
    }
}
