package com.github.onlynight.combinechart.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by lion on 2017/9/5.
 */

public class Utils {

    public static final String ROOT_PATH = "CombineChart";

    public static String getSDPath() {
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            return Environment.getExternalStorageDirectory().toString();//获取跟目录
        }
        return null;
    }

    public static String getRootPath(Context context) {
        if (getSDPath() != null) {
            String path = getSDPath() + File.separator + ROOT_PATH;
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            return path;
        }
        return null;
    }

    public static String readJsonFile(Context context, String assetsFilename) {

        try {
            String filename = copyAssetsFile(context, assetsFilename);
            if (TextUtils.isEmpty(filename)) {
                return null;
            }
            InputStream is = new FileInputStream(filename);
            String json = "";
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            while ((line = reader.readLine()) != null) {
                json += line;
            }

            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String copyAssetsFile(Context context, String assetsFilename) {
        String rootPath = getRootPath(context);
        if (TextUtils.isEmpty(rootPath)) {
            return null;
        }

        String filename = rootPath + File.separator + assetsFilename;

        if (new File(filename).exists()) {
            return filename;
        }

        File rootFile = new File(rootPath);
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }

        try {
            FileOutputStream fos = new FileOutputStream(filename);
            InputStream is = context.getAssets().open(assetsFilename);
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            while ((line = reader.readLine()) != null) {
                writer.write(line);
            }
            reader.close();
            writer.close();

            return filename;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
