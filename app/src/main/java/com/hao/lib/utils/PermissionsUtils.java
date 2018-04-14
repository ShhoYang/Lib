package com.hao.lib.utils;

import android.app.Activity;

import com.luck.picture.lib.permissions.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * @author Yang Shihao
 * @date 2017/7/25
 * <p>
 * CALENDAR（日历）
 * READ_CALENDAR
 * WRITE_CALENDAR
 * <p>
 * CAMERA（相机）
 * CAMERA
 * <p>
 * CONTACTS（联系人）
 * READ_CONTACTS
 * WRITE_CONTACTS
 * GET_ACCOUNTS
 * <p>
 * LOCATION（位置）
 * ACCESS_FINE_LOCATION
 * ACCESS_COARSE_LOCATION
 * <p>
 * MICROPHONE（麦克风）
 * RECORD_AUDIO
 * <p>
 * PHONE（手机）
 * READ_PHONE_STATE
 * CALL_PHONE
 * READ_CALL_LOG
 * WRITE_CALL_LOG
 * ADD_VOICEMAIL
 * USE_SIP
 * PROCESS_OUTGOING_CALLS
 * <p>
 * SENSORS（传感器）
 * BODY_SENSORS
 * <p>
 * SMS（短信）
 * SEND_SMS
 * RECEIVE_SMS
 * READ_SMS
 * RECEIVE_WAP_PUSH
 * RECEIVE_MMS
 * <p>
 * STORAGE（存储卡）
 * READ_EXTERNAL_STORAGE
 * WRITE_EXTERNAL_STORAGE
 */

public class PermissionsUtils {

    private void checkPermission(Activity activity, String... permissions) {
        new RxPermissions(activity)
                .request(permissions)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {

                    }
                });

    }
}
