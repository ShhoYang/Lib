package com.hao.lib.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author Yang Shihao
 */
public class DisplayUtils {

    private static DisplayMetrics mDisplayMetrics;

    private DisplayUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static DisplayMetrics getDisplayMetrics(Context context) {
        if (mDisplayMetrics == null) {
            mDisplayMetrics = context.getResources().getDisplayMetrics();
        }

        return mDisplayMetrics;
    }

    /**
     * 将px转换为dp值
     */
    public static int px2dip(Context context, float pxValue) {
        return (int) (pxValue / getDisplayMetrics(context).density + 0.5f);
    }

    /**
     * 将dp转换为px
     */
    public static int dip2px(Context context, float dipValue) {
        return (int) (dipValue * getDisplayMetrics(context).density + 0.5f);
    }

    /**
     * 将px转换为sp
     */
    public static int px2sp(Context context, float pxValue) {
        return (int) (pxValue / getDisplayMetrics(context).scaledDensity + 0.5f);
    }

    /**
     * 将sp转换为px
     */
    public static int sp2px(Context context, float spValue) {
        return (int) (spValue * getDisplayMetrics(context).scaledDensity + 0.5f);
    }

    /**
     * 获取屏幕宽度(px)
     */
    public static int getScreenWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    /**
     * 获取屏幕高度(px)
     */
    public static int getScreenHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }
    /**
     * 获得状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }
}
