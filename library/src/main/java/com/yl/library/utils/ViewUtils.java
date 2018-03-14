package com.yl.library.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Yang Shihao
 * @date 2017/12/17
 */

public class ViewUtils {

    @SuppressWarnings({"unchecked"})
    public static <T extends View> T $(View view, int id) {
        return (T) view.findViewById(id);
    }

    /**
     * Simpler version of {@link Activity#findViewById(int)} which infers the * target type.
     */
    @SuppressWarnings({"unchecked"})
    public static <T extends View> T $(Activity activity, int id) {
        return (T) activity.findViewById(id);
    }

    public static <T extends View> T $(Object activityOrView, int id) {
        if (activityOrView instanceof Activity) {
            return $((Activity) activityOrView, id);
        }
        if (activityOrView instanceof View) {
            return $((View) activityOrView, id);
        }
        throw new IllegalArgumentException("activityOrView must be instance of Activity or View.");
    }

    public static <T extends View> T $(Context context, int id, ViewGroup root, boolean attachToRoot) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return (T) inflater.inflate(id, root, attachToRoot);
    }

    public static <T extends View> T $(Context context, int id, ViewGroup root) {
        return $(context, id, root, root != null);
    }

    public static <T extends View> T $(Context context, int id) {
        return $(context, id, null);
    }

    public static <T extends View> T $(LayoutInflater inflater, int id, ViewGroup root, boolean attachToRoot) {
        return (T) inflater.inflate(id, root, attachToRoot);
    }

    public static <T extends View> T $(LayoutInflater inflater, int id, ViewGroup root) {
        return $(inflater, id, root, root != null);
    }

    public static <T extends View> T $(LayoutInflater inflater, int id) {
        return $(inflater, id, null);
    }
}
