package com.lsm.android_demo.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lsm.android_demo.R;

/**
 * The type Thread activity.
 */
public class ThreadActivity extends AppCompatActivity {


    private static final int UPDATE_TEXT = 1;

    private Handler handler;
    private Button  mbtn_thread;
    private Button  mbtn_handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        mbtn_thread = (Button) findViewById(R.id.btn_Thread);
        mbtn_handler = (Button) findViewById(R.id.btn_Handler);
        handler = new MyHandler();
    }
    /*
     * java 创建线程的方法
     * 1.继承Thread重载run方法
     * 2.实现Runnable接口
     */
    public void onClick_Thread(View view) {
        //new MyThread().start();
        new Thread(new RunThread()).start();
    }

    /*
     * android 提供异步处理
     * message:消息
     * handler:发送和处理消息
     * MessageQueue:消息队列存放消息
     * Loop:MessageQueue管家
     */
    public void onClick_Handler(View view) {
        Log.d("Handler", "onClick_Handler ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("Handler", "run: ");
                Message message = new Message();
                message.what = UPDATE_TEXT;
                handler.sendMessage(message);
            }
        }).start();
    }

    /**
     * 使用AsyncTask
     * @param view
     */
    public void onClick_AsyncTask(View view) {
        new DownloadFilesTask().execute();
    }

    /**
     * 使用AsyncTask方法:
     * 继承并且重載方法
     * 定义如下：
     * public abstract class AsyncTask
     * extends Object
     * <p>
     * java.lang.Object
     * android.os.AsyncTask<Params, Progress, Result>
     * Params:传入的参数  the type of the parameters sent to the task upon execution.
     * Progress:進度的單位 the type of the progress units published during the background computation.
     * Result：返回結果的類型 the type of the result of the background computation.
     */
    class DownloadFilesTask extends AsyncTask<Void, Integer, Boolean> {

        /**
         * 在后台任务执行前运行，可以做一些初始化任务
         *
         */
        @Override
        protected void onPreExecute() {
            Log.d("DownloadFilesTask", "onPreExecute: id=" + Thread.currentThread().getId());
        }

        /**
         * 后台运行，在子线程中执行
         * @param void param
         * @return true/false
         */
        protected Boolean doInBackground(Void... param) {
            int i = 0;
            while (true){
                Log.d("DownloadFilesTask", "doInBackground: id=" + Thread.currentThread().getId());
                i+=10;
                publishProgress(i);
                if( i> 100){
                    break;
                }
            }

            return true;
        }

        /**
         * 调用publishProgress后，不久调用该方法
         * 使用主线程调用，可以更新ui控件
         * @param progress
         */
        protected void onProgressUpdate(Integer... progress) {
            Log.d("DownloadFilesTask", "onProgressUpdate= "+ progress[0] + " id=" + Thread.currentThread().getId());
        }

        /**
         * 后台任务执行完后return返回时调用
         * 主线程调用
         * @param result
         */
        protected void onPostExecute(Boolean result) {
            Log.d("DownloadFilesTask", "onPostExecute: id=" + Thread.currentThread().getId());
        }
    }

    class  RunThread implements Runnable{
        @Override
        public void run() {
            Log.d("runThread", "run: ");
            //mbtn_thread.setText("change thread");//异常ui控件只有主线程能改
        }
    }
    class MyThread extends Thread{
        @Override
        public void run() {

            Log.d("myThread", "run: id=" + this.getId());
        }
    }

    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            switch (msg.what){
                case UPDATE_TEXT:
                    mbtn_handler.setText("change thread");
                    Log.d("myhandler", "handleMessage: ");
                    break;
                default:
                    break;
            }
        }
    }

}
