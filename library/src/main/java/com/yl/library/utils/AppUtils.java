package com.yl.library.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.socks.library.KLog;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;


/**
 * @author Yang Shihao
 */
public class AppUtils {

    private static final String TAG = "AppUtils";

    private AppUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序版本名称信息
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (NameNotFoundException e) {
            return "";
        }
    }

    /**
     * 获取应用程序版本
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;

        } catch (NameNotFoundException e) {
            return 0;
        }
    }

    /**
     * app是否正在运行
     */
    public static boolean getAppState(Context context, String paceageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(paceageName) || info.baseActivity.getPackageName().equals(paceageName)) {
                KLog.i(TAG, "app正在运行 " + info.topActivity.getPackageName() + " --- " + info.baseActivity.getPackageName());
                return true;
            }
        }
        KLog.i(TAG, "app没有运行");

        return false;
    }

    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }

        return false;

    }

    /**
     * 获取服务是否开启
     *
     * @param context   上下文
     * @param className 完整包名的服务类名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isRunningService(Context context, String className) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = activityManager.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServices) {
            ComponentName service = runningServiceInfo.service;
            if (className.equals(service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断相对应的APP是否存在
     */
    public static boolean appInstalled(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        //获取手机系统的所有APP包名，然后进行一一比较
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        for (int i = 0; i < packageInfos.size(); i++) {
            if ((packageInfos.get(i)).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }

    /**
     * 打开外部app
     *
     * @param context
     * @param packagename
     */
    public static void gotoOtherApp(Context context, String packagename) {
        PackageInfo packageinfo = null;
        try {
            packageinfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (NameNotFoundException e) {

        }
        if (packageinfo == null) {
            return;
        }
        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = context.getPackageManager()
                .queryIntentActivities(resolveIntent, 0);
        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);
            intent.setComponent(cn);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public static String getIMEI(Context context, String phone) {
        StringBuffer imei = new StringBuffer("");
        try {
            TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String temp = tel.getDeviceId();
            if (TextUtils.isEmpty(temp)) {
                imei.append(phone).append("0000");
            } else {
                imei.append(temp);
                int length = imei.length();
                if (length < 15) {
                    for (int j = length; j < 15; j++) {
                        imei.append("0");
                    }
                } else if (length > 15) {
                    imei = new StringBuffer(imei.substring(0, 15));
                }
            }

            return imei.toString();
        } catch (Exception e) {
            return phone + "0000";
        }
    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }
}
