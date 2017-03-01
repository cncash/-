package com.yangxbfuj.pathologicalalarm.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yangxb on 2017/3/1.
 */

public class AlarmSQLiteHelper extends SQLiteOpenHelper {

    private static final String DBNAME = "pathologicalarm.db";
    private static final int DB_VERSION = 1;

    public AlarmSQLiteHelper(Context context){
        super(context,DBNAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AlarmTableInfo.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
