package com.lsm.android_demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.lsm.android_demo.R;

public class SingleTopActivity extends AppCompatActivity {

    private static final String TAG = "ui.singleTopActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_top);
        Log.d(TAG, "onCreate: " + this.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: " + this.toString());
    }

    public void onClick_Create(View view) {
        Intent intent = new Intent(SingleTopActivity.this, SingleTopActivity.class);
        startActivity(intent);
    }
}
