package com.yl.library.utils;

import android.app.Activity;

import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * @author Yang Shihao
 */
public class AppManager {

    private static final String TAG = "AppManager";

    private List<Activity> mStack = new ArrayList<>();

    public static AppManager getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 加入一个Activity到栈中
     */
    public void pushActivity(Activity activity) {
        synchronized (AppManager.class) {
            if (activity == null) {
                return;
            }
            if (mStack == null) {
                mStack = new Stack<>();
            }
            mStack.add(activity);
        }
        KLog.d(TAG, "pushActivity: " + mStack.size());
    }

    /**
     * 移除指定的Activity
     */
    public void popActivity(Activity activity) {
        synchronized (AppManager.class) {
            if (activity == null) {
                return;
            }
            if (mStack != null && mStack.size() != 0 && mStack.contains(activity)) {
                mStack.remove(activity);
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
        KLog.d(TAG, "popActivity: " + mStack.size());
    }

    /**
     * 移除指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if (mStack == null || mStack.isEmpty()) {
            return;
        }
        Iterator<Activity> iterator = mStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity.getClass().equals(cls)) {
                iterator.remove();
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
    }

    /**
     * 移除指定类名的Activity之外的所有Sctivity
     */
    public void finishAllActivityExceptAppoint(Class<?> cls) {
        if (mStack == null || mStack.isEmpty()) {
            return;
        }
        Iterator<Activity> iterator = mStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity.getClass().equals(cls)) {
                continue;
            }
            iterator.remove();
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        KLog.d(TAG,"还剩"+mStack.size()+"个Activity");
    }


    /**
     * 结束所有的Activity
     */
    public void exit() {
        synchronized (AppManager.class) {
            for (Activity activity : mStack) {
                if (activity != null) {
                    activity.finish();
                }
            }
            mStack.clear();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }

    public static class Holder {
        public static final AppManager INSTANCE = new AppManager();
    }
}
