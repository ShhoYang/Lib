package com.hao.lib.di.component.activity;

import com.hao.lib.activity.ui.MainActivity;
import com.hao.lib.di.module.activity.MainModule;

import dagger.Subcomponent;

/**
 * @author Yang Shihao
 */
@Subcomponent(modules = MainModule.class)
public interface MainComponent {

    void inject(MainActivity mainActivity);

    @Subcomponent.Builder
    interface MainComponentBuilder {

        MainComponentBuilder mainMoudle(MainModule mainModule);

        MainComponent builder();
    }
}
