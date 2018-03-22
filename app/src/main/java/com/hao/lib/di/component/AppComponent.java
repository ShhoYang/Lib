package com.hao.lib.di.component;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.hao.lib.App;
import com.hao.lib.di.module.AppModule;
import com.hao.lib.rx.ApiService;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * @author Yang Shihao.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(App app);

    App provideApplication();

    PersistentCookieJar providePersistentCookieJar();

    OkHttpClient provideOkHttpClient();

    ApiService provideApiService();
}
