package com.yl.library.utils;

import android.os.Environment;

import java.io.File;
import java.io.IOException;


/**
 * @author Yang Shihao
 * @date 2017/7/20
 */

public class FileLocalUtils {

    public static String FILE_NAME = "";
    public static final String ROOT_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + FILE_NAME;

    public static boolean isMounted() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    public static File getRootDir() {
        File file = null;
        if (isMounted()) {
            file = new File(ROOT_DIR);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return file;
    }

    public static File getDownDir() {
        File file = null;
        if (isMounted()) {
            file = new File(ROOT_DIR, "download");
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return file;
    }

    public static File getLogDir() {
        File file = null;
        if (isMounted()) {
            file = new File(ROOT_DIR, "log");
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return file;
    }

    public static File getMonitorDir() {
        File file = null;
        if (isMounted()) {
            file = new File(ROOT_DIR, "monitor");
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return file;
    }

    public static File getImageDir() {
        File file = null;
        if (isMounted()) {
            file = new File(ROOT_DIR, "image");
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return file;
    }

    public static File getAudioDir() {
        File file = null;
        if (isMounted()) {
            file = new File(ROOT_DIR, "audio");
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return file;
    }

    public static String getAudioFile() {
        String path = null;
        try {
            if (isMounted()) {
                File file = new File(getAudioDir(), System.currentTimeMillis() + ".m4a");
                if (!file.exists()) {
                    file.createNewFile();
                }
                path = file.getAbsolutePath();
            }
        } catch (IOException e) {
            return null;
        }
        return path;
    }
}
