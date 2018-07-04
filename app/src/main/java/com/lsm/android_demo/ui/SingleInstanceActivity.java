package com.lsm.android_demo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lsm.android_demo.R;

public class SingleInstanceActivity extends AppCompatActivity {

    private static final String TAG = "ui.SingleInstanceAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_instance);
        Log.d(TAG, "onCreate: task id:" + this.getTaskId());
    }
}
