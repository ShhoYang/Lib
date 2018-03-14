package com.yl.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.yl.library.R;

/**
 * @author Haoshi
 */

public class SquareRelativityLayout extends RelativeLayout {

    /**
     * 1-高度=宽度
     * 2-宽度=高度
     */
    private static final int H_W = 1;
    private static final int W_H = 2;
    private int mSpec = H_W;

    public SquareRelativityLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SquareRelativityLayout);
        mSpec = typedArray.getInt(R.styleable.SquareRelativityLayout_spec, mSpec);
    }

    public SquareRelativityLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SquareRelativityLayout);
        mSpec = typedArray.getInt(R.styleable.SquareRelativityLayout_spec, mSpec);
    }

    public SquareRelativityLayout(Context context) {
        super(context);
    }

    @SuppressWarnings("unused")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));

        int childWidthSize = getMeasuredWidth();
        int childHeightSize = getMeasuredHeight();
        if(mSpec == H_W){
            heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        }else {
            widthMeasureSpec = heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
