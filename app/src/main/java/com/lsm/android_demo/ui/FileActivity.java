package com.lsm.android_demo.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.lsm.android_demo.R;
import com.lsm.android_demo.model.Album;
import com.lsm.android_demo.model.Song;

import org.litepal.LitePal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
    }

    public void onClick_Save(View view) {
        String data = "Hello Android！";
        FileOutputStream out = null;
        BufferedWriter writer = null;
        //默认路径=/data/data/<packagename>/files/
        try {
            out = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            try {
                if (writer != null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void onClick_Read(View view) throws IOException {
        StringBuilder content = new StringBuilder();
        FileInputStream in = null;
        BufferedReader reader = null;

        try {
            in = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while( (line = reader.readLine()) != null) {
                content.append(line);
            }
            Log.d("FileActivity", "content: " + content);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (reader != null){
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * SharedPreferences
     * 使用键值对存储
     * 默认的存储位置=/data/data/<packname>/shared_prefs/
     */
    public void onClick_SPRead(View view) {
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        String name = pref.getString("name", "");
        int age = pref.getInt("age", 0);
        Boolean married = pref.getBoolean("married", false);
        Log.d("FileActivity", "name= " + name);
        Log.d("FileActivity", "age= " + age);
        Log.d("FileActivity", "married= " + (married? "yes":"no"));
    }


    public void onClick_SPWrite(View view) {
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putString("name", "andrew");
        editor.putInt("age", 28);
        editor.putBoolean("married", false);
        editor.apply();
    }

    public void onClick_CreateDB(View view) {
        MySQLiteHelper db_helper = new MySQLiteHelper(this, "test.db", null, 1);
        db_helper.getWritableDatabase();
        Log.d("FileActivity", "onClick_CreateDB: ");
    }

    public void onClick_UpdateDB(View view) {
        ContentValues values = new ContentValues();
        MySQLiteHelper db_helper = new MySQLiteHelper(this, "test.db", null, 1);
        SQLiteDatabase db = db_helper.getWritableDatabase();
        values.put("price", 90.0);
        db.update("book", values, "author = ?", new String[]{"andrew"});

    }

    public void onClick_QueryDB(View view) {
        MySQLiteHelper db_helper = new MySQLiteHelper(this, "test.db", null, 1);
        SQLiteDatabase db = db_helper.getWritableDatabase();
        Cursor cursor = db.query("book", null,  null, null,
                null ,null, null, null);
        if (cursor.moveToFirst()){
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                Log.d("FileActivity", "book name: " + name);
                Log.d("FileActivity", "book price: " + price);
                Log.d("FileActivity", "book author: " + author);
            }while (cursor.moveToNext());
            cursor.close();
        }
    }

    public void onClick_DelDB(View view) {
        MySQLiteHelper db_helper = new MySQLiteHelper(this, "test.db", null, 1);
        SQLiteDatabase db = db_helper.getWritableDatabase();
        db.delete("book", "author = ?", new String[]{"lsm"});
    }

    public void onClick_AddDB(View view) {
        ContentValues values = new ContentValues();
        MySQLiteHelper db_helper = new MySQLiteHelper(this, "test.db", null, 1);
        SQLiteDatabase db = db_helper.getWritableDatabase();
        values.put("name", "android");
        values.put("price", 100.0);
        values.put("author", "lsm");
        db.insert("Book", null, values);
        values.clear();
        values.put("name", "java");
        values.put("price", 100.0);
        values.put("author", "andrew");
        db.insert("Book", null, values);
    }

    /**
     * LitePal第三方開源庫，可以實現orm數據庫操作，不用編寫sql
     * @param view
     */
    public void onClick_LitePal(View view) {
        SQLiteDatabase db = LitePal.getDatabase();
        ///////////add///////////
//        Album album = new Album();
//        album.setName("album");
//        album.setPrice(10.99f);
//        album.save();
//        Song song1 = new Song();
//        song1.setName("song1");
//        song1.setDuration(320);
//        song1.setAlbum(album);
//        song1.save();
//        Song song2 = new Song();
//        song2.setName("song2");
//        song2.setDuration(356);
//        song2.setAlbum(album);
//        song2.save();
        //////////update///////////
        Album albumToUpdate = new Album();
        albumToUpdate.setPrice(20.99f); // raise the price
        //albumToUpdate.update(1);
        albumToUpdate.updateAll("name = ?", "album");//更新所有
        ///////////delete/////////////
        //LitePal.delete(Song.class, 1);
        //LitePal.deleteAll(Song.class, "duration > ?" , "350");
        ///////////query///////////
        //List<Song> allSongs = LitePal.findAll(Song.class);
        //List<Song> songs = LitePal.where("name like ? and duration < ?", "song%", "200").order("duration").find(Song.class);
        Song song = LitePal.find(Song.class, 1);
        if (song != null) {
            String name = song.getName();
            int duration = song.getDuration();
            Album album = song.getAlbum();
            Log.d("LitePal", "name: " + name);
            Log.d("LitePal", "duration: " + duration);
            if (album != null) {
                Log.d("LitePal", "album name: " + album.getName());
                Log.d("LitePal", "album price: " + album.getPrice());
            }
        }
    }

    /**
     * SQLite
     * SQLiteOpenHelper
     * 存储的位置/data/data/<packname>/databases/
     */
    class MySQLiteHelper extends SQLiteOpenHelper{
        private static  final String CREATE_BOOK = "create table Book(" +
                "id integer primary key autoincrement," +
                "name text," +
                "price real," +
                "author text);";
        private  static final String TAG = "MySQLiteHelper";
        Context  mContext;

        public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            mContext = context;
        }


        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_BOOK);
            Log.d(TAG, "onCreate: ");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(TAG, "onUpgrade: ");

        }
    }
}
