package com.hao.lib.demo.lifecycle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.hao.lib.R;
import com.hao.lib.base.ui.BaseActivity;
import com.hao.lib.utils.AppManager;
import com.hao.lib.view.TabView;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tab_one)
    TabView mTabOne;
    @BindView(R.id.tab_two)
    TabView mTabTwo;
    @BindView(R.id.tab_three)
    TabView mTabThree;
    @BindView(R.id.tab_four)
    TabView mTabFour;

    private boolean mExit = false;
    private FragmentManager mFragmentManager;
    private OneFragment mOneFragment;
    private TwoFragment mTwoFragment;
    private ThreeFragment mThreeFragment;
    private FourFragment mFourFragment;

    private int mCurrentIndex = 0;
    private List<TabView> mTabViews = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initInject() {

    }

    @Override
    public void initView() {
        hideBack();
        mFragmentManager = getSupportFragmentManager();
        mTabViews.add(mTabOne);
        mTabViews.add(mTabTwo);
        mTabViews.add(mTabThree);
        mTabViews.add(mTabFour);

    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mFragments.clear();
        if (savedInstanceState != null) {
            mOneFragment = (OneFragment) mFragmentManager.getFragment(savedInstanceState, "one");
            mTwoFragment = (TwoFragment) mFragmentManager.getFragment(savedInstanceState, "two");
            mThreeFragment = (ThreeFragment) mFragmentManager.getFragment(savedInstanceState, "three");
            mFourFragment = (FourFragment) mFragmentManager.getFragment(savedInstanceState, "four");
            addToFragmentList(mOneFragment);
            addToFragmentList(mTwoFragment);
            addToFragmentList(mThreeFragment);
            addToFragmentList(mFourFragment);
            mCurrentIndex = savedInstanceState.getInt("index", 0);
            switchTab(mTabViews.get(mCurrentIndex));

        } else {
            mOneFragment = new OneFragment();
            addFragment(mOneFragment);
            showFragment(mOneFragment);
        }
        KLog.d(TAG, "initView: " + mFragments.size());
    }

    @Override
    public void initData() {

    }

    private void addToFragmentList(Fragment fragment) {
        if (fragment != null) {
            mFragments.add(fragment);
        }
    }

    private void addFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            mFragmentManager.beginTransaction().add(R.id.fl, fragment).commitAllowingStateLoss();
            mFragments.add(fragment);
        }
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        for (Fragment f : mFragments) {
            if (f != fragment) {
                transaction.hide(f);
            }
        }
        transaction.show(fragment).commitAllowingStateLoss();
    }

    private void switchTab(TabView tabView) {
        for (TabView view : mTabViews) {
            if (view != tabView) {
                view.selected(false);
            }
        }
        tabView.selected(true);
        setTitle(tabView.getText());
    }

    @Override
    public void onBackPressed() {
        if (mExit) {
            AppManager.getInstance().exit();
        } else {
            mExit = true;
            toast("再按返回键退出");
            Flowable.timer(3, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
                @Override
                public void accept(Long aLong) throws Exception {
                    mExit = false;
                }
            });
        }
    }

    @OnClick(R.id.tab_one)
    public void onTabOneClicked() {
        if (mOneFragment == null) {
            mOneFragment = new OneFragment();
        }
        addFragment(mOneFragment);
        showFragment(mOneFragment);
        switchTab(mTabOne);
        mCurrentIndex = 0;
    }

    @OnClick(R.id.tab_two)
    public void onTabTwoClicked() {
        if (mTwoFragment == null) {
            mTwoFragment = new TwoFragment();
        }
        addFragment(mTwoFragment);
        showFragment(mTwoFragment);
        switchTab(mTabTwo);
        mCurrentIndex = 1;
    }

    @OnClick(R.id.tab_three)
    public void onMTabThreeClicked() {
        if (mThreeFragment == null) {
            mThreeFragment = new ThreeFragment();
        }
        addFragment(mThreeFragment);
        showFragment(mThreeFragment);
        switchTab(mTabThree);
        mCurrentIndex = 2;
    }

    @OnClick(R.id.tab_four)
    public void onTabFourClicked() {
        if (mFourFragment == null) {
            mFourFragment = new FourFragment();
        }
        addFragment(mFourFragment);
        showFragment(mFourFragment);
        switchTab(mTabFour);
        mCurrentIndex = 3;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mOneFragment != null) {
            mFragmentManager.putFragment(outState, "one", mOneFragment);
        }
        if (mTwoFragment != null) {
            mFragmentManager.putFragment(outState, "two", mTwoFragment);
        }
        if (mThreeFragment != null) {
            mFragmentManager.putFragment(outState, "three", mThreeFragment);
        }
        if (mFourFragment != null) {
            mFragmentManager.putFragment(outState, "four", mFourFragment);
        }
        outState.putInt("index", mCurrentIndex);
        super.onSaveInstanceState(outState);
    }
}
