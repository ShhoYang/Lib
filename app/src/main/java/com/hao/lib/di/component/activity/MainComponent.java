package com.hao.lib.di.component.activity;

import com.hao.lib.activity.ui.MainActivity;
import com.hao.lib.di.component.AppComponent;
import com.hao.lib.di.module.activity.MainModule;
import com.hao.lib.di.scope.ActivityScope;

import dagger.Component;

/**
 * @author Yang Shihao
 */
@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {

    void inject(MainActivity mainActivity);
}
