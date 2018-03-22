package com.hao.lib.activity.ui;

import android.Manifest;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.TextView;

import com.hao.lib.R;
import com.hao.lib.base.activity.BaseActivity;
import com.hao.lib.utils.DateUtils;
import com.hao.lib.utils.FileLocalUtils;
import com.hao.lib.utils.FileUtils;
import com.luck.picture.lib.permissions.RxPermissions;

import java.io.File;
import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.OnClick;
import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cat.ereza.customactivityoncrash.config.CaocConfig;


public class CrashActivity extends BaseActivity {

    @BindView(R.id.tv_details)
    TextView mTvDetails;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_crash;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        showBack(false);
        setTitle("SORRY");
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

    @OnClick(R.id.btn_restart)
    public void onBtnRestartClicked() {
        CaocConfig config = CustomActivityOnCrash.getConfigFromIntent(getIntent());
        CustomActivityOnCrash.restartApplication(CrashActivity.this, config);
    }
}
