package com.hao.lib.di.module;

import android.content.Context;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.hao.lib.App;
import com.hao.lib.rx.Api;
import com.hao.lib.rx.ApiService;
import com.hao.lib.rx.gson.GsonConverterFactory;
import com.hao.lib.rx.porxy.HttpProxyHandler;
import com.hao.lib.rx.scalar.ScalarsConverterFactory;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.socks.library.KLog;

import java.lang.reflect.Proxy;
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
    public PersistentCookieJar providePersistentCookieJar() {
        return new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(mApplication));
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(PersistentCookieJar cookieJar) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                KLog.d("json++++++++++", message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
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
    public ApiService provideApiService(OkHttpClient okHttpClient){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        HttpProxyHandler proxyHandler = new HttpProxyHandler(mApplication);
        ApiService apiService = retrofit.create(ApiService.class);
        proxyHandler.setProxyObject(apiService);
        return (ApiService) Proxy.newProxyInstance(ApiService.class.getClassLoader(), new Class<?>[]{ApiService.class}, proxyHandler);
    }

    @Provides
    @Singleton
    public Api provideApi(ApiService apiService) {
        return new Api(apiService);
    }
}
