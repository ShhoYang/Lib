package com.yl.library.base.mvp;

import android.content.Intent;

/**
 * @author Yang Shihao
 */
public interface IView {

    void showDialog();

    void showDialog(String message);

    void dismissDialog();

    void toast(String msg);

    void gotoActivity(Intent intent);

    void gotoActivityAndFinish(Intent intent);

    void finishActivity();
}
