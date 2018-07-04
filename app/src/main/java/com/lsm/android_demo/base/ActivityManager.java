package com.lsm.android_demo.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/*
 * activity 管理类
 * 用于方便销毁所有activity，退出时使用
 *
 */
public class ActivityManager {
    private  static List<Activity> activityList = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity){
        activityList.remove(activity);
    }

    public static void removeAll(){
        for (Activity activity: activityList) {
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
