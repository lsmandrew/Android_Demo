package com.lsm.android_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lsm.android_demo.ui.BroadcastActivity;
import com.lsm.android_demo.ui.ContentProviderActivity;
import com.lsm.android_demo.ui.ControlViewActivity;
import com.lsm.android_demo.ui.FileActivity;
import com.lsm.android_demo.ui.FragmentActivity;
import com.lsm.android_demo.ui.HttpActivity;
import com.lsm.android_demo.ui.LifeCycleActivity;
import com.lsm.android_demo.ui.LinearLayoutActivity;
import com.lsm.android_demo.ui.PresentationActivity;
import com.lsm.android_demo.ui.RelativeLayoutActivity;
import com.lsm.android_demo.ui.ServiceActivity;
import com.lsm.android_demo.ui.ThreadActivity;
import com.lsm.android_demo.ui.WebViewActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClick_Toast(View view) {
        //context 为应用程序上下文 duration为出现时长
//        Toast toast = Toast.makeText(this, "test toast", Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.TOP| Gravity.RIGHT, 0, 0);//change position
//        toast.show();
//        Toast.makeText(this, "test toast", Toast.LENGTH_LONG).show();
         /////Creating a Custom Toast View/////
        //Note: Do not use the public constructor for a Toast unless you are going to define the layout with setView(View).
        //If you do not have a custom layout to use, you must use makeText(Context, int, int) to create the Toast.
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_id));
        TextView text = (TextView) layout.findViewById(R.id.test_toast_id);
        text.setText("This is a custom toast");
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void onClick_Linear(View view) {
        Intent intent = new Intent(this, LinearLayoutActivity.class);
        startActivity(intent);

    }

    public void onClick_Relative(View view) {
        Intent intent = new Intent(this, RelativeLayoutActivity.class);
        startActivity(intent);
    }

    public void onClick_Menu(View view) {
        Intent intent = new Intent("com.lsm.android_demo.ui.MenuActivity");
        startActivity(intent);
    }

    public void onClick_lifecycle(View view) {
        Intent intent = new Intent(this, LifeCycleActivity.class);
        startActivity(intent);
    }


    public void onClick_Broadcast(View view) {
        Intent intent = new Intent(this, BroadcastActivity.class);
        startActivity(intent);
    }

    public void onClick_Thread(View view) {
        Intent intent = new Intent(this, ThreadActivity.class);
        startActivity(intent);
    }

    public void onClick_Service(View view) {
        Intent intent = new Intent(this, ServiceActivity.class);
        startActivity(intent);
    }

    public void onClick_Content(View view) {
        Intent intent = new Intent(this, ContentProviderActivity.class);
        startActivity(intent);
    }

    public void onClick_File(View view) {
        Intent intent = new Intent(this, FileActivity.class);
        startActivity(intent);
    }

    public void onClick_WebView(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);
    }

    public void onClick_Http(View view) {
        Intent intent = new Intent(this, HttpActivity.class);
        startActivity(intent);
    }

    public void onClick_Fragment(View view) {
        Intent intent = new Intent(this, FragmentActivity.class);
        startActivity(intent);
    }

    public void onClick_ControlView(View view) {
        Intent intent = new Intent(this, ControlViewActivity.class);
        startActivity(intent);
    }

    public void onClick_Second(View view) {
        //int disp_count = HardwareUtil.getScreenCount(this);
//        Display[] displays = HardwareUtil.getScreen(this);
//        SecondPresentation secondPresentation =new SecondPresentation (this, displays[1]);//displays[1]是副屏
//        secondPresentation.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
//        secondPresentation.show();
        Intent intent = new Intent(this, PresentationActivity.class);
        startActivity(intent);
    }
}
