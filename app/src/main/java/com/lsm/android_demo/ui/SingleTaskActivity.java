package com.lsm.android_demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.lsm.android_demo.R;

public class SingleTaskActivity extends AppCompatActivity {

    private static final String TAG = "ui.SingleTaskActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: " + this.toString());
    }

    public void onClick_Start(View view) {
        Intent intent = new Intent(SingleTaskActivity.this, LifeCycleActivity.class);
        startActivity(intent);
    }
}
