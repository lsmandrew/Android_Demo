package com.lsm.android_demo.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.lsm.android_demo.R;

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
