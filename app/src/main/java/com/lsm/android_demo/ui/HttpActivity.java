package com.lsm.android_demo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lsm.android_demo.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        textView = findViewById(R.id.textview_http_id);

    }

    public void onClick_SendHttp(View view) {
        Log.d("HttpActivity", "onClick_SendHttp: ");
//        SendRequestByHttpURLConnection();
        SendRequestByOKHttp();
    }

    private void SendRequestByOKHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://www.baidu.com")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String strData = response.body().string();
                    showResponse(strData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void SendRequestByHttpURLConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection con = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://www.baidu.com");
                    con = (HttpURLConnection)url.openConnection();
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(8000);
                    con.setReadTimeout(8000);
                    InputStream in = con.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ( (line = reader.readLine())  != null ){
                        response.append(line);
                    }
                    Log.d("HttpActivity", "show resp ");
                    showResponse(response.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    try{
                        if (reader != null){
                            reader.close();
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    if (con != null){
                        con.disconnect();
                    }
                }

            }
        }).start();
    }

    private void showResponse(final String strResp) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(strResp);
            }
        });
    }
}
