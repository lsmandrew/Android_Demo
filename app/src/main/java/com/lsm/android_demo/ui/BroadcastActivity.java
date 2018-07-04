package com.lsm.android_demo.ui;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lsm.android_demo.R;
import com.lsm.android_demo.broadcast.LocalBroadcastReceiver;

public class BroadcastActivity extends AppCompatActivity {

    private LocalBroadcastManager localBroadcastManager;
    private IntentFilter intentFilter;
    private LocalBroadcastReceiver localBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        /*
         * 本地广播
         * 只发送和接受本地的广播，其他应用不能接收到
         */
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        //动态注册接收器
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.lsm.android_demo.LOCAL_BROADCAST");
        localBroadcastReceiver = new LocalBroadcastReceiver();
        localBroadcastManager.registerReceiver(localBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localBroadcastReceiver);
    }

    public void onClick_SendBroad(View view) {
        Intent intent = new Intent("com.lsm.android_demo.MY_BROADCAST");
        sendBroadcast(intent);
    }

    public void onClick_LocalBraodCast(View view) {
        Intent intent = new Intent("com.lsm.android_demo.LOCAL_BROADCAST");
        localBroadcastManager.sendBroadcast(intent);
    }
}
