package com.lsm.android_demo.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * sdCard工具类
 * <p>
 * 解惑：
 * 获取Android手机的自带存储路径和sdcard存储路径
 * <p>
 * Android手机获取自带存储路径和sd卡存储路径的方式是：
 * 调用Environment.getExternalStorageDirectory()，返回的存储目录并不是系统内置的SD卡目录。
 * <p>
 * 1.一部分手机将eMMC存储挂载到 /mnt/external_sd 、/mnt/sdcard2 等节点，而将外置的SD卡挂载到 Environment.getExternalStorageDirectory()这个结点。
 * 此时，调用Environment.getExternalStorageDirectory()，则返回外置的SD的路径。
 * <p>
 * 2.而另一部分手机直接将eMMC存储挂载在Environment.getExternalStorageDirectory()这个节点，而将真正的外置SD卡挂载到/mnt/external_sd、/mnt/sdcard2 等节点。
 * 此时，调用Environment.getExternalStorageDirectory()，则返回内置的SD的路径。
 **/
public class SdCardUtil {
    private static final String TAG = "SdCardUtil";

    /**
     * sdCard卡是已经挂载
     **/
    public static boolean isSdCardMounted = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

    /**
     * sdCard卡是尚未挂载
     **/
    public static boolean isSdCardRemoved = Environment.getExternalStorageState().equals(Environment.MEDIA_REMOVED);

    /**
     * 内置和外置卡
     **/
    public static void getCards() {
        File sdFile = Environment.getExternalStorageDirectory();
        File parentFile = sdFile.getParentFile(); // 获取内置sdcard的父路径
        File[] listFiles = parentFile.listFiles(); // 列出该父目录下的所有路径
        int j = listFiles.length;
        for (int i = 0; i < j; i++) { // 如果子路径可以写 就是拓展卡（包含内置的和外置的）
            if (listFiles[i].canWrite()) {
                // LogUtil.i(TAG, listFiles[i].getAbsolutePath());
            }
        }
    }


    /**
     * 获取 SD 卡根目录
     **/
    public static File getSdCardDir() {
        if (isSdCardMounted) {
            return Environment.getExternalStorageDirectory(); // 取得sdcard文件路径
        } else {
            return Environment.getDataDirectory(); // Environment.getRootDirectory();
        }
    }

    /**
     * sdCard根目录（获取的目录默认没有最后的”/”,需要自己加上）
     **/
    public static String getSdCardPath() {
        if (null != getSdCardDir()) {
            return getSdCardDir().getPath();
        } else {
            return null;
        }
    }

    /**
     * SD卡是否可用（可写入数据）
     */
    public static boolean isAvailable() {
        if (null != getSdCardPath()) {
            return new File(getSdCardPath()).canWrite();
        } else {
            return false;
        }
    }

    /**
     * 获取磁盘可用空间
     */
    public static long getSDCardAvailaleSize() {
        File path = getSdCardDir();
        StatFs stat = new StatFs(path.getPath());
        long blockSize, availableBlocks;
        if (Build.VERSION.SDK_INT >= 18) {
            blockSize = stat.getBlockSizeLong();
            availableBlocks = stat.getAvailableBlocksLong();
        } else {
            blockSize = stat.getBlockSize();
            availableBlocks = stat.getAvailableBlocks();
        }
        return availableBlocks * blockSize;
    }

    /**
     * 获取某个目录可用大小
     */
    public static long getDirSize(String path) {
        StatFs stat = new StatFs(path);
        long blockSize, availableBlocks;
        if (Build.VERSION.SDK_INT >= 18) {
            blockSize = stat.getBlockSizeLong();
            availableBlocks = stat.getAvailableBlocksLong();
        } else {
            blockSize = stat.getBlockSize();
            availableBlocks = stat.getAvailableBlocks();
        }
        return availableBlocks * blockSize;
    }


    /**
     * 看文件系统空间
     **/
    private static StatFs getStatFs() {
        if (null != getSdCardPath()) {
            return new StatFs(getSdCardPath());
        }
        return null;
    }

    /**
     * Block的大小
     **/
    private static long getBlockSize() {
        if (null != getStatFs()) {
            return getStatFs().getBlockSize();
        }
        return 0L;
    }

    /**
     * 总Block数量
     **/

    private static long getTotalBlock() {
        if (null != getStatFs()) {
            return getStatFs().getBlockCount();
        }
        return 0L;
    }

    /**
     * 剩余可用的Block数量
     **/
    private static long getAvailableBlock() {
        if (null != getStatFs()) {
            return getStatFs().getAvailableBlocks();
        }
        return 0L;
    }

    public static String getSdCardInfo() {
        StringBuilder info = new StringBuilder();
        info.append(getSdCardPath() + ";");
        if (Environment.isExternalStorageRemovable()) { // API 2.3.3以上
            info.append("虚拟可卸;");
        } else {
            info.append("物理固化;");
        }
        if (isSdCardMounted) {
            String[] total = fileSize(getTotalBlock() * getBlockSize());
            String[] available = fileSize(getAvailableBlock() * getBlockSize());
            info.append("共" + total[0] + total[1] + ";剩余" + available[0] + available[1]);
        } else {
            info.append("但不可用,请检测是否被USB设备占用");
        }
        LogUtil.i(TAG, info.toString());
        return info.toString();
    }

