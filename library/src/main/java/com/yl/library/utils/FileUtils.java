package com.yl.library.utils;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Yang Shihao
 * @date 2017/7/24
 */

public class FileUtils {

    private static final String ENCODING = "UTF-8";

    private FileUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void deleteFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        File file = new File(path);
        if (file == null) {
            return;
        }
        if (file.exists()) {
            file.delete();
        }
    }

    public static void deleteDir(File file) {
        if (file == null || !file.exists() || !file.isDirectory()) {
            return;
        }
        File[] files = file.listFiles();
        if(files == null){
            return;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                deleteDir(f);
            } else {
                f.delete();
            }
        }
        file.delete();
    }

    public static boolean writeString(File file, String content) {
        if (file == null || TextUtils.isEmpty(content)) {
            return false;
        }
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            os.write(content.getBytes(ENCODING));
            os.close();
        } catch (Exception ee) {
            return false;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
        }

        return true;
    }

    public static String readString(File file) {
        if (file == null) {
            return "";
        }
        Long length = file.length();
        byte[] fileContent = new byte[length.intValue()];
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            in.read(fileContent);
            in.close();
            return new String(fileContent, ENCODING);
        } catch (Exception e) {
            return "";
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
