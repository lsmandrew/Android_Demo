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
import com.lsm.android_demo.ui.LifeCycleActivity;
import com.lsm.android_demo.ui.LinearLayoutActivity;
import com.lsm.android_demo.ui.RelativeLayoutActivity;

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
}
