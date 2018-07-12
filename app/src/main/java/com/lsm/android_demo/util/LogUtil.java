package com.lsm.android_demo.util;

import android.util.Log;


/**
 * Log操作的工具类
 **/
public class LogUtil {
    private static final String TAG = "LogUtil";

    private static final String TOP_BORDER = "╔═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════";
    private static final String LEFT_BORDER = "║ ";
    private static final String BOTTOM_BORDER = "╚═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════";
    //=================================================================
    private static final int v = 0;
    private static final int d = 1;
    private static final int i = 2;
    private static final int w = 3;
    private static final int e = 4;
    //===================以下参数，根据实际情况进行修改===================
    public static final int level = 5;//(ApplicationUtil.isDebugMode() == true ? 5 : -1);     //打开log信息
    private static boolean isSave = true;  // 日志是否保存在SD卡
    //每条日志记录的有效负载长度加上结构体logger_entry的长度不能超过4K个字节。根据平台通用性，这里自定义为1K
    private static int MAX_LENGTH = 1024;

    private LogUtil() {
        throw new Error("我是工具类,不要实例化我哦");
    }


    public static void v(String tag, String msg) {
        if (v < level) {
            logcat(tag, msg, "v");
        }
        if (isSave) {
            saveLog(tag, msg, "v");
        }
    }

    public static void d(String tag, String msg) {
        if (d < level) {
            logcat(tag, msg, "d");
        }
        if (isSave) {
            saveLog(tag, msg, "d");
        }
    }

    public static void i(String tag, String msg) {
        if (i < level) {
            logcat(tag, msg, "i");
        }
        if (isSave) {
            saveLog(tag, msg, "i");
        }
    }

    public static void w(String tag, String msg) {
        if (w < level) {
            logcat(tag, msg, "w");
        }
        if (isSave) {
            saveLog(tag, msg, "w");
        }
    }

    public static void e(String tag, String msg) {
        if (e < level) {
            logcat(tag, msg, "e");
        }
        if (isSave) {
            saveLog(tag, msg, "e");
        }
    }

    /*
     * Android Monitor -> logcat 显示log
     */
    private static void logcat(String tag, String msg, String flag) {
        String subStr = "";
        if (null != msg) {
            msg = msg.trim();
        }
        int index = 0;
        while (null != msg && !"".equals(msg) && index < msg.length()) {
            if (msg.length() <= index + MAX_LENGTH) {
                subStr = msg.substring(index);
            } else {
                subStr = msg.substring(index, index + MAX_LENGTH);
            }
            index += MAX_LENGTH;
            if ("v".equals(flag)) {
//                Log.v(tag, TOP_BORDER);
//                Log.v(tag, LEFT_BORDER + subStr.trim());
//                Log.v(tag, BOTTOM_BORDER);
                Log.v(tag, "  \n" + TOP_BORDER + "\n" + LEFT_BORDER + subStr.trim() + "\n" + BOTTOM_BORDER);
            } else if ("d".equals(flag)) {
//                Log.d(tag, TOP_BORDER);
//                Log.d(tag, LEFT_BORDER + subStr.trim());
//                Log.d(tag, BOTTOM_BORDER);
                Log.d(tag, "  \n" + TOP_BORDER + "\n" + LEFT_BORDER + subStr.trim() + "\n" + BOTTOM_BORDER);
            } else if ("i".equals(flag)) {
//                Log.i(tag, TOP_BORDER);
//                Log.i(tag, LEFT_BORDER + subStr.trim());
//                Log.i(tag, BOTTOM_BORDER);
                Log.i(tag, "  \n" + TOP_BORDER + "\n" + LEFT_BORDER + subStr.trim() + "\n" + BOTTOM_BORDER);

            } else if ("w".equals(flag)) {
//                Log.w(tag, TOP_BORDER);
//                Log.w(tag, LEFT_BORDER + subStr.trim());
//                Log.w(tag, BOTTOM_BORDER);
                Log.w(tag, "  \n" + TOP_BORDER + "\n" + LEFT_BORDER + subStr.trim() + "\n" + BOTTOM_BORDER);
            } else if ("e".equals(flag)) {
//                Log.e(tag, TOP_BORDER);
//                Log.e(tag, LEFT_BORDER + subStr.trim());
//                Log.e(tag, BOTTOM_BORDER);
                Log.e(tag, "  \n" + TOP_BORDER + "\n" + LEFT_BORDER + subStr.trim() + "\n" + BOTTOM_BORDER);
            }
        }
    }

    /*
     * log 保存
     */
    private static void saveLog(String tag, String msg, String flag) {
        if ("IndexActivity".equals(tag) || "ChannelActivity".equals(tag)) {

            String subStr = "";
            msg = msg.trim();
            int index = 0;
            while (index < msg.length()) {
                if (msg.length() <= index + MAX_LENGTH) {
                    subStr = msg.substring(index);
                } else {
                    subStr = msg.substring(index, index + MAX_LENGTH);
                }
                index += MAX_LENGTH;
                if ("v".equals(flag)) {
                    SdCardUtil.writeLogToSdCard("v[" + tag + "]: " + subStr.trim());
                } else if ("d".equals(flag)) {
                    SdCardUtil.writeLogToSdCard("d[" + tag + "]: " + subStr.trim());
                } else if ("i".equals(flag)) {
                    SdCardUtil.writeLogToSdCard("i[" + tag + "]: " + subStr.trim());
                } else if ("w".equals(flag)) {
                    SdCardUtil.writeLogToSdCard("w[" + tag + "]: " + subStr.trim());
                } else if ("e".equals(flag)) {
                    SdCardUtil.writeLogToSdCard("e[" + tag + "]: " + subStr.trim());
                }
            }
        }
    }


}