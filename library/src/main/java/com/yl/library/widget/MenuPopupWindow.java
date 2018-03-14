package com.yl.library.widget;

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

import com.yl.library.R;
import com.yl.library.adapter.MenuItemAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * @author Yang Shihao
 */

public class MenuPopupWindow implements PopupWindow.OnDismissListener {

    private static final float DEFAULT_ALPHA = 0.7f;

    private Context mContext;
    private int mWidth;
    private int mHeight;
    private int mTextColor = -1;
    private int mBackGround = -1;
    private int mAnimationStyle = -1;
    private float mWindowBackgroundAlpha = 0.7f;

    private Window mWindow;
    private PopupWindow mPopupWindow;
    private List mMenuItems;
    private ItemClickListener mItemClickListener;

    private MenuPopupWindow(Context context) {
        mContext = context;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public MenuPopupWindow showAsDropDown(View anchor, int xOff, int yOff) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(anchor, xOff, yOff);
        }
        return this;
    }

    public MenuPopupWindow showAsDropDown(View anchor) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(anchor);
        }
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public MenuPopupWindow showAsDropDown(View anchor, int xOff, int yOff, int gravity) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(anchor, xOff, yOff, gravity);
        }
        return this;
    }

    /**
     * 相对于父控件的位置（通过设置Gravity.CENTER，下方Gravity.BOTTOM等 ），可以设置具体位置坐标
     */
    public MenuPopupWindow showAtLocation(View parent, int gravity, int x, int y) {
        if (mPopupWindow != null) {
            mPopupWindow.showAtLocation(parent, gravity, x, y);
        }
        return this;
    }

    private PopupWindow build() {
        if (mMenuItems == null) {
            new NullPointerException("menu is null");
        }
        RecyclerView contentView = new RecyclerView(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        contentView.setLayoutParams(params);
        contentView.setLayoutManager(new LinearLayoutManager(mContext));
        if (mBackGround != -1) {
            contentView.setBackgroundColor(mBackGround);
        }
        contentView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        MenuItemAdapter adapter = new MenuItemAdapter(mContext, R.layout.menu_popup_window_item, mMenuItems);
        adapter.setTextColor(mTextColor);
        if (mItemClickListener != null) {
            adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    mItemClickListener.itemClick(position);
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
        }
        contentView.setAdapter(adapter);
        Activity activity = (Activity) mContext;
        if (activity != null) {
            mWindow = activity.getWindow();
            WindowManager.LayoutParams windowParams = mWindow.getAttributes();
            windowParams.alpha = mWindowBackgroundAlpha;
            mWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            mWindow.setAttributes(windowParams);
        }

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
        mPopupWindow.setFocusable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setOnDismissListener(this);
        mPopupWindow.update();
        return mPopupWindow;
    }

    @Override
    public void onDismiss() {
        dismiss();
    }

    /**
     * 关闭popWindow
     */
    public void dismiss() {
        if (mWindow != null) {
            WindowManager.LayoutParams params = mWindow.getAttributes();
            params.alpha = 1.0f;
            mWindow.setAttributes(params);
        }
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
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

        public Builder setTextColor(int color) {
            mMenuPopupWindow.mTextColor = color;
            return this;
        }

        public Builder setBackGroundColor(int color) {
            mMenuPopupWindow.mBackGround = color;
            return this;
        }

        public Builder setAnimationStyle(int animationStyle) {
            mMenuPopupWindow.mAnimationStyle = animationStyle;
            return this;
        }

        public Builder setWindowBackgroundAlpha(@FloatRange(from = 0, to = 1.0f) float darkValue) {
            mMenuPopupWindow.mWindowBackgroundAlpha = darkValue;
            return this;
        }

        public Builder setMenuItems(List menuItems) {
            mMenuPopupWindow.mMenuItems = menuItems;
            return this;
        }

        public Builder setItemClickListener(ItemClickListener itemClickListener) {
            mMenuPopupWindow.mItemClickListener = itemClickListener;
            return this;
        }

        public MenuPopupWindow build() {
            mMenuPopupWindow.build();
            return mMenuPopupWindow;
        }
    }

    public interface ItemClickListener {
        void itemClick(int position);
    }
}
