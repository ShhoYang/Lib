package com.hao.lib.di.component.activity;

import com.hao.lib.di.module.activity.ActivityCommonModule;
import com.hao.lib.di.scope.ActivityScope;
import com.hao.lib.mvp.ui.activity.DetailsActivity;
import com.hao.lib.mvp.ui.activity.HomeActivity;
import com.hao.lib.mvp.ui.activity.WelcomeActivity;

import dagger.Component;

/**
 * @author Yang Shihao
 */
@ActivityScope
@Component(modules = ActivityCommonModule.class)
public interface ActivityCommonComponent {

    void inject(DetailsActivity detailsActivity);

    void inject(WelcomeActivity welcomeActivity);

    void inject(HomeActivity homeActivity);

}
