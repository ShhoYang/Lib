package com.hao.lib.di.component.fragment;

import com.hao.lib.di.component.AppComponent;
import com.hao.lib.di.module.fragment.MainModule;
import com.hao.lib.di.scope.FragmentScope;
import com.hao.lib.mvp.ui.fragment.MainFragment;

import dagger.Component;

/**
 * @author Yang Shihao
 */
@FragmentScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {

    void inject(MainFragment mainFragment);
}
