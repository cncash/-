package com.yangxbfuj.pathologicalalarm;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.yangxbfuj.pathologicalalarm.model.AlarmSQLiteHelper;
import com.yangxbfuj.pathologicalalarm.model.AlarmTableInfo;

public class AlarmContentProvider extends ContentProvider {

    private final String TAG = getClass().getSimpleName();

    public static final String CONTENT = "content://";
    public static final String AUTHORY = "com.yangxbfuj.alarms";

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORY;
    public static final String CONTENT_TYPE_ITEM = "vnd.android.cursor.item/vnd." + AUTHORY;

    public static final Uri ALARM_URI = Uri.parse(CONTENT + AUTHORY + "/" + AlarmTableInfo.TABLE_NAME);

    static final int ALARMS = 1;
    static final int ALARM_ITEM = 2;

    static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORY, AlarmTableInfo.TABLE_NAME, ALARMS);
        uriMatcher.addURI(AUTHORY, AlarmTableInfo.TABLE_NAME + "/#", ALARM_ITEM);
    }

    private SQLiteDatabase mSQLiteDatabase;

    public AlarmContentProvider() {
    }

    @Override
    public boolean onCreate() {
        mSQLiteDatabase = new AlarmSQLiteHelper(getContext()).getWritableDatabase();
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = mSQLiteDatabase.delete(AlarmTableInfo.TABLE_NAME, selection, selectionArgs);
        if (count >= 1) {
            // 此处向外界通知ContentProvider的数据发生了变化，由于ContentProvider的调用是跟调用者是同一个进程中
            // 所以getContext()取得了同一个对象，从而getContentResolver()也是同一个对象
            getContext().getContentResolver().notifyChange(ALARM_URI, null);
        }
        return count;
    }

    @Override
    public String getType(Uri uri) {
        Logger.d(TAG, uri.toString());
        switch (uriMatcher.match(uri)) {
            case ALARMS:
                return CONTENT_TYPE;
            //return null;
            case ALARM_ITEM:
                return CONTENT_TYPE_ITEM;
            //return null;
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Logger.d(TAG, uri.toString());
        long newId = 0;
        Uri newUri = null;
        Logger.d(TAG, "match uri is " + uriMatcher.match(uri));
        switch (uriMatcher.match(uri)) {
            case ALARMS:
                newId = mSQLiteDatabase.insert(AlarmTableInfo.TABLE_NAME, null, values);
                newUri = Uri.parse(CONTENT + AUTHORY + "/"
                        + uri.getPath() + "/" + newId);
                break;
            default:
                break;
        }
        if (newId > 0) {
            // 此处向外界通知ContentProvider的数据发生了变化，由于ContentProvider的调用是跟调用者是同一个进程中
            // 所以getContext()取得了同一个对象，从而getContentResolver()也是同一个对象
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        } else return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Logger.d(this.getClass().getSimpleName(), "query match uri is " + uriMatcher.match(uri));
        Logger.d(getClass().getSimpleName(), uri.toString());
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case ALARMS:
                cursor = mSQLiteDatabase.query(AlarmTableInfo.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case ALARM_ITEM:
                String id = uri.getPathSegments().get(1);
                cursor = mSQLiteDatabase.query(AlarmTableInfo.TABLE_NAME, projection, "id = ?",
                        new String[]{id}, null, null, sortOrder);
                break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count = mSQLiteDatabase.update(AlarmTableInfo.TABLE_NAME, values, selection, selectionArgs);
        if (count >= 1) {
            // 此处向外界通知ContentProvider的数据发生了变化，由于ContentProvider的调用是跟调用者是同一个进程中
            // 所以getContext()取得了同一个对象，从而getContentResolver()也是同一个对象
            getContext().getContentResolver().notifyChange(ALARM_URI, null);
        }
        return count;
    }
}
