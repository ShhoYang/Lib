package com.hao.lib.rx;


import android.support.annotation.NonNull;

import com.hao.lib.base.mvp.IView;
import com.hao.lib.rx.exception.ApiException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.functions.Consumer;

/**
 * @author Yang Shihao
 */
public abstract class RxSubscriber<D> {

    private Disposable mSubscribe;

    public RxSubscriber(Observable<D> observable) {
        subscribe(observable, null);
    }

    public RxSubscriber(Observable<D> observable, IView view) {
        subscribe(observable, view);
    }

    private void subscribe(Observable<D> observable, final IView view) {
        mSubscribe = observable.subscribe(new Consumer<D>() {
            @Override
            public void accept(@NonNull D d) throws Exception {
                if (view != null) {
                    view.dismissDialog();
                }
                if (d == null) {
                    _onNull();
                } else {
                    _onNext(d);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                if (view != null) {
                    view.dismissDialog();
                }
                if (throwable instanceof HttpException
                        || throwable instanceof CompositeException
                        || throwable instanceof SocketTimeoutException
                        || throwable instanceof ConnectException
                        || throwable instanceof UnknownHostException) {
                    _onError(HttpCode.CODE_30001.getCode());
                } else if (throwable instanceof ApiException) {
                    _onError(throwable.getMessage());
                } else {
                    //T.showShort(App.().getContext(), "网络请求失败");
                }
            }
        });
    }

    public Disposable getSubscribe() {
        return mSubscribe;
    }

    protected abstract void _onNext(D d);

    protected void _onError(String code) {
       // T.showShort(Library.getInstance().getContext(), HttpCode.getMsg(code));
    }

    protected void _onNull() {

    }
}
