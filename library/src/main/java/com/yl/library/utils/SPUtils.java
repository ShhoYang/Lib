package com.yl.library.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Yang Shihao
 */
public class SPUtils {

    public SPUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "SPUtils";

    private static SharedPreferences getSP(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getSP(context).edit();
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     */
    public static void put(Context context, String key, String value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static void put(Context context, String key, Boolean value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putBoolean(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static void put(Context context, String key, Integer value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static void put(Context context, String key, Long value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putLong(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static void put(Context context, String key, Float value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putFloat(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static String get(Context context, String key, String defaultValue) {
        return getSP(context).getString(key, defaultValue);
    }

    public static long get(Context context, String key, long defaultValue) {
        return getSP(context).getLong(key, defaultValue);
    }

    public static int get(Context context, String key, int defaultValue) {
        return getSP(context).getInt(key, defaultValue);
    }

    public static boolean get(Context context, String key, boolean defaultValue) {
        return getSP(context).getBoolean(key, defaultValue);
    }

    public static float get(Context context, String key, float defaultValue) {
        return getSP(context).getFloat(key, defaultValue);
    }

    /**
     * 移除某个key值已经对应的值
     */
    public static void remove(Context context, String key) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public static void clear(Context context) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }
}