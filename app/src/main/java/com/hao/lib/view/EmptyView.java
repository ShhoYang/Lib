package com.hao.lib.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hao.lib.R;

/**
 * @author Yang Shihao
 */
public class EmptyView extends LinearLayout {

    private String mNoDataText = "暂无数据";
    private String mLoadErrorText = "加载失败";

    private TextView mTextView;

    public EmptyView(Context context) {
        super(context);
        init(context);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.empty_view, this, true);
        mTextView = findViewById(R.id.tv_text);
    }

    public void hide() {
        setVisibility(GONE);
    }

    public void noData() {
        mTextView.setText(mNoDataText);
    }

    public void loadError() {
        mTextView.setText(mLoadErrorText);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mTextView.setOnClickListener(l);
        findViewById(R.id.iv_pic).setOnClickListener(l);
    }
}
