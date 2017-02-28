package com.yangxbfuj.pathologicalalarm;

import android.util.Log;

/**
 * Created by yangxb on 2017/2/27.
 */

public class Logger {

    public static int LOG_LEVEL  = LogLevel.DEBUG.mValue;

    public static void v(String tag,String msg){
        if(LOG_LEVEL <= LogLevel.VERBOSE.mValue){
            Log.v(tag,msg);
        }
    }

    public static void v(String tag,String msg,Throwable tr){
        if(LOG_LEVEL <= LogLevel.VERBOSE.mValue) {
            Log.v(tag, msg, tr);
        }
    }

    public static void d(String tag,String msg){
        if(LOG_LEVEL <= LogLevel.DEBUG.mValue){
            Log.d(tag,msg);
        }
    }

    public static void d(String tag,String msg,Throwable tr){
        if(LOG_LEVEL <= LogLevel.DEBUG.mValue) {
            Log.d(tag, msg, tr);
        }
    }

    public static void i(String tag,String msg){
        if(LOG_LEVEL <= LogLevel.INFO.mValue){
            Log.i(tag,msg);
        }
    }

    public static void i(String tag,String msg,Throwable tr){
        if(LOG_LEVEL <= LogLevel.INFO.mValue) {
            Log.i(tag, msg, tr);
        }
    }

    public static void w(String tag,String msg){
        if(LOG_LEVEL <= LogLevel.WARN.mValue){
            Log.w(tag,msg);
        }
    }

    public static void w(String tag,String msg,Throwable tr){
        if(LOG_LEVEL <= LogLevel.WARN.mValue) {
            Log.w(tag, msg, tr);
        }
    }

    public static void e(String tag,String msg){
        if(LOG_LEVEL <= LogLevel.ERROR.mValue){
            Log.e(tag,msg);
        }
    }

    public static void e(String tag,String msg,Throwable tr){
        if(LOG_LEVEL <= LogLevel.ERROR.mValue) {
            Log.e(tag, msg, tr);
        }
    }

    private enum LogLevel{
        VERBOSE(0),
        INFO(1),
        DEBUG(2),
        WARN(3),
        ERROR(4),
        ASSERT(5),
        NOTHING(6);

        int mValue;

        LogLevel(int value) {
            this.mValue = value;
        }

    }

}
