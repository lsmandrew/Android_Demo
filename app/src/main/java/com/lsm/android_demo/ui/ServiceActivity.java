package com.lsm.android_demo.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lsm.android_demo.R;
import com.lsm.android_demo.server.MyService;

public class ServiceActivity extends AppCompatActivity {

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
        Intent startIntent = new Intent(this, MyService.class);
        startService(startIntent);//开启服务
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
}
