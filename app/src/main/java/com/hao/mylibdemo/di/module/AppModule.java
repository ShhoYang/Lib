package com.hao.mylibdemo.di.module;

import android.content.Context;


import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.hao.mylibdemo.App;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.socks.library.KLog;
import com.yl.library.rx.gson.GsonConverterFactory;
import com.yl.library.rx.scalar.ScalarsConverterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * @author Yang Shihao
 */
@Module
public class AppModule {

    private App mApplication;
    private String mBaseUrl;

    public AppModule(App application, String baseUrl) {
        mApplication = application;
        mBaseUrl = baseUrl;
    }

    @Provides
    @Singleton
    public Context provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    public HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                KLog.d("json++++++++++", message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    @Provides
    @Singleton
    public PersistentCookieJar providePersistentCookieJar() {
        return new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(mApplication));
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor, PersistentCookieJar cookieJar) {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .cookieJar(cookieJar)
                .build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofitUtils(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }
}
