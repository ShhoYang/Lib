package com.hao.lib.di.module.activity;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.hao.lib.base.mvp.AListPresenter;
import com.hao.lib.base.mvp.APresenter;
import com.hao.lib.base.proxy.UIProxy;
import com.hao.lib.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author Yang Shihao
 */

@Module
public class ActivityCommonModule {

    private Activity mActivity;

    public ActivityCommonModule(Activity activity) {
        mActivity  = activity;
    }

    @Provides
    @ActivityScope
    UIProxy provideUIProxy() {
        return new UIProxy(mActivity);
    }

    @Provides
    @Nullable
    @ActivityScope
    APresenter provideAPresenter() {
        return null;
    }

    @Provides
    @Nullable
    @ActivityScope
    AListPresenter provideAListPresenter() {
        return null;
    }
}
