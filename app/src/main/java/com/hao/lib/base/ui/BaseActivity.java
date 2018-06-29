package com.hao.lib.base.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hao.lib.R;
import com.hao.lib.base.mvp.APresenter;
import com.hao.lib.rx.RxBus;
import com.hao.lib.utils.AppManager;
import com.hao.lib.utils.DisplayUtils;
import com.hao.lib.utils.T;
import com.hao.lib.view.ToolbarLayout;
import com.jaeger.library.StatusBarUtil;
import com.socks.library.KLog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author Yang Shihao
 */
public abstract class BaseActivity<P extends APresenter> extends AppCompatActivity {

    protected static String TAG;

    @Nullable
    @BindView(R.id.base_toolbar)
    ToolbarLayout mToolbarLayout;

    @Nullable
    @Inject
    protected P mPresenter;
    protected Activity mContext;
    private Unbinder mUnbinder;
    private ProgressDialog mDialog;
    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = "Activity lifecycle " + getClass().getSimpleName();
        KLog.d(TAG, "onCreate: ");
        if (fullScreen()) {
            //requestWindowFeature(Window.FEATURE_NO_TITLE);
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(getLayoutId());
        } else if (!hasToolbar()) {
            setContentView(getLayoutId());
            setStatsBarColor();
        } else {
            setContentView(R.layout.activity_base);
            LinearLayout activity = findViewById(R.id.activity_base);
            View.inflate(this, getLayoutId(), activity);
            setStatsBarColor();
        }
        mUnbinder = ButterKnife.bind(this);
        // AppManager.getInstance().pushActivity(this);
        mContext = this;
        initInject();
        onInitView();
        initView();
        initView(savedInstanceState);
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        KLog.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        KLog.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        KLog.d(TAG, "onStop: ");
        dismissDialog();
        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KLog.d(TAG, "onDestroy: ");
        clearCompositeDisposable();
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        // AppManager.getInstance().popActivity(this);
    }

    protected boolean fullScreen() {
        return false;
    }

    protected boolean hasToolbar() {
        return true;
    }

    protected void setStatsBarColor() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
    }


    /**
     * ToolbarLayout的操作
     */
    protected ToolbarLayout getToolbar() {
        return mToolbarLayout;
    }

    protected void setTitleOffset() {
        if (mToolbarLayout != null) {
            mToolbarLayout.setPadding(0, DisplayUtils.getStatusBarHeight(this), 0, 0);
        }
    }

    protected void onInitView() {
        if (mToolbarLayout != null) {
            mToolbarLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackClicked();
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMenuClicked();
                }
            });
        }
    }

    protected void initView(@Nullable Bundle savedInstanceState) {

    }

    protected void setTitleBackgroundColor(@ColorInt int color) {
        if (mToolbarLayout != null) {
            mToolbarLayout.setBackgroundColor(color);
        }
    }

    protected void setTitle(String text) {
        if (mToolbarLayout != null) {
            mToolbarLayout.setTitle(text);
        }
    }

    protected void setTitleTextSize(float size) {
        if (mToolbarLayout != null) {
            mToolbarLayout.setTitleTextSize(size);
        }
    }

    protected void showBack() {
        if (mToolbarLayout != null) {
            mToolbarLayout.showBack();
        }
    }

    protected void hideBack() {
        if (mToolbarLayout != null) {
            mToolbarLayout.hideBack();
        }
    }

    protected void showTextMenu() {
        if (mToolbarLayout != null) {
            mToolbarLayout.showTextMenu();
        }
    }

    protected void hideTextMenu() {
        if (mToolbarLayout != null) {
            mToolbarLayout.hideTextMenu();
        }
    }

    protected void setMenuText(String text) {
        if (mToolbarLayout != null) {
            mToolbarLayout.setMenuText(text);
        }
    }

    protected void showIconMenu() {
        if (mToolbarLayout != null) {
            mToolbarLayout.showIconMenu();
        }
    }

    protected void hideIconMenu() {
        if (mToolbarLayout != null) {
            mToolbarLayout.hideIconMenu();
        }
    }

    protected void setMenuIcon(@DrawableRes int resId) {
        if (mToolbarLayout != null) {
            mToolbarLayout.setMenuIcon(resId);
        }
    }

    protected void onBackClicked() {
        finish();
    }

    protected void onMenuClicked() {

    }

    /**
     * 注册一个RxBus
     */
    protected void registerRxBus(Class<T> cls, Consumer consumer) {
        Disposable subscribe = RxBus.getInstance().register(cls).subscribe(consumer, mThrowableConsumer);
        if (mPresenter == null) {
            createCompositeDisposable();
            mCompositeDisposable.add(subscribe);
        } else {
            mPresenter.getDisposable2Destroy().add(subscribe);
        }
    }

    private Consumer mThrowableConsumer = new Consumer<Throwable>() {
        @Override
        public void accept(@NonNull Throwable throwable) throws Exception {
            KLog.d("RxBus exception");
        }
    };

    private void createCompositeDisposable() {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
    }

    private void clearCompositeDisposable() {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }

    /**
     * 加载对话框------------------------------------------------------------------------------------
     */
    public void showDialog() {
        showDialog("正在加载...");
    }

    public void showDialog(String message) {
        if (mDialog == null) {
            mDialog = new ProgressDialog(this);
        }
        mDialog.setMessage(message);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    public void dismissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    /**
     * 吐司-----------------------------------------------------------------------------------------
     */
    private Toast mToast;

    public void toast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void toast(@StringRes int resId) {
        toast(getString(resId));
    }

    /**
     * Activity跳转------------------------------------------------------------------------------------
     */
    public void startActivity(Class<?> cls) {
        startActivity(null, cls);
    }

    public void startActivity(Bundle bundle, Class<?> cls) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void startActivityAndFinish(Class<?> cls) {
        startActivity(null, cls);
        finish();
    }

    public void startActivityAndFinish(Bundle bundle, Class<?> cls) {
        startActivity(bundle, cls);
        finish();
    }

    public void finishActivity() {
        finish();
    }

    public Bundle getBundle() {
        return getIntent().getExtras();
    }

    /**
     * 抽象方法
     */
    protected abstract @LayoutRes
    int getLayoutId();

    protected abstract void initInject();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        KLog.d(TAG, "onNewIntent: ");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        KLog.d(TAG, "onSaveInstanceState: ");
    }
}
