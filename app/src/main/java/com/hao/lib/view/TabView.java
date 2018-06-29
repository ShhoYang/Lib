package com.hao.lib.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hao.lib.R;

/**
 * @author Yang Shihao
 */

public class TabView extends LinearLayout {

    private static final String TAG = "MainTab";

    private ImageView mIvIcon;
    private TextView mTvText;
    private TextView mTvRedNum;

    private int mIconId = -1;
    private String mText;
    private int mTextColor = -1;
    private boolean mSelected = false;

    public TabView(Context context) {
        super(context);
        init(context, null);
    }


    public TabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.tab_view, this, true);
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabView);
        mIconId = typedArray.getResourceId(R.styleable.TabView_icon, mIconId);
        mText = typedArray.getString(R.styleable.TabView_text);
        mTextColor = typedArray.getResourceId(R.styleable.TabView_textColor, mTextColor);
        mSelected = typedArray.getBoolean(R.styleable.TabView_selected, false);
    }

    public TabView setIconId(@DrawableRes int iconId) {
        mIvIcon.setImageResource(iconId);
        return this;
    }

    public TabView setText(String text) {
        mTvText.setText(text);
        return this;
    }

    public TabView setTextColor(int textColor) {
        mTvText.setTextColor(textColor);
        return this;
    }

    public TabView selected(boolean selected) {
        setSelected(selected);
        return this;
    }

    public String getText() {
        return mText;
    }

    public void setCount(String count) {
        if (TextUtils.isEmpty(count) || "0".equals(count)) {
            mTvRedNum.setVisibility(GONE);
        } else {
            mTvRedNum.setVisibility(VISIBLE);
            mTvRedNum.setText(count);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mIvIcon = findViewById(R.id.iv_icon);
        mTvText = findViewById(R.id.tv_text);
        mTvRedNum = findViewById(R.id.tv_red_num);
        if (mIconId != -1) {
            mIvIcon.setImageResource(mIconId);
        }

        if (mTextColor != -1) {
            ColorStateList csl = getResources().getColorStateList(mTextColor);
            mTvText.setTextColor(csl);
        }
        mTvText.setText(mText);
        setSelected(mSelected);
    }
}
