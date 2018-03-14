package com.yl.library.rx.porxy;

import android.support.annotation.NonNull;

import com.yl.library.rx.exception.TokenInvalidException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * @author Yang Shihao
 */
public abstract class ProxyHandler implements InvocationHandler {

    private IGlobalManager mGlobalManager;
    private Object mProxyObject;
    protected Disposable mDisposable;
    protected boolean mIsTokenNeedRefresh;


    public void setProxyObject(Object proxyObject) {
        mProxyObject = proxyObject;
    }

    public void setGlobalManager(IGlobalManager globalManager) {
        mGlobalManager = globalManager;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        return Observable.just(true).flatMap(new Function<Object, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull Object o) throws Exception {
                try {
                    if (mIsTokenNeedRefresh) {
                        updateMethodToken(method, args);
                    }
                    return (Observable<?>) method.invoke(mProxyObject, args);
                } catch (InvocationTargetException e) {
                    return null;
                } catch (IllegalAccessException e) {
                    return null;
                }
            }

        }).retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                        if (throwable instanceof TokenInvalidException) {
                            return refreshTokenWhenTokenInvalid();
                        }
                        return Observable.error(throwable);
                    }
                });
            }
        });
    }

    protected void refreshTokenSuccess() {
        mIsTokenNeedRefresh = true;
        dis();
    }

    protected void refreshTokenFail() {
        mGlobalManager.cancelAllRequest();
        dis();
    }

    private void dis() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    protected abstract Observable<?> refreshTokenWhenTokenInvalid();

    protected abstract void updateMethodToken(Method method, Object[] args);
}
