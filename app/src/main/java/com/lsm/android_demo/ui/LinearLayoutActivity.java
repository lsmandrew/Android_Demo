package com.lsm.android_demo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lsm.android_demo.R;

public class LinearLayoutActivity extends AppCompatActivity {
    private static final String TAG = "AppCompatActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_layout);
        Button button1 = (Button)findViewById(R.id.btn1_id);
        Button button2 = (Button)findViewById(R.id.btn2_id);
        Button button3 = (Button)findViewById(R.id.btn3_id);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: niming");
            }
        });

        button2.setOnClickListener(new  BttonListener());
        button3.setOnClickListener(new  BttonListener());

    }

    private class BttonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.btn1_id:
                    Log.d(TAG, "onClick: button1");
                    break;
                case R.id.btn2_id:
                    Log.d(TAG, "onClick: button2");
                    break;
                case R.id.btn3_id:
                    Log.d(TAG, "onClick: button3");
                    break;

            }

        }
    }


}
