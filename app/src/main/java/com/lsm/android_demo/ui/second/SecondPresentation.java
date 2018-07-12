package com.lsm.android_demo.ui.second;

import android.app.Presentation;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.Display;

import com.lsm.android_demo.R;

/**
 * 副屏
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
public class SecondPresentation extends Presentation {

    public SecondPresentation(Context outerContext, Display display) {
        super(outerContext, display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_display);
    }

}
