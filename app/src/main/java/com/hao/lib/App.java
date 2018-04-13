package com.hao.lib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import com.hao.lib.di.component.AppComponent;
import com.hao.lib.di.component.DaggerAppComponent;
import com.hao.lib.di.module.AppModule;
import com.hao.lib.mvp.ui.activity.CrashActivity;
import com.hao.lib.mvp.ui.activity.HomeActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import cat.ereza.customactivityoncrash.config.CaocConfig;

/**
 * @author Yang Shihao
 */

public class App extends MultiDexApplication {

    private static App mApp;
    private static AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApp = this;

        CaocConfig.Builder.create()
                .restartActivity(HomeActivity.class)
                .errorActivity(CrashActivity.class)
                .apply();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this, Constant.BASE_URL))
                .build();
    }


    public static App getApplication(){
        return mApp;
    }

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Scale);
            }
        });

        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new ClassicsFooter(context);
            }
        });
    }
}
