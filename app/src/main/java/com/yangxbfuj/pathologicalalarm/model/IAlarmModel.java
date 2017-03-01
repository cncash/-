package com.yangxbfuj.pathologicalalarm.model;

import android.net.Uri;

import com.yangxbfuj.pathologicalalarm.bean.AlarmBean;

import java.util.List;

/**
 * Created by yangxb on 2017/2/28.
 */

public interface IAlarmModel {

    void saveAnAlarm(AlarmBean alarm);

    List<AlarmBean> getAllAlarms();

}
