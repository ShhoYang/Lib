package com.hao.lib.di.module.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.hao.lib.base.mvp.AListPresenter;
import com.hao.lib.base.mvp.APresenter;
import com.hao.lib.base.mvp.AViewPagerPresenter;
import com.hao.lib.base.proxy.ListUIProxy;
import com.hao.lib.base.proxy.UIProxy;
import com.hao.lib.base.proxy.ViewPagerUIProxy;
import com.hao.lib.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author Yang Shihao
 */

@Module
public class FragmentCommonModule {

    private static final String TAG = "FragmentCommonModule";

    private Fragment mFragment;

    public FragmentCommonModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    @FragmentScope
    UIProxy provideUIProxy() {
        Log.d(TAG, "provideUIProxy: ");
        return new UIProxy(mFragment);
    }

    @Provides
    @FragmentScope
    ListUIProxy provideListUIProxy() {
        Log.d(TAG, "provideListUIProxy: ");
        return new ListUIProxy(mFragment);
    }

    @Provides
    @FragmentScope
    ViewPagerUIProxy provideViewPagerUIProxy() {
        Log.d(TAG, "provideViewPagerUIProxy: ");
        return new ViewPagerUIProxy(mFragment);
    }

    @Provides
    @Nullable
    @FragmentScope
    APresenter provideAPresenter() {
        return null;
    }

    @Provides
    @Nullable
    @FragmentScope
    AListPresenter provideAListPresenter() {
        return null;
    }

    @Provides
    @Nullable
    @FragmentScope
    AViewPagerPresenter provideAViewPagerPresenter() {
        return null;
    }
}
