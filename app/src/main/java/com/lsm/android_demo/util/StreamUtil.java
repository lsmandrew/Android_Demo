package com.lsm.android_demo.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * 流数据工具类
 **/
public class StreamUtil {
    private static final String TAG = "StreamUtil";

    private StreamUtil() {
        throw new Error("我是工具类,不要实例化我哦");
    }

    /**
     * InputStream --> byte[] 【从输入流中获取数据(二进制)】
     */
    public static byte[] inStreamToBytes(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[8192]; // 8K 缓冲区
        int len = -1;
        while (-1 != (len = inStream.read(bytes))) {
            outStream.write(bytes, 0, len);
            outStream.flush();
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * inStream --> outStream
     */
    private static void copy(InputStream inStream, OutputStream outStream) throws Exception {
        byte[] bytes = new byte[1024];
        int read;
        while (-1 != (read = inStream.read(bytes))) {
            outStream.write(bytes, 0, read);
        }
    }

    /**
     * InputStream --> String
     */
    public static String inStreamToStr(InputStream inStream) throws Exception {
        return new String(inStreamToBytes(inStream), ConstantUtil.CHARSET_NAME);
    }


    /**
     * InputStream --> String
     */
    public static String inStreamToStr2(InputStream inStream) throws Exception {
        // InputStream is = new FileInputStream(new File("text.txt"));
        BufferedReader in = new BufferedReader(new InputStreamReader(inStream, ConstantUtil.CHARSET_NAME), 8192);
        StringBuffer sb = new StringBuffer();
        String line = null;
        while (null != (line = in.readLine())) {
            sb.append(line + "\n");
        }
        inStream.close();
        return sb.toString();
    }


    /**
     * InputStream --> File
     */
    public static void inStreamToFile(InputStream inStream, File file) throws Exception {
        OutputStream outStream = new FileOutputStream(file);
        byte[] bytes = new byte[8192];
        int len = -1;
        while (-1 != (len = inStream.read(bytes, 0, 8192))) {
            outStream.write(bytes, 0, len);
            outStream.flush();
        }
        outStream.close();
        inStream.close();
    }

    /**
     * String --> InputStream
     */
    public static InputStream strToInStream(String str) throws Exception {
        ByteArrayInputStream inStream = new ByteArrayInputStream(str.getBytes(ConstantUtil.CHARSET_NAME));
        return inStream;
    }

    /**
     * File --> InputStream
     */
    public static InputStream fileToInStream(File file) throws IOException {
        InputStream inStream = new FileInputStream(file);
        return inStream;
    }


    /**
     * InputStream --> String
     */
    public static String inputStreamToString(InputStream is) throws IOException {
        // InputStream is = new FileInputStream(new File("text.txt"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8192);
        StringBuffer sb = new StringBuffer();
        String line = null;
        while (null != (line = reader.readLine())) {
            sb.append(line + "\n");
        }
        is.close();
        return sb.toString();
    }
}
