package com.hao.lib.rx.porxy;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.hao.lib.rx.exception.TokenInvalidException;
import com.socks.library.KLog;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import retrofit2.http.Field;
import retrofit2.http.Part;

/**
 * @author Yang Shihao
 */
public class HttpProxyHandler implements InvocationHandler {

    private static final String TAG = "HttpProxyHandler";

    private Application mApplication;
    private Object mProxyObject;
    private Disposable mDisposable;
    private boolean mIsTokenNeedRefresh;

    public HttpProxyHandler(Application application) {
        mApplication = application;
    }

    public void setProxyObject(Object proxyObject) {
        mProxyObject = proxyObject;
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

    protected void refreshTokenFinished(boolean isSuccess) {
        mIsTokenNeedRefresh = isSuccess;
        if (!isSuccess) {
            exit();
        }
        dis();
    }

    private void dis() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }


    private void updateMethodToken(Method method, Object[] args) {
        if (mIsTokenNeedRefresh) {
            Annotation[][] annotationsArray = method.getParameterAnnotations();
            Annotation[] annotations;
            if (annotationsArray != null && annotationsArray.length > 0) {
                for (int i = 0; i < annotationsArray.length; i++) {
                    annotations = annotationsArray[i];
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof Field) {
                            if ("TOKEN".equals(((Field) annotation).value())) {
                                KLog.d(TAG, "替换token成功");
                                //args[i] = App.getInstance().getConfig().getToken();
                                // KLog.d("TOKEN", App.getInstance().getConfig().getToken());
                            }
                        } else if (annotation instanceof Part) {
                            if ("TOKEN".equals(((Part) annotation).value())) {
                                KLog.d(TAG, "替换token成功");
                                //args[i] = App.getInstance().getConfig().getToken();
                                // KLog.d("TOKEN", App.getInstance().getConfig().getToken());
                            }
                        }
                    }
                }
            }
            mIsTokenNeedRefresh = false;
        }
    }

    private synchronized Observable<?> refreshTokenWhenTokenInvalid() {
        synchronized (HttpProxyHandler.class) {
           /* final Bitmap.Config config = App.getInstance().getConfig();
            String phone = config.getPhone();
            String pwd = config.getPassword();
            if (!config.isLogin() || TextUtils.isEmpty(phone) || TextUtils.isEmpty(pwd)) {
                refreshTokenFail();
                logout();
            } else {
                mDisposable = new RxSubscriber<User>(Api.loginNoSubscribe(phone, pwd, AppUtils.getIMEI(mApplication, phone), "")) {
                    @Override
                    protected void _onNext(User user) {
                        KLog.d(TAG, "获取新的token成功");
                        config.setToken(user.getToken());
                        config.setUser(user);
                        mIsTokenNeedRefresh = true;
                        refreshTokenSuccess();
                    }

                    @Override
                    protected void _onError(String code) {
                        KLog.d(TAG, "获取新的token失败");
                        //登陆失败,跳转其他页面,关闭其他所有Activity
                        refreshTokenFail();
                        logout();
                    }
                }.getSubscribe();
            }*/
            return Observable.just(true);
        }
    }


    private void exit(){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
               /* App.getInstance().getConfig().exit();
                Intent intent = new Intent(mApplication, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Constant.KEY_BOOLEAN_1, true);
                mApplication.startActivity(intent);*/
            }
        });
    }
}
