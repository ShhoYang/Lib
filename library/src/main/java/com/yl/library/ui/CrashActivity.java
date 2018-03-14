package com.yl.library.ui;

import android.Manifest;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import com.luck.picture.lib.permissions.RxPermissions;
import com.yl.library.R;
import com.yl.library.base.activity.BaseActivity;
import com.yl.library.utils.DateUtils;
import com.yl.library.utils.FileLocalUtils;
import com.yl.library.utils.FileUtils;

import java.io.File;
import java.lang.reflect.Field;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cat.ereza.customactivityoncrash.config.CaocConfig;


public class CrashActivity extends BaseActivity {

    private TextView mTvDetails;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_crash;
    }

    @Override
    protected void initMVP() {

    }

    @Override
    protected void initView() {
        showBack(false);
        setTitle("SORRY");
        mTvDetails = (TextView) $(R.id.tv_details);
        $(R.id.btn_restart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CaocConfig config = CustomActivityOnCrash.getConfigFromIntent(getIntent());
                CustomActivityOnCrash.restartApplication(CrashActivity.this, config);
            }
        });
    }

    @Override
    protected void initData() {
        final String error = CustomActivityOnCrash.getStackTraceFromIntent(getIntent());

        RxPermissions rxPermissions = new RxPermissions(this);
        if (rxPermissions.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && rxPermissions.isGranted(Manifest.permission.READ_PHONE_STATE)) {
            final String content = getDeviceInfo() + error;
            mTvDetails.setText(content);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FileUtils.writeString(new File(FileLocalUtils.getLogDir(), DateUtils.getTimeStr("yyyy-MM-dd-HH:mm:ss") + ".log"), content);
                }
            }).start();
        } else if (rxPermissions.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            mTvDetails.setText(error);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FileUtils.writeString(new File(FileLocalUtils.getLogDir(), DateUtils.getTimeStr("yyyy-MM-dd-HH:mm:ss") + ".log"), error);
                }
            }).start();
        } else {
            mTvDetails.setText(error);
        }

    }

    private String getDeviceInfo() {
        StringBuffer stringBuffer = new StringBuffer("");
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);

            if (packageInfo != null) {
                String versionName = packageInfo.versionName == null ? "null" : packageInfo.versionName;
                stringBuffer.append("versionName = ").append(versionName).append("\n")
                        .append("versionCode = ").append(packageInfo.versionCode).append("\n");
            }
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                stringBuffer.append(field.getName()).append(" = ").append(field.get(null).toString()).append("\n");
            }
            stringBuffer.append("\n\n");
            return stringBuffer.toString();
        } catch (PackageManager.NameNotFoundException | IllegalAccessException e) {
            return stringBuffer.toString();
        }
    }
}
