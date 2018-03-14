package com.yl.library.utils;

import android.Manifest;
import android.app.Activity;

import com.luck.picture.lib.permissions.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * @author Yang Shihao
 * @date 2017/7/25
 */

public class PermissionsUtils {

    private OnPermissionListener mPermissionListener;

    public static PermissionsUtils getInstance() {
        return Holder.INSTANCE;
    }

    public void checkRecordAudio(Activity activity, OnPermissionListener permissionListener) {
        mPermissionListener = permissionListener;
        checkPermission(activity, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public void checkLocation(Activity activity, OnPermissionListener permissionListener) {
        mPermissionListener = permissionListener;
        checkPermission(activity, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public void checkSD(Activity activity, OnPermissionListener permissionListener) {
        mPermissionListener = permissionListener;
        checkPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    private void checkPermission(Activity activity, String... permissions) {
        new RxPermissions(activity)
                .request(permissions)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            mPermissionListener.granted();
                        } else {
                            mPermissionListener.noGranted();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

    }

    public static class Holder {
        public static final PermissionsUtils INSTANCE = new PermissionsUtils();
    }

    public interface OnPermissionListener {
        void granted();

        void noGranted();
    }
}
