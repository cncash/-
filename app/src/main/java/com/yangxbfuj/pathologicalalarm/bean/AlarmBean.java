package com.yangxbfuj.pathologicalalarm.bean;

import android.net.Uri;

/**
 * Created by yangxb on 2017/2/28.
 */

public class AlarmBean {

    public static final int TRUE = 1;

    public static final int FAlSE = 0;

    // 主键 id
    private int mId;

    // 启用标志
    private int mEnable;

    // 闹铃时间
    private long mAlarmTime;

    // 闹铃是否重复
    private int mRepeat;

    // 闹铃在一周之内哪几天工作
    private int[] mWeekInfo;

    // 闹铃资源地址
    private Uri mResource;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public int isEnable() {
        return mEnable;
    }

    public void setEnable(int enable) {
        this.mEnable = enable;
    }

    public long getAlarmTime() {
        return mAlarmTime;
    }

    public void setAlarmTime(long alarmTime) {
        this.mAlarmTime = alarmTime;
    }

    public int isRepeat() {
        return mRepeat;
    }

    public void setRepeat(int repeat) {
        this.mRepeat = repeat;
    }

    public int[] getWeekInfo() {
        return mWeekInfo;
    }

    public void setWeekInfo(int[] weekInfo) {
        this.mWeekInfo = weekInfo;
    }

    public Uri getResouce() {
        return mResource;
    }

    public void setResouce(Uri resource) {
        this.mResource = resource;
    }
}