    /**
     * sdCard总Block数量:MB
     **/
    public static long getSdCardTotalSize() {
        StringBuilder info = new StringBuilder();
        info.append("SdCard:");
        long totalSize = 0;
        if (isSdCardMounted) {
            totalSize = getTotalBlock() * getBlockSize() / 1024 / 1024;
            info.append("总共: " + totalSize + "MB");
        } else {
            info.append("但不可用,请检测是否被USB设备占用");
        }
        LogUtil.i(TAG, info.toString());
        return totalSize;
    }

    /**
     * sdCard剩余可用的Block数量:MB
     **/
    public static long getSdCardAvailableSize() {
        StringBuilder info = new StringBuilder();
        info.append("SdCard:");
        long availableSize = 0;
        if (isSdCardMounted) {
            availableSize = getAvailableBlock() * getBlockSize() / 1024 / 1024;
            info.append("SdCard剩余可用: " + availableSize + "MB");
        } else {
            info.append("但不可用,请检测是否被USB设备占用");
        }
        LogUtil.i(TAG, info.toString());
        return availableSize;
    }

    /**
     * 回传为字符串数组:[0]大小;[1]单位
     **/
    private static String[] fileSize(long size) {
        String str = "";
        if (size >= 1024) {
            str = "KB";
            size /= 1024;
            if (size >= 1024) {
                str = "MB";
                size /= 1024;
            }
        }
        DecimalFormat formatter = new DecimalFormat();
        formatter.setGroupingSize(3); // 每3个数字用,分隔如：1,000
        String result[] = new String[2];
        result[0] = formatter.format(size);
        result[1] = str;
        return result;
    }


    /**
     * 将 log 保存到 SDCard
     */
    public static void writeLogToSdCard(String log) {
        File file = null;
        String logPath = SdCardUtil.getSdCardPath() + File.separator + "SmartHotelLog" + File.separator + getSerialNumber() + "_Log_";
        try {
            Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
            String timeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(curDate);
            String fileName = new SimpleDateFormat("yyyyMMdd").format(curDate);
            if (SdCardUtil.isSdCardMounted) {
                LogUtil.i(TAG, "Serial Number: " + getSerialNumber());
                file = new File(logPath + fileName + ".txt");
                if (!file.exists()) {
                    LogUtil.i(TAG, "日志文件夹不存在，创建SmartHotelLog日志文件夹: " + file.getAbsolutePath());
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                log = timeStr + " " + log + "\r\n"; // 每次写入时，都加上时间，写完后换行

                RandomAccessFile raf = new RandomAccessFile(file, "rwd"); //追加
                raf.seek(file.length());
                raf.write(log.getBytes());
                raf.close();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public static String getLogs(String fileName) {
        String logTxt = "无日志信息";
        File file = null;
        String logPath = SdCardUtil.getSdCardPath() + File.separator + "SmartHotelLog" + File.separator + getSerialNumber() + "_Log_";
        try {
            if (SdCardUtil.isSdCardMounted) {
                file = new File(logPath + fileName + ".txt");
                if (file.exists() && file.isFile()) {
                    logTxt = StreamUtil.inputStreamToString(StreamUtil.fileToInStream(file));
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return logTxt;
    }

    public static File getLogFile(String fileName) {
        File file = null;
        String logPath = SdCardUtil.getSdCardPath() + File.separator + "SmartHotelLog" + File.separator + getSerialNumber() + "_Log_";
        try {
            if (SdCardUtil.isSdCardMounted) {
                file = new File(logPath + fileName + ".txt");
                if (file.exists() && file.isFile()) {
                    return file;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
        return null;
    }

    private static String getSerialNumber() {
        String serial = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serial;
    }


    /**
     * 保存错误信息到文件中
     */
    public static void saveCrashInfoToSdCard(Context context, Throwable ex) {
        Map<String, String> infos = new HashMap<String, String>(); // 用来存储设备信息和异常信息
        try {
            PackageManager manage = context.getPackageManager();
            PackageInfo info = manage.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (null != info) {
                String versionName = info.versionName == null ? "null" : info.versionName;
                String versionCode = info.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "An Error Occured when collect package info." + e.getMessage());
        }

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                LogUtil.e(TAG, "An Error Occured when collect crash info." + e.getMessage());
            }
        }


        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + " = " + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (null != cause) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();

        String result = writer.toString();
        sb.append(result);
        try {
            File file = null;
            Date curDate = new Date(System.currentTimeMillis());// 获取当前时间v
            String fileName = new SimpleDateFormat("yyyyMMdd").format(curDate);
            if (SdCardUtil.isSdCardMounted) {
                file = new File(SdCardUtil.getSdCardPath() + File.separator + "AS/Crash_" + fileName + ".txt");
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                String log = sb.toString() + "\r\n"; // 每次写入时，都加上时间，写完后换行
                RandomAccessFile raf = new RandomAccessFile(file, "rwd"); //追加
                raf.seek(file.length());
                raf.write(log.getBytes());
                raf.close();
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
    }
}