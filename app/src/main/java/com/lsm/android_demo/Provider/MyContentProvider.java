package com.lsm.android_demo.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * 内容提供者
 * 为其他应用提供外部接口，方便其他应用读取数据
 * 其他应用使用ContentResolver来读取
 */
public class MyContentProvider extends ContentProvider {

    private static final String  TAG = "MyContentProvider";
    public  static final int TABLE_DIR = 0;
    public  static final int TABLE_ITEM = 1;
    public  static final String AUTHORITY = "com.lsm.android.provider";
    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "table", TABLE_DIR);
        uriMatcher.addURI(AUTHORITY, "table/#", TABLE_ITEM);

    }
    public MyContentProvider() {
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        //throw new UnsupportedOperationException("Not yet implemented");
        Log.d(TAG, "delete: ");
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        //throw new UnsupportedOperationException("Not yet implemented");
        Log.d(TAG, "getType: ");
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        //throw new UnsupportedOperationException("Not yet implemented");
        Log.d(TAG, "insert: ");
        return null;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        Log.d(TAG, "onCreate: ");
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        //throw new UnsupportedOperationException("Not yet implemented");
        Log.d(TAG, "query: ");
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        //throw new UnsupportedOperationException("Not yet implemented");
        Log.d(TAG, "update: ");
        return 0;
    }
}
