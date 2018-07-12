package com.lsm.android_demo;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import org.litepal.LitePal;




/**
 * 自己定义application
 * 用于保存Context
 * 方便获取Context
 */
public class MyApplication extends Application {
    private static Context mContent;

    @Override
    public void onCreate() {
        super.onCreate();
        mContent = getApplicationContext();
        LitePal.initialize(this);
        //simple init 使用logger库前线初始化
        //Logger.addLogAdapter(new AndroidLogAdapter());
        //Log.d("MyApplication", "onCreate: ");
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                                        .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                                        .methodCount(0)         // (Optional) How many method line to show. Default 2
                                        .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                                        .logStrategy(null) // (Optional) Changes the log strategy to print out. Default LogCat
                                        .tag("My custom tag")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                                        .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        Logger.d("MyApplication %s", "onCreate");

    }

    public static Context getContext(){
        return mContent;
    }
}
