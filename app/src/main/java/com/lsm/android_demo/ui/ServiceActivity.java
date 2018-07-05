package com.lsm.android_demo.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.lsm.android_demo.R;
import com.lsm.android_demo.server.FrontService;
import com.lsm.android_demo.server.MyService;

public class ServiceActivity extends AppCompatActivity {

    private static final String TAG = "ui.ServiceActivity";

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.DownloadBinder downloadBinder = (MyService.DownloadBinder)service;
            downloadBinder.StartDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
    }

    public void onClick_Start(View view) {
        Log.d(TAG, "onClick_Start:id =" + Thread.currentThread().getId());
        Intent startIntent = new Intent(this, MyService.class);
        startService(startIntent);//开启服务
        //IntentService自动创建子线程运行，自动停止服务
//        Intent startIntent = new Intent(this, MyIntentService.class);
//        startService(startIntent);//开启服务
    }

    public void onClick_Stop(View view) {
        Intent stopIntent = new Intent(this, MyService.class);
        stopService(stopIntent);//停止服务
    }

    public void onClick_BindServer(View view) {
        Intent bindIntent = new Intent(this, MyService.class);
        bindService(bindIntent, connection, BIND_AUTO_CREATE);
    }

    public void onClick_UnBindServer(View view) {
        Intent bindIntent = new Intent(this, MyService.class);
        unbindService(connection);
    }

    public void onClick_Front(View view) {
        Intent startIntent = new Intent(this, FrontService.class);
        startService(startIntent);//开启服务
    }
}
