package com.hao.lib.di.component.fragment;

import com.hao.lib.di.module.fragment.FragmentCommonModule;
import com.hao.lib.di.scope.FragmentScope;

import dagger.Component;

/**
 * @author Yang Shihao
 */
@FragmentScope
@Component(modules = FragmentCommonModule.class)
public interface FragmentCommonComponent {

//    void inject(BaseFragment baseFragment);
}
