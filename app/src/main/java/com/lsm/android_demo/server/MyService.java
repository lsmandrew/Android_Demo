package com.lsm.android_demo.server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/*
 * 自定义服务
 */
public class MyService extends Service {

    private DownloadBinder mBinder = new DownloadBinder();

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyService", "onCreate: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyService", "onDestroy: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService", "onStartCommand exe: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        //stopSelf();
        return mBinder;
    }

    public class DownloadBinder extends Binder{

        public void StartDownload(){
            Log.d("DownloadBinder", "StartDownload: ");
        }

        public int getProgress(){
            Log.d("DownloadBinder", "getProgress: ");
            return 0;
        }



    }
}
