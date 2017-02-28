package com.yangxbfuj.pathologicalalarm.model;

import android.net.Uri;

import com.yangxbfuj.pathologicalalarm.bean.AlarmBean;

import java.util.List;

/**
 * Created by yangxb on 2017/2/28.
 */

public interface IAlarmModel {

    void setEnable(boolean enable);

    void setAlarmTime(long alarmTime);

    void setRepeat(boolean repeat);

    void setWeekInfo(boolean[] weekInfo);

    void setResouce(Uri resource);

    void saveAnAlarm();

    List<AlarmBean> getAllAlarms();
}
