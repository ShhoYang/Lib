package com.yl.library.base.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yl.library.R;
import com.yl.library.base.mvp.APresenter;
import com.yl.library.utils.AppManager;
import com.yl.library.utils.DisplayUtils;
import com.yl.library.widget.StatusBarUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Yang Shihao
 */
public abstract class BaseActivity<P extends APresenter> extends AppCompatActivity
        implements View.OnClickListener {

    protected P mPresenter;
    protected Activity mContext;
    private Unbinder mUnbinder;
    private ProgressDialog mDialog;

    //左边图片
    private ImageView mIvLeft;
    //右边图片
    private ImageView mIvRight;
    //标题
    private TextView mTvTitle;
    //左边图片
    private TextView mTvLeft;
    //右边文字
    private TextView mTvRight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (fullScreen()) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(getLayoutId());
        } else if (!hasToolbar()) {
            setContentView(getLayoutId());
            StatusBarUtils.setColor(this, ContextCompat.getColor(this, R.color.theme_color));
        } else {
            setContentView(R.layout.activity_base);
            createUI();
            StatusBarUtils.setColor(this, ContextCompat.getColor(this, R.color.theme_color));
        }
        mUnbinder = ButterKnife.bind(this);
        AppManager.getInstance().pushActivity(this);
        mContext = this;
        initMVP();
        if (mPresenter != null) {
            mPresenter.mContext = this;
        }
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

    private void createUI() {
        LinearLayout activity = $(R.id.activity_base);
        View.inflate(this, getLayoutId(), activity);
        mIvLeft = $(R.id.base_iv_left);
        mIvRight = $(R.id.base_iv_right);
        mTvLeft = $(R.id.base_tv_left);
        mTvRight = $(R.id.base_tv_right);
        mTvTitle = $(R.id.base_tv_title);

        mIvLeft.setOnClickListener(this);
        mIvRight.setOnClickListener(this);
        mTvLeft.setOnClickListener(this);
        mTvRight.setOnClickListener(this);
    }

    protected void initUI() {

    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.base_iv_left) {
            onImageViewLeftClicked();
        } else if (viewId == R.id.base_iv_right) {
            onImageViewRightClicked();
        } else if (viewId == R.id.base_tv_left) {
            onTextViewLeftClicked();
        } else if (viewId == R.id.base_tv_right) {
            onTextViewRightClicked();
        }
    }

    protected <T extends View> T $(@IdRes int resId) {
        return (T) super.findViewById(resId);
    }

    protected <T extends View> T $(View layoutView, @IdRes int resId) {
        return (T) layoutView.findViewById(resId);
    }

    protected abstract @LayoutRes
    int getLayoutId();

    protected abstract void initMVP();

    protected abstract void initView();

    protected abstract void initData();

    protected boolean fullScreen() {
        return false;
    }

    protected boolean hasToolbar() {
        return true;
    }

    /**
     * 是否显示返回键,默认显示
     */
    protected void showBack(boolean b) {
        if (mTvTitle == null) {
            return;
        }
        if (b) {
            mIvLeft.setVisibility(View.VISIBLE);
        } else {
            mIvLeft.setVisibility(View.GONE);
        }
    }

    /**
     * 设置title
     */
    public void setTitle(String title) {
        if (mTvTitle == null) {
            return;
        }
        mTvTitle.setText(title);
    }

    /**
     * 左边的图片------------------------------------------------------------------------------------
     */
    protected void setImageLeft(@DrawableRes int resourceId) {
        if (mIvLeft == null) {
            return;
        }
        mIvLeft.setImageResource(resourceId);
    }

    protected void setLeftImageVisibility(int visibility) {
        if (mIvLeft == null) {
            return;
        }
        mIvLeft.setVisibility(View.GONE);
    }

    protected void onImageViewLeftClicked() {
        finish();
    }

    /**
     * 左边的文字------------------------------------------------------------------------------------
     */
    protected void setTextLeft(String text) {
        if (mIvLeft == null) {
            return;
        }
        mTvLeft.setText(text);
        mTvLeft.setVisibility(View.VISIBLE);
        mIvLeft.setVisibility(View.GONE);
    }

    protected void setLeftTextVisibility(int visibility) {
        if (mTvTitle == null) {
            return;
        }
        mIvLeft.setVisibility(visibility);
    }

    protected void onTextViewLeftClicked() {

    }

    /**
     * 右边的图片------------------------------------------------------------------------------------
     */
    protected void setImageRight(@DrawableRes int resourceId) {
        if (mIvRight == null) {
            return;
        }
        mIvRight.setVisibility(View.VISIBLE);
        mIvRight.setImageResource(resourceId);
    }

    protected void setRightImageVisibility(int visibility) {
        if (mIvRight == null) {
            return;
        }
        mIvRight.setVisibility(visibility);
    }

    protected void onImageViewRightClicked() {

    }

    /**
     * 右边的文字------------------------------------------------------------------------------------
     */
    protected void setTextRight(String text) {
        if (mTvRight == null) {
            return;
        }
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText(text);
    }

    protected void setRightTextVisibility(int visibility) {
        if (mTvRight == null) {
            return;
        }
        mTvRight.setVisibility(visibility);
    }

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

    /**
     * Activity跳转------------------------------------------------------------------------------------
     */
    public void gotoActivity(Intent intent) {
        startActivity(intent);
    }

    public void gotoActivityAndFinish(Intent intent) {
        startActivity(intent);
        finish();
    }

    public void finishActivity() {
        finish();
    }
}
