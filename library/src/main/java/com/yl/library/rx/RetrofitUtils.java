package com.yl.library.rx;

import android.support.annotation.NonNull;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.socks.library.KLog;
import com.yl.library.Library;
import com.yl.library.rx.gson.GsonConverterFactory;
import com.yl.library.rx.porxy.IGlobalManager;
import com.yl.library.rx.porxy.ProxyHandler;
import com.yl.library.rx.scalar.ScalarsConverterFactory;

import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * @author: HaoShi
 */
public class RetrofitUtils implements IGlobalManager {

    public static RetrofitUtils getInstance() {
        return Holder.INSTANCE;
    }

    private static Retrofit mRetrofit;
    private static OkHttpClient mClient;
    private PersistentCookieJar mPersistentCookieJar;

    public Retrofit getRetrofit() {
        if (mRetrofit == null) {
            if (mClient == null) {

                mPersistentCookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(Library.getInstance().getContext()));
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        KLog.d("json++++++++++", message);
                    }
                });
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                mClient = new OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .addInterceptor(logging)
                        .cookieJar(mPersistentCookieJar)
                        .build();
            }

            mRetrofit = new Retrofit.Builder()
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(mClient)
                    .build();

        }
        return mRetrofit;
    }

    public void cookieClear() {
        if (mPersistentCookieJar != null) {
            mPersistentCookieJar.clear();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> cls, @NonNull ProxyHandler proxyHandler) {
        T t = getRetrofit().create(cls);
        proxyHandler.setProxyObject(t);
        proxyHandler.setGlobalManager(this);
        return (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class<?>[]{cls}, proxyHandler);
    }

    @Override
    public void cancelAllRequest() {
        cookieClear();
        mClient.dispatcher().cancelAll();
    }

    static class Holder {
        public static RetrofitUtils INSTANCE = new RetrofitUtils();
    }
}
