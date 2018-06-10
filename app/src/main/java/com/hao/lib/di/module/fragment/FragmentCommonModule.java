package com.hao.lib.di.module.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.hao.lib.base.mvp.AListPresenter;
import com.hao.lib.base.mvp.APresenter;
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
}
