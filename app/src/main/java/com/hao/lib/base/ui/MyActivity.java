package com.hao.lib.base.ui;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hao.lib.R;
import com.hao.lib.base.mvp.APresenter;
import com.hao.lib.base.proxy.UIProxy;
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
public abstract class MyActivity<P extends APresenter,X extends UIProxy> extends AppCompatActivity
        implements IView {

    private static final String TAG = "MyActivity";

    @Nullable
    @Inject
    protected P mPresenter;
    protected Activity mContext;
    private Unbinder mUnbinder;

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

    @Inject
    protected X mUIProxy;

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
            LinearLayout activity = findViewById(R.id.activity_base);
            View.inflate(this, getLayoutId(), activity);
            setStatsBarColor();
        }
        mUnbinder = ButterKnife.bind(this);
        AppManager.getInstance().pushActivity(this);
        mContext = this;
        initInject();
        if (mPresenter != null) {
            mPresenter.setUIProxy(mUIProxy);
            mPresenter.initBundle();
        }
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
        mUIProxy.dismissDialog();
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
}
