package com.yangxbfuj.pathologicalalarm.model;

/**
 * Created by yangxb on 2017/3/1.
 */

public class AlarmTableInfo {

    public final static String TABLE_NAME = "alarms";
    public final static String ID = "id";
    public final static String ENABLE = "enable";
    public final static String ALARM_TIME = "alarm_time";
    public final static String REPEAT = "repeat";
    public final static String WEEK_INFO = "week_info";
    public final static String ALARM_PATH = "alarm_path";

    public final static String CREATE_TABLE = "create table " + TABLE_NAME +"("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ENABLE + " INTEGER ,"
            + ALARM_TIME + " INTEGER ,"
            + REPEAT + " INTEGER ,"
            + WEEK_INFO + " INTEGER ,"
            + ALARM_PATH + " TEXT "
            + " )";
}
