package com.lsm.android_demo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lsm.android_demo.R;
import com.lsm.android_demo.ui.adapter.PeopleAdapter;

/**
 * 控件的使用
 */
public class ControlViewActivity extends AppCompatActivity {
    private String[] data={"Tom", "Marry", "Andrew"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_view);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                this, android.R.layout.simple_list_item_1, data);
//        ListView listView = findViewById(R.id.list_view_id);
//        listView.setAdapter(adapter);
        PeopleAdapter adapter = new PeopleAdapter(this, R.layout.list_item, data);
        ListView listView = findViewById(R.id.list_view_id);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ControlViewActivity.this, "id="+id, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
