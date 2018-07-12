package com.lsm.android_demo.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.NetworkInterface;
import java.net.SocketException;

public class HardwareUtil {
    private static final String TAG = "HardwareUtil";

    private HardwareUtil() {
        throw new Error("我是工具类,不要实例化我哦");
    }

    /**
     * 是否异显屏幕
     */
    public static boolean isPresentationScreen(Context context) {
        if (getScreenCount(context) == 2) {
            Display[] displays = getScreen(context);
            return displays[1].isValid();
        }
        return false;
    }

    /**
     * 获取 屏幕个数
     */

    public static int getScreenCount(Context context) {
        Display[] displays = getScreen(context);
        if (null != displays) {
            for (int i = 0; i < displays.length; i++) {
                LogUtil.i(TAG, "Display[ " + i + " ]信息：" + displays[i]);
            }
            return displays.length;
        } else {
            return 0;
        }
    }

    /**
     * 获取 屏幕
     */

    public static Display[] getScreen(Context context) {
        DisplayManager displayManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            displayManager = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        }
        Display[] displays = new Display[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            displays = displayManager.getDisplays();
        }
        return displays;
    }


    /**
     * 获取屏幕宽 dp
     */
    public static float getScreenWidth_dp(Context context) {
        return px2dp(context, getScreenWidth_px(context));
    }

    /**
     * 获取屏幕高 dp
     */
    public static float getScreenHeight_dp(Context context) {
        return px2dp(context, getScreenHeight_px(context));
    }

    /**
     * 获取屏幕高 dp
     */
    public static float getScreenHeightReal_dp(Context context) {
        return px2dp(context, getScreenHeightReal_px(context));
    }


    /**
     * 获取屏幕宽 px
     */
    public static int getScreenWidth_px(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取可用屏幕(显示的真实高度)高 px ,【如果底部有虚拟按键，屏幕高度是屏幕真是高度-虚拟按键的高度】(除虚拟栏Bar外，以上屏幕【含状态栏Bar】)
     * （有和没有底部的虚拟操作栏获取值相差一个虚拟栏的高）
     */
    public static int getScreenHeight_px(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕高 px（含 虚拟栏Bar、状态栏Bar ）
     */
    public static int getScreenHeightReal_px(Context context) {
        if (Build.VERSION.SDK_INT >= 17) {
            Point point = new Point();
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            manager.getDefaultDisplay().getRealSize(point);
            return point.y;
        } else {
            return 0;
        }
    }


    /**
     * 获取屏幕 逻辑密度N（即 px 和 dp 转化倍数值）px = dp * N
     * 【这是一个比例因子的密度无关的像素单元。
     * 一个像素 160 dp（例如屏幕240x320，1.5“X2” 屏幕），提供系统的显示基线。因此，在160dpi屏幕密度值是1；在120分辨率的屏幕将是75】
     */
    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 获取屏幕宽 英寸
     */
    public static float getScreenWidth_inch(Context context) {
        return (float) getScreenWidth_px(context) / getScreenDensity_dp(context);
    }


    /**
     * 获取屏幕高 英寸
     */
    public static float getScreenHeight_inch(Context context) {
        return (float) getScreenHeight_px(context) / getScreenDensity_dp(context);
    }

    /**
     * 获取屏幕高 英寸
     */
    public static float getScreenHeightReal_inch(Context context) {
        return (float) getScreenHeightReal_px(context) / getScreenDensity_dp(context);
    }

    /**
     * 获取屏幕物理密度 ppi (设备的点密度: 每英寸屏幕的精确物理像素)
     */
    public static int getScreenDensity_dp(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }


    /**
     * 获取屏幕宽物理密度 ppi (设备的点密度: 每英寸屏幕的精确物理像素)
     */
    public static float getX_density_dp(Context context) {
        return context.getResources().getDisplayMetrics().xdpi;
    }

    /**
     * 获取屏幕高物理密度 ppi (设备的点密度: 每英寸屏幕的精确物理像素)
     */
    public static float getY_density_dp(Context context) {
        return context.getResources().getDisplayMetrics().ydpi;
    }


    /**
     * 获取ANDROID ID
     */
    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 判断设备是否是手机
     */
    public static boolean isPhone(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }

    /**
     * 获取mac地址
     */
    public static String getMac(Context context) {
        String macAddress = null;
        StringBuffer buf = new StringBuffer();
        NetworkInterface networkInterface = null;
        try {
            networkInterface = NetworkInterface.getByName("eth1");
            if (null == networkInterface) {
                networkInterface = NetworkInterface.getByName("wlan0");
            }
            if (null == networkInterface) {
                return "02:00:00:00:00:02";
            }
            byte[] addr = networkInterface.getHardwareAddress();
            for (byte b : addr) {
                buf.append(String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            macAddress = buf.toString();
        } catch (SocketException e) {
            e.printStackTrace();
            return "02:00:00:00:00:02";
        }
        return macAddress;
    }


    /**
     * 设备信息
     **/
    public static String getHardwareInfo(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n++++++++++++++++++++++++++++++++++++++++++++++++++硬件信息++++++++++++++++++++++++++++++++++++++++++++++++++");
        sb.append("\n屏幕个数: " + getScreenCount(context));
        sb.append("\n同一个屏幕，无论分辨率改成什么，都无法改变既有宽高的dp（等同dip），文字大小sp，所以，推荐使用dp和sp，不建议使用px");
        sb.append("\n屏幕W              : " + getScreenWidth_px(context) + " px, " + getScreenWidth_dp(context) + " dp, " + getScreenWidth_inch(context) + "”");
        sb.append("\n屏幕H(包含虚拟栏Bar): " + getScreenHeightReal_px(context) + " px, " + getScreenHeightReal_dp(context) + " dp, " + getScreenHeightReal_inch(context) + "”");
        sb.append("\n屏幕H(不含虚拟栏Bar): " + getScreenHeight_px(context) + " px, " + getScreenHeight_dp(context) + " dp, " + getScreenHeight_inch(context) + "”");
        sb.append("\n虚拟栏Bar的高度为   :   " + getNavigationBarHeight_px(context) + " px,   " + getNavigationBarHeight_dp(context) + " dp");
        sb.append("\n状态栏Bar的高度为   :   " + getStatusBarHeight_px(context) + " px,   " + getStatusBarHeight_dp(context) + " dp");
        sb.append("\n屏幕尺寸: " + getScreenInch(context) + "”");
        sb.append("\n逻辑密度: " + getScreenDensity(context)).append("  ,物理密度: " + getScreenDensity_dp(context) + " ppi");
        sb.append("\n物理密度X: " + getX_density_dp(context) + " ppi").append("  ,物理密度Y: " + getY_density_dp(context) + " ppi");
        sb.append("\nMAC地址: " + getMac(context));
        sb.append("\n制造商/设备名/系统版本等诸多信息FINGERPRINT: " + Build.FINGERPRINT);
        sb.append("\nEither A changelist number: " + Build.ID);
        sb.append("\nHOST    : " + Build.HOST);
        sb.append("\nCPU_ABI : " + Build.CPU_ABI);
        sb.append("\nCPU_ABI2: " + Build.CPU_ABI2);
        sb.append("\n系统版本 : " + Build.VERSION.RELEASE);//手机系统版本
        sb.append("\n系统SDK : " + Build.VERSION.SDK);
        sb.append("\n系统CODENAME: " + Build.VERSION.CODENAME);
        sb.append("\n产品/硬件制造商MANUFACTURER: " + Build.MANUFACTURER);
        sb.append("\n用于向用户显示的构建ID字符串: " + Build.DISPLAY);
        sb.append("\n整体产品名称PRODUCT  : " + Build.PRODUCT);
        sb.append("\n品牌类型MODEL       : " + Build.MODEL);
        sb.append("\n品牌型号名DEVICE    : " + Build.DEVICE);
        sb.append("\n构建类型user/eng    : " + Build.TYPE);
        sb.append("\n基板名BOARD: " + Build.BOARD);
        sb.append("\nBRAND: " + Build.BRAND);
        sb.append("\nBOOTLOADER: " + Build.BOOTLOADER);
        sb.append("\nRADIO: " + Build.RADIO);
        sb.append("\nHARDWARE: " + Build.HARDWARE);
        sb.append("\nSERIAL: " + Build.SERIAL);
        sb.append("\n序列号: " + getSerialNumber());
        sb.append("\n>>>>>>>>>>");
        sb.append("\nVERSION_INCREMENTAL: " + Build.VERSION.INCREMENTAL);

        sb.append("\nRAM总共: " + getRAMTotalMemory());
        sb.append("\nROM总共: " + StringUtil.formatSize2(getRomMemroy()[0]));
        sb.append("\nROM可用: " + StringUtil.formatSize2(getRomMemroy()[1]));
        sb.append("\nCPU型号: " + getCpuName());
        sb.append("\nCPU序列号(16位): " + getCpuNO());
        sb.append("\nCPU当前频率（单位KHZ）: " + getCurCpuFreq());
        sb.append("\nCPU最小频率（单位KHZ）: " + getMinCpuFreq());
        sb.append("\nCPU最大频率（单位KHZ）: " + getMaxCpuFreq());
        sb.append("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        LogUtil.i(TAG, sb.toString());
        return sb.toString();
    }

    /**
     * 手机基本信息
     **/
    @SuppressLint("MissingPermission")
    public static String getPhoneInfo(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n\n+++++++++++++++++++++++++手机信息+++++++++++++++++++++++++");
        sb.append("\n是否是手机: " + isPhone(context));
        sb.append("\n手机号: " + tm.getLine1Number());
        sb.append("\n设备的软件版本号: " + tm.getDeviceSoftwareVersion());
        sb.append("\n国际移动设备身份码IMEI: " + tm.getDeviceId());
        sb.append("\nISO标准的国家码: " + tm.getNetworkCountryIso());
        sb.append("\n当前使用的网络类型: " + tm.getNetworkType());
        sb.append("\n手机类型: " + tm.getPhoneType());
        sb.append("\nSIM的状态信息: " + tm.getSimState());
        sb.append("\nSIM卡国家码: " + tm.getSimCountryIso());
        sb.append("\nSIM卡提供的移动国家码和移动网络码: " + tm.getSimOperator());
        sb.append("\nSIM卡的序列号: " + tm.getSimSerialNumber());
        sb.append("\n语音邮件号码: " + tm.getVoiceMailNumber());
        sb.append("\n服务商名称: " + tm.getSimOperatorName());
        String imsi = tm.getSubscriberId();
        if (null != imsi) {
            sb.append("\n国际移动设备用户识别码IMSI: " + imsi);
            sb.append("\n已注册运营商编号: " + tm.getNetworkOperator());
            sb.append("\n已注册运营商名称: " + tm.getNetworkOperatorName());
            // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信
            if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
                sb.append("\n已注册运营商名称: 中国移动");
            } else if (imsi.startsWith("46001")) {
                sb.append("\n已注册运营商名称: 中国联通");
            } else if (imsi.startsWith("46003")) {
                sb.append("\n已注册运营商名称: 中国电信");
            } else {
                sb.append("\n已注册运营商名称: 其他");
            }
        }

        sb.append("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        LogUtil.i(TAG, sb.toString());
        return sb.toString();
    }

    /**
     * 发送短信
     */
    public static void sendSms(Context context, String phoneNumber, String content) {
        Uri uri = Uri.parse("smsto:" + (StringUtil.isEmptyTrimed(phoneNumber) ? "" : phoneNumber));
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", StringUtil.isEmptyTrimed(content) ? "" : content);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        context.startActivity(intent);
    }

    /**
     * 拨打电话
     */
    public static void callPhone(final Context context, String phoneNumber) {
        if (!StringUtil.isEmptyTrimed(phoneNumber)) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            context.startActivity(intent);
        }
    }


    /**
     * 内存RAM
     **/
    public static String getRAMTotalMemory() {
        String path = "/proc/meminfo";
        String firstLine = null;
        int totalRam = 0;
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader br = new BufferedReader(fileReader, 8192);
            firstLine = br.readLine().split("\\s+")[1];
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (firstLine != null) {
            totalRam = (int) Math.ceil((new Float(Float.valueOf(firstLine) / (1024 * 1024)).doubleValue()));
        }

        return totalRam + "GB";//返回1GB/2GB/3GB/4GB
    }

    /**
     * meminfo
     **/
    public static void getMemoryInfo() {
        String str1 = "/proc/meminfo";
        String ram = "";
        StringBuilder sb = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fileReader, 8192);
            while (null != (ram = localBufferedReader.readLine())) {
                sb.append("\n" + ram);
            }
        } catch (IOException e) {
            LogUtil.e(TAG, e.getMessage());
        }
        LogUtil.i(TAG, sb.toString());
    }

    /**
     * Rom大小
     * 总共ROM: romInfo[0]
     * 可用ROM: romInfo[1]
     **/
    public static long[] getRomMemroy() {
        long[] romInfo = new long[2];
        romInfo[0] = getTotalInternalMemorySize();
        // Available rom memory
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        romInfo[1] = blockSize * availableBlocks;
//        LogUtil.i(TAG, "+++++++++++++++++++++++ ROM +++++++++++++++++++++++++++++" + //
//                "\n 总共ROM: " + StringUtil.formatSize2(romInfo[0]) + //
//                "\n 可用ROM: " + StringUtil.formatSize2(romInfo[1]));
        return romInfo;
    }

    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }


    /**
     * 序列号
     */
    public static String getSerialNumber() {
        String serial = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return serial;
    }

    /**
     * 获取CPU名字
     **/
    public static String getCpuName() {
        String cpuName = "";
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            cpuName = array[1];
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
        //LogUtil.i(TAG, "CPU名字: " + cpuName);
        return cpuName;
    }

    /**
     * 获取CPU序列号(16位)
     **/
    public static String getCpuNO() {
        String str;
        String cpuNo = "0000000000000000";
        String cpuAddress = "0000000000000000";
        try {
            Process pp = Runtime.getRuntime().exec("cat /proc/cpuinfo");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            // 查找CPU序列号
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {  // 查找到序列号所在行
                    if (str.indexOf("Serial") > -1) {
                        cpuNo = str.substring(str.indexOf(":") + 1, str.length());  // 提取序列号
                        cpuAddress = cpuNo.trim(); // 去空格
                        break;
                    }
                } else { // 文件结尾
                    break;
                }
            }
        } catch (Exception e) {
            LogUtil.i(TAG, e.getMessage());
        }
//        LogUtil.i(TAG, "cpuNo: " + cpuNo);
//        LogUtil.i(TAG, "cpuAddress: " + cpuAddress);
        return cpuAddress;
    }

    /**
     * 获取CPU最小频率（单位KHZ）
     **/
    public static String getMinCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        //LogUtil.i(TAG, "获取CPU最小频率: " + result.trim() + "KHZ");
        return result.trim();
    }

    /**
     * 实时获取CPU当前频率（单位KHZ）
     **/
    public static String getCurCpuFreq() {
        String result = "N/A";
        try {
            FileReader fr = new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //LogUtil.i(TAG, "实时获取CPU当前频率: " + result + "KHZ");
        return result;
    }

    /**
     * 获取CPU最大频率（单位KHZ）
     **/
    public static String getMaxCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        //LogUtil.i(TAG, "获取CPU最大频率: " + result.trim() + "KHZ");
        return result.trim();
    }

    /**
     * 获取顶部状态栏(status bar)高度px [与显示与否无关]
     */
    public static int getStatusBarHeight_px(Context context) {
        int height = 0;
        Rect localRect = new Rect();
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        height = localRect.top;
        if (0 == height) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int h = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                height = context.getResources().getDimensionPixelSize(h);
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
            }
        }
        return height;
    }

    /**
     * 获取顶部状态栏(status bar)高度dp [与显示与否无关]
     */
    public static float getStatusBarHeight_dp(Context context) {
        return px2dp(context, getStatusBarHeight_px(context));
    }


    /**
     * 获取底部虚拟操作栏(navigation bar)高度px [与显示与否无关]
     */
    public static int getNavigationBarHeight_px(Context context) {
        if (0 != context.getResources().getIdentifier("config_showNavigationBar", "bool", "android")) {
            int id = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            return context.getResources().getDimensionPixelSize(id);
        }
        return 0;
    }

    /**
     * 获取底部虚拟操作栏(navigation bar)高度dp [与显示与否无关]
     */
    public static float getNavigationBarHeight_dp(Context context) {
        return px2dp(context, getNavigationBarHeight_px(context));
    }

    /**
     * dp 转 px
     */
    public static int dp2px(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    /**
     * sp 转 px
     */
    public static int px2dip(Context context, float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    /**
     * px 转 dp
     */
    public static float px2dp(Context context, float px) {
        return (px / context.getResources().getDisplayMetrics().density);
    }

    /**
     * px 转 sp
     */
    public static float px2sp(Context context, float px) {
        return (px / context.getResources().getDisplayMetrics().scaledDensity);
    }


    /**
     * 获取屏幕尺寸
     **/
    public static double getScreenInch(Context context) {
        try {
            int realWidth = 0, realHeight = 0;
            Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            if (Build.VERSION.SDK_INT >= 17) {
                Point size = new Point();
                display.getRealSize(size);
                realWidth = size.x;
                realHeight = size.y;
            } else if (Build.VERSION.SDK_INT < 17 && Build.VERSION.SDK_INT >= 14) {
                realWidth = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
                realHeight = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
            } else {
                realWidth = metrics.widthPixels;
                realHeight = metrics.heightPixels;
            }
            return formatDouble(Math.sqrt((realWidth / metrics.xdpi) * (realWidth / metrics.xdpi) + (realHeight / metrics.ydpi) * (realHeight / metrics.ydpi)), 1);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return 0;
    }


    /**
     * Double类型保留指定位数的小数，返回double类型（四舍五入）
     * newScale 为指定的位数
     */
    private static double formatDouble(double inch, int newScale) {
        BigDecimal bigDecimal = new BigDecimal(inch);
        return bigDecimal.setScale(newScale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     * 设置屏幕为横屏
     * <p>还有一种就是在Activity中加属性android:screenOrientation="landscape"</p>
     * <p>不设置Activity的android:configChanges时，切屏会重新调用各个生命周期，切横屏时会执行一次，切竖屏时会执行两次</p>
     * <p>设置Activity的android:configChanges="orientation"时，切屏还是会重新调用各个生命周期，切横、竖屏时只会执行一次</p>
     * <p>设置Activity的android:configChanges="orientation|keyboardHidden|screenSize"（4.0以上必须带最后一个参数）时
     * 切屏不会重新调用各个生命周期，只会执行onConfigurationChanged方法</p>
     */
    public static void setLandscape(Context context) {
        ((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 设置屏幕为竖屏
     */
    public static void setPortrait(Context context) {
        ((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 判断是否横屏
     */
    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 判断是否竖屏
     */
    public static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 获取屏幕旋转角度
     */
    public static int getScreenRotation(Context context) {
        switch (((Activity) context).getWindowManager().getDefaultDisplay().getRotation()) {
            default:
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
        }
    }

    /**
     * 判断是否锁屏
     */
    public static boolean isScreenLock(Context context) {
        KeyguardManager manager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        return manager.inKeyguardRestrictedInputMode();
    }

    /**
     * 设置安全窗口，禁用系统截屏。防止 App 中的一些界面被截屏，并显示在其他设备中造成信息泄漏。
     * （常见手机设备系统截屏操作方式为：同时按下电源键和音量键）
     */
    public static void closeScreenShot(Context context) {
        ((Activity) context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
    }

}
