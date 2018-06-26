package com.hao.lib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hao.lib.R;
import com.hao.lib.utils.DisplayUtils;

/**
 * @author Yang Shihao
 */
public class ToolbarLayout extends RelativeLayout {

    private static final String TAG = "ToolbarLayout";

    private ImageView mIvBack;
    private ImageView mIvMenu;
    private TextView mTvTitle;
    private TextView mTvMenu;

    private boolean mShowBack = true;
    private String mTitleText;
    private String mMenuText;
    private Drawable mMenuIcon;

    public ToolbarLayout(Context context) {
        super(context);
        init(context, null);
    }

    public ToolbarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    protected void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.toolbar_layout, this);
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ToolbarLayout);
        mShowBack = typedArray.getBoolean(R.styleable.ToolbarLayout_tb_show_back, mShowBack);
        mTitleText = typedArray.getString(R.styleable.ToolbarLayout_tb_title_text);
        mMenuText = typedArray.getString(R.styleable.ToolbarLayout_tb_menu_text);
        mMenuIcon = typedArray.getDrawable(R.styleable.ToolbarLayout_tb_menu_icon);
        typedArray.recycle();
    }

    public void setTitle(String text) {
        ViewGroup.LayoutParams params = mTvTitle.getLayoutParams();
        params.width = DisplayUtils.getScreenWidth(getContext()) / 2;
        mTvTitle.setLayoutParams(params);
        mTvTitle.setText(text);
    }

    public void setTitleTextSize(float size) {
        mTvTitle.setTextSize(size);
    }

    //----------------------------------------------------------------------------------------------

    public void showBack() {
        mIvBack.setVisibility(VISIBLE);
    }

    public void hideBack() {
        mIvBack.setVisibility(GONE);
    }

    //----------------------------------------------------------------------------------------------

    public void showTextMenu() {
        mTvMenu.setVisibility(VISIBLE);
    }

    public void hideTextMenu() {
        mTvMenu.setVisibility(GONE);
    }

    public void setMenuText(String text) {
        mIvMenu.setVisibility(GONE);
        mTvMenu.setVisibility(VISIBLE);
        mTvMenu.setText(text);
    }

    //----------------------------------------------------------------------------------------------

    public void showIconMenu() {
        mIvMenu.setVisibility(VISIBLE);
    }

    public void hideIconMenu() {
        mIvMenu.setVisibility(GONE);
    }

    public void setMenuIcon(@DrawableRes int resId) {
        mTvMenu.setVisibility(GONE);
        mIvMenu.setVisibility(VISIBLE);
        mIvMenu.setImageResource(resId);
    }

    public void setOnClickListener(@Nullable OnClickListener backListener, @Nullable OnClickListener menuListener) {
        mIvBack.setOnClickListener(backListener);
        mTvMenu.setOnClickListener(menuListener);
        mIvMenu.setOnClickListener(menuListener);
    }

    public TextView getTvTitle() {
        return mTvTitle;
    }

    @Override
    public void setAlpha(float alpha) {
        mIvBack.setAlpha(alpha);
        mIvMenu.setAlpha(alpha);
        mTvMenu.setAlpha(alpha);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mIvBack = findViewById(R.id.iv_back);
        mIvMenu = findViewById(R.id.iv_menu);
        mTvTitle = findViewById(R.id.tv_title);
        mTvMenu = findViewById(R.id.tv_menu);


        if (!mShowBack) {
            mIvBack.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(mTitleText)) {
            setTitle(mTitleText);
        }

        if (mMenuIcon != null) {
            mIvMenu.setVisibility(VISIBLE);
            mIvMenu.setImageDrawable(mMenuIcon);

        } else if (!TextUtils.isEmpty(mMenuText)) {
            mTvMenu.setVisibility(VISIBLE);
            mTvMenu.setText(mMenuText);
        }

        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isEnabled()) {
            return super.dispatchTouchEvent(ev);
        } else {
            return true;
        }
    }
}
