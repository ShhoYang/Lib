package com.hao.lib.base.ui;

import android.os.Bundle;
import android.support.annotation.StringRes;

/**
 * @author Yang Shihao
 */
public interface IView {

    Bundle getBundle();

    void showDialog();

    void showDialog(String message);

    void dismissDialog();

    void toast(String msg);

    void toast(@StringRes int resId);

    void startActivity(Class<?> cls);

    void startActivity(Bundle bundle, Class<?> cls);

    void startActivityAndFinish(Class<?> cls);

    void startActivityAndFinish(Bundle bundle, Class<?> cls);

    void finishActivity();
}
