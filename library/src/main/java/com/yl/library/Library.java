package com.yl.library;

import android.content.Context;
import android.support.annotation.NonNull;

import com.socks.library.KLog;
import com.yl.library.rx.HttpManager;
import com.yl.library.utils.FileLocalUtils;

/**
 * @author Yang Shihao
 */

public class Library {

    private Context mContext;
    private String mBaseUrl;

    public void init(@NonNull Context context, @NonNull String baseUrl, boolean isDebug) {
        mContext = context;
        mBaseUrl = baseUrl;
        KLog.init(isDebug);
        FileLocalUtils.FILE_NAME = context.getPackageName();
    }

    public Context getContext() {
        if (mContext == null) {
            new NullPointerException("Library not init");
        }

        return mContext;
    }

    public String getBaseUrl() {
        if (mBaseUrl == null) {
            new NullPointerException("Library not init");
        }
        return mBaseUrl;
    }

    public void cookieClear() {
        HttpManager.getInstance().cookieClear();
    }

    public static Library getInstance() {
        return Holder.INSTANCE;
    }

    public static class Holder {
        public static final Library INSTANCE = new Library();
    }
}
