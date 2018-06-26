package com.hao.lib.di.component.fragment;

import com.hao.lib.di.component.AppComponent;
import com.hao.lib.di.module.fragment.FragmentCommonModule;
import com.hao.lib.di.module.fragment.NewsModule;
import com.hao.lib.di.scope.FragmentScope;
import com.hao.lib.ui.fragment.NewsFragment;

import dagger.Component;

/**
 * @author Yang Shihao
 */
@FragmentScope
@Component(modules = {FragmentCommonModule.class, NewsModule.class}, dependencies = AppComponent.class)
public interface NewsComponent {

    void inject(NewsFragment newsFragment);
}
