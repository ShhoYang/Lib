package com.hao.lib;

import android.util.Log;

import com.hao.lib.base.ui.BaseActivity;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TestActivity extends BaseActivity {

    private static final String TAG = "TestActivity";

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void initInject() {
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

        Observable.concatArrayDelayError(
                getObservableInteger(1),
                getObservableString("A"),
                getObservableInteger(2),
                getObservableString("B"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (o instanceof String) {
                            Log.d(TAG, "我是字符串" + ((String) o));
                        }

                        if (o instanceof Integer) {
                            Log.d(TAG, "我是数字" + ((Integer) o));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "throwable: " + throwable.getMessage());
                    }
                });
    }


    private Observable<Integer> getObservableInteger(final int i) {
        return Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                try {
                    Thread.sleep(500); // 假设此处是耗时操作
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (i == 2) {
                    new Throwable();
                }

                return i;
            }
        });
    }

    private Observable<String> getObservableString(final String s) {
        return Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    Thread.sleep(1000); // 假设此处是耗时操作
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return s;
            }
        });
    }
}
