package com.lsm.android_demo.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lsm.android_demo.R;
import com.lsm.android_demo.ui.fragment.OtherRightFragment;
import com.lsm.android_demo.ui.fragment.RightFragment;

public class FragmentActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        Button button = findViewById(R.id.left_button);
        replaceFragment(new RightFragment());
        button.setOnClickListener(this);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.right_other, fragment);
        transaction.addToBackStack(null);//返回上一个碎片
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_button:
                replaceFragment(new OtherRightFragment());
                break;
            default:
                break;
        }
    }
}
