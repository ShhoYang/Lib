package com.hao.lib.base.mvp;

import com.hao.lib.rx.Api;
import com.hao.lib.rx.RxSubscriber;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author Yang Shihao
 */
public class APresenter<V extends IView> {


    private CompositeDisposable mDisposableStop;
    private CompositeDisposable mDisposableDestroy;

    protected V mView;
    protected Api mApi;

    public APresenter(V view, Api api) {
        mView = view;
        mApi = api;

        initBundle();
    }

    protected void initBundle() {

    }

    /**
     * 网络请求控制在生命周期OnStop
     */
    public void addRx2Stop(RxSubscriber rxSubscriber) {
        add(mDisposableStop, rxSubscriber);
    }

    public void addRx2Stop(Disposable disposable) {
        add(mDisposableStop, disposable);
    }

    public void onStop() {
        clear(mDisposableStop);
    }

    /**
     * 网络请求控制在生命周期onDestroy
     */
    public void addRx2Destroy(RxSubscriber rxSubscriber) {
        add(mDisposableDestroy, rxSubscriber);
    }

    public void addRx2Destroy(Disposable disposable) {
        add(mDisposableDestroy, disposable);
    }

    public void onDestroy() {
        clear(mDisposableDestroy);
        mView = null;
    }

    public CompositeDisposable getDisposable2Destroy() {
        if (mDisposableDestroy == null) {
            mDisposableDestroy = new CompositeDisposable();
        }
        return mDisposableDestroy;
    }

    private void add(CompositeDisposable compositeDisposable, RxSubscriber rxSubscriber) {
        Disposable subscribe = rxSubscriber.getSubscribe();
        if (subscribe != null && !subscribe.isDisposed()) {
            if (compositeDisposable == null) {
                compositeDisposable = new CompositeDisposable();
            }
            compositeDisposable.add(subscribe);
        }
    }

    private void add(CompositeDisposable compositeDisposable, Disposable disposable) {

        if (disposable != null && !disposable.isDisposed()) {
            if (compositeDisposable == null) {
                compositeDisposable = new CompositeDisposable();
            }
            compositeDisposable.add(disposable);
        }
    }

    private void clear(CompositeDisposable compositeDisposable) {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }
}
