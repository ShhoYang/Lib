package com.hao.mylibdemo.di.component;



import com.hao.mylibdemo.App;
import com.hao.mylibdemo.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Yang Shihao.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(App app);

    App getContext();
}
