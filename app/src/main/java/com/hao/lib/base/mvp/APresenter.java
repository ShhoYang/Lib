package com.hao.lib.base.mvp;

import com.hao.lib.base.proxy.UIProxy;
import com.hao.lib.rx.Api;
import com.hao.lib.rx.RxSubscriber;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author Yang Shihao
 */
public class APresenter<V> {

    private static final String TAG = "APresenter";

    private CompositeDisposable mDisposableStop;
    private CompositeDisposable mDisposableDestroy;

    protected V mView;
    protected Api mApi;
    protected UIProxy mUIProxy;

    public APresenter(V view, Api api) {
        mView = view;
        mApi = api;
    }

    public void setUIProxy(UIProxy UIProxy) {
        mUIProxy = UIProxy;
    }

    public void initBundle() {

    }

    /**
     * 网络请求控制在生命周期OnStop
     */
    public void addRx2Stop(RxSubscriber rxSubscriber) {
        Disposable subscribe = rxSubscriber.getSubscribe();
        if (subscribe != null && !subscribe.isDisposed()) {
            if (mDisposableStop == null) {
                mDisposableStop = new CompositeDisposable();
            }
            mDisposableStop.add(subscribe);
        }
    }

    public void addRx2Stop(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            if (mDisposableStop == null) {
                mDisposableStop = new CompositeDisposable();
            }
            mDisposableStop.add(disposable);
        }
    }

    public void onStop() {
        if (mDisposableStop != null && !mDisposableStop.isDisposed()) {
            mDisposableStop.clear();
        }
    }

    /**
     * 网络请求控制在生命周期onDestroy
     */
    public void addRx2Destroy(RxSubscriber rxSubscriber) {
        Disposable subscribe = rxSubscriber.getSubscribe();
        if (subscribe != null && !subscribe.isDisposed()) {
            if (mDisposableDestroy == null) {
                mDisposableDestroy = new CompositeDisposable();
            }
            mDisposableDestroy.add(subscribe);
        }
    }

    public void addRx2Destroy(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            if (mDisposableDestroy == null) {
                mDisposableDestroy = new CompositeDisposable();
            }
            mDisposableDestroy.add(disposable);
        }
    }

    public void onDestroy() {
        if (mDisposableDestroy != null && !mDisposableDestroy.isDisposed()) {
            mDisposableDestroy.clear();
        }
        mView = null;
    }

    public CompositeDisposable getDisposable2Destroy() {
        if (mDisposableDestroy == null) {
            mDisposableDestroy = new CompositeDisposable();
        }
        return mDisposableDestroy;
    }
}
