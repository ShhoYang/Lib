package com.hao.lib.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * @author Yang Shihao
 */

public class MenuPopupWindow implements PopupWindow.OnDismissListener {

    private static final float DEFAULT_ALPHA = 0.7f;

    private Context mContext;
    private int mWidth;
    private int mHeight;
    private int mAnimationStyle = -1;
    private float mWindowBackgroundAlpha = DEFAULT_ALPHA;
    private boolean mBackgroundDimEnabled = true;

    private Window mWindow;
    private PopupWindow mPopupWindow;
    private RecyclerView.Adapter mAdapter;

    private MenuPopupWindow(Context context) {
        mContext = context;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public View getContentView() {
        if (mPopupWindow == null) {
            return null;
        }
        return mPopupWindow.getContentView();
    }

    public MenuPopupWindow showAsDropDown(View anchor, int xOff, int yOff) {
        changeAlpha(mWindowBackgroundAlpha);
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(anchor, xOff, yOff);
        }
        return this;
    }

    public MenuPopupWindow showAsDropDown(View anchor) {
        changeAlpha(mWindowBackgroundAlpha);
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(anchor);
        }
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public MenuPopupWindow showAsDropDown(View anchor, int xOff, int yOff, int gravity) {
        changeAlpha(mWindowBackgroundAlpha);
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(anchor, xOff, yOff, gravity);
        }
        return this;
    }

    /**
     * 相对于父控件的位置（通过设置Gravity.CENTER，下方Gravity.BOTTOM等 ），可以设置具体位置坐标
     */
    public MenuPopupWindow showAtLocation(View parent, int gravity, int x, int y) {
        changeAlpha(mWindowBackgroundAlpha);
        if (mPopupWindow != null) {
            mPopupWindow.showAtLocation(parent, gravity, x, y);
        }
        return this;
    }

    public boolean isShowing() {
        return mPopupWindow.isShowing();
    }

    private void changeAlpha(float alpha) {
        if (!mBackgroundDimEnabled) {
            return;
        }
        Activity activity = (Activity) mContext;
        if (activity == null) {
            return;
        }
        mWindow = activity.getWindow();
        WindowManager.LayoutParams windowParams = mWindow.getAttributes();
        windowParams.alpha = alpha;
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mWindow.setAttributes(windowParams);
    }


    @Override
    public void onDismiss() {
        dismiss();
    }

    /**
     * 关闭popWindow
     */
    public void dismiss() {
        changeAlpha(1.0F);
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    private PopupWindow build() {
        RecyclerView contentView = new RecyclerView(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        contentView.setLayoutParams(params);
        contentView.setLayoutManager(new LinearLayoutManager(mContext));
        contentView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        contentView.setAdapter(mAdapter);
        if (mWidth == 0 && mHeight == 0) {
            mPopupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } else if (mHeight == 0) {
            mPopupWindow = new PopupWindow(contentView, mWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        } else if (mWidth == 0) {
            mPopupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, mHeight);
        } else {
            mPopupWindow = new PopupWindow(contentView, mWidth, mHeight);
        }
        if (mAnimationStyle != -1) {
            mPopupWindow.setAnimationStyle(mAnimationStyle);
        }
        mPopupWindow.setFocusable(false);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setOnDismissListener(this);
        mPopupWindow.update();
        return mPopupWindow;
    }

    public static class Builder {

        private MenuPopupWindow mMenuPopupWindow;

        public Builder(@NonNull Context context) {
            mMenuPopupWindow = new MenuPopupWindow(context);
        }

        public Builder setWidth(int width) {
            mMenuPopupWindow.mWidth = width;
            return this;
        }

        public Builder setHeight(int height) {
            mMenuPopupWindow.mHeight = height;
            return this;
        }

        public Builder setAnimationStyle(int animationStyle) {
            mMenuPopupWindow.mAnimationStyle = animationStyle;
            return this;
        }

        public Builder setWindowBackgroundAlpha(@FloatRange(from = 0, to = 1.0f) float backgroundAlpha) {
            mMenuPopupWindow.mWindowBackgroundAlpha = backgroundAlpha;
            return this;
        }

        public Builder setBackgroundDimEnabled(boolean backgroundDimEnabled) {
            mMenuPopupWindow.mBackgroundDimEnabled = backgroundDimEnabled;
            return this;
        }

        public Builder setAdapter(@NonNull RecyclerView.Adapter adapter) {
            mMenuPopupWindow.mAdapter = adapter;
            return this;
        }

        public MenuPopupWindow build() {
            mMenuPopupWindow.build();
            return mMenuPopupWindow;
        }
    }
}
