package com.lsm.android_demo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/*
 * 查看创建了那个activity,用于知道当前使用那个activity
 */
public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "base.BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: " + getClass().getSimpleName());
    }
}
