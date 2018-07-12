package com.lsm.android_demo.util;

import android.content.Context;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * 字符串操作的工具类
 **/
public class StringUtil {
    private static final String TAG = "StringUtil";

    private StringUtil() {
        throw new Error("我是工具类,不要实例化我哦");
    }

    public static String xmlDecode(String str) {
        return str.replaceAll("&gt;", ">").replaceAll("&lt;", "<").replaceAll("&apos;", "'").replaceAll("&quot;", "\"")
                .replaceAll("&amp;", "&").replaceAll("\n", "").replaceAll("\r", "");
    }

    static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 一位数前补0
     **/
    public static String numberAdd(int paramInt) {
        if ((paramInt >= 0) && (paramInt <= 9)) {
            return "0" + paramInt;
        } else {
            return String.valueOf(paramInt);
        }
    }

    /**
     * trim()后,字符串为空判断
     **/
    public static boolean isEmptyTrimed(String str) {
        return null == str || str.trim().length() == 0;
    }

    /**
     * TextUtils.isEmpty() 方法的源码 不适用长度不为0但是都是空格的字符串需
     **/
    public static boolean isEmptyNoTrim(CharSequence str) {
        try {
            return TextUtils.isEmpty(str);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());

        }
        return false;
    }

    /**
     * 回传为字符串数组:[0]大小;[1]单位
     **/
    public static String[] formatSize(long size) {
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
     * 回传为字符
     **/
    public static String formatSize2(long size) {
        String suffix = null;
        float fSize = 0;
        if (size >= 1024) {
            suffix = "KB";
            fSize = size / 1024;
            if (fSize >= 1024) {
                suffix = "MB";
                fSize /= 1024;
            }
            if (fSize >= 1024) {
                suffix = "GB";
                fSize /= 1024;
            }
        } else {
            fSize = size;
        }
        DecimalFormat df = new DecimalFormat("#0.00");
        StringBuilder resultBuffer = new StringBuilder(df.format(fSize));
        if (null != suffix) {
            resultBuffer.append(suffix);
        }
        return resultBuffer.toString();
    }

    /**
     * 字符串分割成数组
     **/
    public static String[] splitStr(String source, char spliter) {
        List<String> list = new ArrayList<String>();
        String subStr;
        String[] result;
        if (source.charAt(0) == spliter) {
            source = source.substring(1, source.length());
        }
        if (source.charAt(source.length() - 1) == spliter) {
            source = source.substring(0, source.length() - 1);
        }
        int start = 0;
        int end = source.indexOf(spliter);
        while (end > 0) {
            subStr = source.substring(start, end);
            list.add(subStr);
            start = end + 1;
            end = source.indexOf(spliter, start);
        }
        subStr = source.substring(start, source.length());
        list.add(subStr);
        result = new String[list.size()];
        Iterator<String> iter = list.iterator();
        int i = 0;
        while (iter.hasNext()) {
            result[i++] = (String) iter.next();
        }
        return result;
    }

    /**
     * 字符串首字母进行大写
     **/
    public static String firstLetterToUpper(String str) {
        char[] array = str.toCharArray();
        if (Character.isLetter(array[0]) && Character.isLowerCase(array[0])) {
            array[0] -= 32;
        }
        return String.valueOf(array);
    }

    /**
     * 字符串首字母进行小写
     **/
    public static String firstLetterToLower(String str) {
        char[] array = str.toCharArray();
        if (Character.isLetter(array[0]) && Character.isUpperCase(array[0])) {
            array[0] += 32;
        }
        return String.valueOf(array);
    }

    public static String getFromAssets(Context context, String fileName) {
        String result = "";
        try {
            InputStream inStream = context.getResources().getAssets().open(fileName);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            int len = -1;
            while ((len = inStream.read()) != -1) {
                outStream.write(len);
            }
            result = outStream.toString();
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return result;
    }

    /**
     * 是否为 json 格式
     **/
    public static Boolean isJson(String json) {
        if (StringUtil.isEmptyTrimed(json)) {
            LogUtil.e(TAG, "Empty/Null json content");
            return false;
        }
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                new JSONObject(json);
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            LogUtil.e(TAG, "Invalid Json");
            return false;
        }
    }


    /**
     * 是否为 xml 格式
     **/

    public static Boolean isXml(String xml) {
        if (StringUtil.isEmptyTrimed(xml)) {
            LogUtil.e(TAG, "Empty/Null xml content");
            return false;
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            return true;
        } catch (TransformerException e) {
            LogUtil.e(TAG, "Invalid xml");
            return false;
        }

    }


    /**
     * 合并两个 byte[]
     **/
    public static byte[] byteMerger(byte[] b1, byte[] b2) {
        byte[] tmp = new byte[b1.length + b2.length];
        System.arraycopy(b1, 0, tmp, 0, b1.length);
        System.arraycopy(b2, 0, tmp, b1.length, b2.length);
        return tmp;
    }


    public static String bytes2Str(byte[] b) {
        String result = null;
        try {
            result = new String(b, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * byte[] -> 16进制
     */
    public static String bytes2HexString(byte[] b) {
        StringBuffer result = new StringBuffer();
        String hex;
        for (int i = 0; i < b.length; i++) {
            hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            result.append(hex.toUpperCase());
        }
        return result.toString();
    }

    /**
     * byte[] -> 16进制大写字符串
     * 例如: new byte[]{ 0, (byte) 0xa8 }) ->  00A8
     */
    public static String bytes2HexStr(byte[] bytes) {
        char[] ret = new char[bytes.length << 1];
        for (int i = 0, j = 0; i < bytes.length; i++) {
            ret[j++] = hexDigits[bytes[i] >>> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    /**
     * 16进制大写字符串 -> byte[]
     * 例如: 00A8  -> { 0, (byte) 0xA8 }
     */
    public static byte[] hexStr2Bytes(String hexString) {
        int len = hexString.length();
        if (len % 2 != 0) {
            throw new IllegalArgumentException("长度不是偶数");
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >>> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return ret;
    }

    /**
     * hexChar转int
     *
     * @param hexChar hex单个字节
     * @return 0..15
     */
    private static int hex2Dec(char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }


    /**
     * 字符串转换为Ascii
     **/
    public static String stringToAscii(String str) {
        StringBuffer buffer = new StringBuffer();
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                buffer.append((int) chars[i]).append(",");
            } else {
                buffer.append((int) chars[i]);
            }
        }
        return buffer.toString();
    }

    /**
     * Ascii转换为字符串
     **/
    public static String asciiToString(String ascii) {
        StringBuffer sbu = new StringBuffer();
        String[] chars = ascii.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }


    /**
     * Ascii转换为字符串
     **/
    public static String asciiToString(byte bt) {
        StringBuffer sbu = new StringBuffer();
        String[] chars = String.valueOf(bt).split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }

    /**
     * 把byte 转化为两位十六进制数
     **/
    public static String toHex(byte b) {
        String result = Integer.toHexString(b & 0xFF);
        if (result.length() == 1) {
            result = '0' + result;
        }
        return result;
    }

    /**
     * int -> byte[4]
     * 将int数值转换为占四个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序
     */
    public static byte[] int2Bytes(int i) {
        byte[] result = new byte[4];
        result[3] = (byte) ((i >> 24) & 0xFF);  // 最高位,无符号右移。
        result[2] = (byte) ((i >> 16) & 0xFF);  // 次高位
        result[1] = (byte) ((i >> 8) & 0xFF);   // 次低位
        result[0] = (byte) (i & 0xFF);          // 最低位
        for (int j = 0; i < result.length; i++) {
            LogUtil.i(TAG, i + " -> int2Byte[" + j + "]: " + result[j]);
        }
        return result;
    }

    /**
     * int -> byte[4]
     * 将int数值转换为占四个字节的byte数组，本方法适用于(低位在后，高位在前)的顺序
     **/
    public static byte[] int2Bytes2(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF); // 最高位,无符号右移。
        result[1] = (byte) ((i >> 16) & 0xFF); // 次高位
        result[2] = (byte) ((i >> 8) & 0xFF);  // 次低位
        result[3] = (byte) (i & 0xFF);         // 最低位
        for (int j = 0; j < result.length; j++) {
            LogUtil.i(TAG, i + " -> int2Byte[" + j + "]: " + result[j]);
        }
        return result;
    }


    /**
     * byte[4] -> int
     * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序。和int2Bytes()配套使用
     **/
    public static int bytes2Int(byte[] src) {
        int value;
        value = (int) ((src[0] & 0xFF)
                | ((src[1] & 0xFF) << 8)
                | ((src[2] & 0xFF) << 16)
                | ((src[3] & 0xFF) << 24));
        return value;
    }

    /**
     * byte[4] -> int
     * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序。和int2Bytes2()配套使用
     */
    public static int bytes2Int2(byte[] src) {
        int value;
        value = (int) (((src[0] & 0xFF) << 24)
                | ((src[1] & 0xFF) << 16)
                | ((src[2] & 0xFF) << 8)
                | (src[3] & 0xFF));
        return value;
    }


    /**
     * short-> byte[2]
     **/
    public static byte[] short2Byte(short shorts) {
        int temp = shorts;
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Integer(temp & 0xff).byteValue();// 将最低位保存在最低位
            temp = temp >> 8; // 向右移8位
        }
        for (int i = 0; i < b.length; i++) {
            LogUtil.i(TAG, "short2Byte[" + i + "]:" + b[i]);
        }
        return b;
    }

    /**
     * byte[2] -> short
     **/
    public static short byte2Short(byte[] b, int index) {
        return (short) (((b[index + 1] << 8) | b[index + 0] & 0xff));
    }

    /**
     * byte[2] -> short
     **/
    public static short byte2Short(byte[] b) {
        short s = 0;
        short s0 = (short) (b[0] & 0xff);// 最低位
        short s1 = (short) (b[1] & 0xff);
        s1 <<= 8;
        s = (short) (s0 | s1);
        return s;
    }

    /**
     * 将byte转为2进制的字符串
     */
    public static String bytes2BinStr(byte b) {
        String result = "";
        byte a = b;
        for (int i = 0; i < 8; i++) {
            result = (a % 2) + result;
            a = (byte) (a / 2);
        }
        return result;
    }

}
