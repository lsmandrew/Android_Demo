package com.lsm.android_demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.lsm.android_demo.R;

public class LifeCycleActivity extends AppCompatActivity {

    private static final String TAG = "ui.LifeCycleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle);
        Log.d(TAG, "onCreate: " + this.toString());
        Log.d(TAG, "taskid: " + this.getTaskId());
        if(savedInstanceState != null){//获取保存了的数据
            String tempData = savedInstanceState.getString("data_key");
            Log.d(TAG, tempData);
        }
    }
    //不可見变成可见
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    //在活动回收前一定会调用，可以用于保存状态，防止临时数据丢失
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String tempData = "keep data";
        outState.putString("data_key", tempData);
    }

    //暂停时
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    //完成不可见时
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: " + this.toString());
    }
    //standard 启动模式，不管栈是否存在activity，每次启动都创建实例
    public void onClick_Standard(View view) {
        Intent intent = new Intent(LifeCycleActivity.this, LifeCycleActivity.class);
        startActivity(intent);
    }

    //singleTop (栈顶单例)启动模式 需要在AndroidManifest.xml中配置，不指定默认是standard模式
    //如果在栈顶中活动不再创建实例而直接使用，但是如果不在栈顶位置再次启动会创建实例
    public void onClick_SingleTop(View view) {
        Intent intent = new Intent(LifeCycleActivity.this, SingleTopActivity.class);
        startActivity(intent);
    }
    //singleTask(task单例)模式
    //task栈中如果已经存在该activity的话，直接使用。并且把该activity上的activity全部出栈
    public void onClick_SingleTask(View view) {
        Intent intent = new Intent(LifeCycleActivity.this, SingleTaskActivity.class);
        startActivity(intent);
    }
    //SingleInstance模式，当使用该模式启动activity会创建新的task并且仅有该activity，方便各个程序共享该activity
    public void OnClick_SingleInstance(View view) {
        Intent intent = new Intent(LifeCycleActivity.this, SingleInstanceActivity.class);
        startActivity(intent);
    }
}
