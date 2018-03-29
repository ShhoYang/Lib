package com.hao.lib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hao.lib.R;
import com.hao.lib.utils.DisplayUtils;

/**
 * @author Yang Shihao
 *         <p>
 *         单行
 */

public class ClearEditView extends LinearLayout {

    private EditText mEtContent;
    private ImageView mIvClear;
    private ImageView mIvIcon;
    private int mTextColor = 0xFF444444;
    private int mTextSize;
    private int mMaxLength = 0;
    private int mIconId = 0;
    private String mHint = "";
    private int mInputType = 1;

    public ClearEditView(Context context) {
        super(context);
        init(context, null);
    }

    public ClearEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ClearEditView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.clear_edit_view, this, true);
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClearEditView);
        mTextColor = typedArray.getColor(R.styleable.ClearEditView_textColor, mTextColor);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.ClearEditView_textSize, DisplayUtils.sp2px(context, 16));
        mMaxLength = typedArray.getInteger(R.styleable.ClearEditView_maxLength, mMaxLength);
        mHint = typedArray.getString(R.styleable.ClearEditView_hint);
        mIconId = typedArray.getResourceId(R.styleable.ClearEditView_icon, mIconId);
        mInputType = typedArray.getInteger(R.styleable.ClearEditView_inputType, mInputType);
        typedArray.recycle();
    }

    public void setText(String s) {
        mEtContent.setText(s);
        mEtContent.setSelection(s.length());
        mIvClear.setVisibility(GONE);
    }

    public void setInputType(int type) {
        if (type != -1) {
            mEtContent.setInputType(type);
        }
    }

    public String getText() {
        return mEtContent.getText().toString();
    }

    public EditText getEditText() {
        return mEtContent;
    }

    public void getFocus() {
        mEtContent.setFocusable(true);
        mEtContent.setFocusableInTouchMode(true);
        mEtContent.requestFocus();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mEtContent = findViewById(R.id.clear_et_content);
        mIvClear = findViewById(R.id.clear_iv_clear);
        mIvIcon = findViewById(R.id.clear_iv_icon);

        mEtContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        mEtContent.setTextColor(mTextColor);
        mEtContent.setHint(mHint);

        if (mInputType == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
            mEtContent.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            mEtContent.setInputType(mInputType);
        }

        if (mIconId == 0) {
            mIvIcon.setVisibility(View.GONE);
        } else {
            mIvIcon.setVisibility(View.VISIBLE);
            mIvIcon.setImageResource(mIconId);
        }
        setListener();
    }

    private void setListener() {
        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int len = s.length();

                if (len == 0) {
                    mIvClear.setVisibility(GONE);
                } else {
                    mIvClear.setVisibility(VISIBLE);
                }

                if (mMaxLength == 0) {
                    return;
                }

                if (len > mMaxLength) {
                    String str = s.toString();
                    String newStr = str.substring(0, mMaxLength);
                    mEtContent.setText(newStr);
                    mEtContent.setSelection(mMaxLength);
                }
            }
        });

        mEtContent.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ClearEditView.this.setSelected(hasFocus);
                mIvIcon.setSelected(hasFocus);
                if (hasFocus) {
                    if (mEtContent.getText().toString().length() != 0) {
                        mIvClear.setVisibility(VISIBLE);
                    }
                } else {
                    mIvClear.setVisibility(GONE);
                }
            }
        });

        mIvClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtContent.setText("");
            }
        });
    }
}
