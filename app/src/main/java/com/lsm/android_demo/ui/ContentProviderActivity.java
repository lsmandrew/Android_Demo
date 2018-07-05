package com.lsm.android_demo.ui;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.lsm.android_demo.R;


/**
 *
 */
public class ContentProviderActivity extends AppCompatActivity {
    //private Uri uri = Uri.parse("content://com.lsm.android_demo.provider/table");
    private Uri uri = Uri.parse("content://com.xx.xxx.provider/table");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);

    }

    public void onClick_Add(View view) {
//        ContentValues values = new ContentValues();
//        values.put("name", "lsm");
//        values.put("pwd", "000000");
//        getContentResolver().insert(uri, values);
    }

    public void onClick_Del(View view) {

    }

    public void onClick_Query(View view) {
//        String[] projection = {"name", "pwd"};//Which columns to return. select col1, col2
//        String[] queryArgs = null;//where提供值
//        String selection = null;//where colo
//        String orderBy = null;//order by col, col2
//        Cursor cursor = getContentResolver().query(uri, projection, selection, queryArgs, orderBy);
//        if(cursor != null) {
//            while (cursor.moveToNext()){
//                String name_value = cursor.getString(cursor.getColumnIndex("name"));
//                String pwd_value = cursor.getString(cursor.getColumnIndex("pwd"));
//                Log.d("ContentProviderActivity", "name=" + name_value + " pwd=" + pwd_value);
//            }
//            cursor.close();
//        }
    }

    public void onClick_Update(View view) {
    }

    public void onClick_ReadContact(View view) {
        readContacts();
    }

    private void readContacts(){
        Cursor cursor = null;

        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null);
            if (cursor != null){
                while (cursor.moveToNext()){
                    //contact name
                    String contact_name = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String contact_phone = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.d("ContentProviderActivity", "name=" + contact_name + " pwd=" + contact_phone);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null){
                cursor.close();
            }
        }
    }


}
