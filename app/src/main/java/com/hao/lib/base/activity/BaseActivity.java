package com.hao.lib.base.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hao.lib.Constant;
import com.hao.lib.R;
import com.hao.lib.base.mvp.APresenter;
import com.hao.lib.utils.AppManager;
import com.hao.lib.utils.DisplayUtils;
import com.jaeger.library.StatusBarUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import butterknife.Unbinder;

/**
 * @author Yang Shihao
 */
public abstract class BaseActivity<P extends APresenter> extends AppCompatActivity {

    @Inject
    protected P mPresenter;
    protected Activity mContext;
    private Unbinder mUnbinder;
    private ProgressDialog mDialog;

    @Nullable
    @BindView(R.id.base_rl_title)
    RelativeLayout mRlTitle;

    @Nullable
    @BindView(R.id.base_iv_left)
    ImageView mIvLeft;

    @Nullable
    @BindView(R.id.base_tv_left)
    TextView mTvLeft;

    @Nullable
    @BindView(R.id.base_tv_title)
    TextView mTvTitle;

    @Nullable
    @BindView(R.id.base_iv_right)
    ImageView mIvRight;

    @Nullable
    @BindView(R.id.base_tv_right)
    TextView mTvRight;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (fullScreen()) {
            //requestWindowFeature(Window.FEATURE_NO_TITLE);
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(getLayoutId());
        } else if (!hasToolbar()) {
            setContentView(getLayoutId());
            setStatsBarColor();
        } else {
            setContentView(R.layout.activity_base);
            LinearLayout activity = $(R.id.activity_base);
            View.inflate(this, getLayoutId(), activity);
            setStatsBarColor();
        }
        mUnbinder = ButterKnife.bind(this);
        AppManager.getInstance().pushActivity(this);
        mContext = this;
        initInject();
        initUI();
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mTvTitle != null) {
            ViewGroup.LayoutParams params = mTvTitle.getLayoutParams();
            params.width = DisplayUtils.getScreenWidth(this) / 2;
            mTvTitle.setLayoutParams(params);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        AppManager.getInstance().popActivity(this);
    }

    protected void setStatsBarColor() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
    }

    protected void initUI() {

    }

    protected <T extends View> T $(@IdRes int resId) {
        return (T) super.findViewById(resId);
    }

    protected <T extends View> T $(View layoutView, @IdRes int resId) {
        return (T) layoutView.findViewById(resId);
    }

    protected abstract @LayoutRes
    int getLayoutId();

    protected abstract void initInject();

    protected abstract void initView();

    protected abstract void initData();

    @Nullable
    public RelativeLayout getTitleView() {
        return mRlTitle;
    }

    protected boolean fullScreen() {
        return false;
    }

    protected boolean hasToolbar() {
        return true;
    }

    /**
     * 是否显示返回键,默认显示
     */
    protected void backVisibility(int visibility) {
        if (mTvLeft != null) {
            mIvLeft.setVisibility(visibility);
        }
    }

    /**
     * 设置Title背景颜色
     */
    protected void setTitleBackground(@ColorInt int color) {
        if (mRlTitle != null) {
            mRlTitle.setBackgroundColor(color);
        }
    }

    /**
     *
     */
    protected void setTitleOffset() {
        if (mRlTitle != null) {
            mRlTitle.setPadding(0, DisplayUtils.getStatusBarHeight(this), 0, 0);
        }
    }

    /**
     * 设置title
     */
    public void setTitle(String title) {
        if (mTvTitle != null) {
            mTvTitle.setText(title);
        }
    }

    /**
     * 左边的图片------------------------------------------------------------------------------------
     */
    protected void setImageLeft(@DrawableRes int resourceId) {
        if (mIvLeft != null) {
            mIvLeft.setImageResource(resourceId);
        }
    }

    protected void setLeftImageVisibility(int visibility) {
        if (mIvLeft != null) {
            mIvLeft.setVisibility(View.GONE);
        }
    }

    @Optional
    @OnClick(R.id.base_iv_left)
    protected void onImageViewLeftClicked() {
        finish();
    }

    /**
     * 左边的文字------------------------------------------------------------------------------------
     */
    protected void setTextLeft(String text) {
        if (mIvLeft != null) {
            mTvLeft.setText(text);
        }
    }

    protected void setLeftTextVisibility(int visibility) {
        if (mTvTitle != null) {
            mIvLeft.setVisibility(visibility);
        }
    }

    @Optional
    @OnClick(R.id.base_tv_left)
    protected void onTextViewLeftClicked() {

    }

    /**
     * 右边的图片------------------------------------------------------------------------------------
     */
    protected void setImageRight(@DrawableRes int resourceId) {
        if (mIvRight != null) {
            mIvRight.setImageResource(resourceId);
        }
    }

    protected void setRightImageVisibility(int visibility) {
        if (mIvRight != null) {
            mIvRight.setVisibility(visibility);
        }
    }

    @Optional
    @OnClick(R.id.base_iv_right)
    protected void onImageViewRightClicked() {

    }

    /**
     * 右边的文字------------------------------------------------------------------------------------
     */
    protected void setTextRight(String text) {
        if (mTvRight != null) {
            mTvRight.setText(text);
        }
    }

    protected void setRightTextVisibility(int visibility) {
        if (mTvRight != null) {
            mTvRight.setVisibility(visibility);
        }
    }

    @Optional
    @OnClick(R.id.base_tv_right)
    protected void onTextViewRightClicked() {

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
            intent.putExtra(Constant.EXTRA_BUNDLE, bundle);
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
        Intent intent = getIntent();
        if (intent == null) {
            return null;
        }

        return intent.getBundleExtra(Constant.EXTRA_BUNDLE);
    }
}
