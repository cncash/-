package com.yangxbfuj.pathologicalalarm.presents;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

import com.yangxbfuj.pathologicalalarm.AlarmContentProvider;
import com.yangxbfuj.pathologicalalarm.IAlarmPlayer;
import com.yangxbfuj.pathologicalalarm.Logger;
import com.yangxbfuj.pathologicalalarm.bean.AlarmBean;
import com.yangxbfuj.pathologicalalarm.model.AlarmTableInfo;
import com.yangxbfuj.pathologicalalarm.model.IAlarmModel;
import com.yangxbfuj.pathologicalalarm.view.IAlarmPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangxb on 2017/3/1.
 */

public class AlarmPresent implements IAlarmModel, IAlarmPage {

    private Context mContext;
    ContentResolver mResolver;
    AlarmObserver mAlarmObserver;
    List<AlarmBean> mAlarmList;
    boolean isDataChanged = true;

    public AlarmPresent(Context context) {
        mContext = context;
        mResolver = mContext.getContentResolver();
        mAlarmObserver = new AlarmObserver(new Handler());
        registerObserver();
    }

    public void registerObserver() {
        mResolver.registerContentObserver(AlarmContentProvider.ALARM_URI, true, mAlarmObserver);
    }

    public void unregisterObserver() {
        mResolver.unregisterContentObserver(mAlarmObserver);
    }

    @Override
    public void onDataChanged() {
        if (mContext instanceof IAlarmPage) {
            ((IAlarmPage) mContext).onDataChanged();
        }
    }

    public class AlarmObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public AlarmObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            Logger.d("AlarmObserver", "onChange " + uri.toString());
            super.onChange(selfChange, uri);
            isDataChanged = true;
            onDataChanged();
        }
    }

    @Override
    public void saveAnAlarm(AlarmBean alarm) {
        ContentValues alarmValues = new ContentValues();
        alarmValues.put(AlarmTableInfo.ALARM_PATH, alarm.getResouce().toString());
        alarmValues.put(AlarmTableInfo.ALARM_TIME, alarm.getAlarmTime());
        alarmValues.put(AlarmTableInfo.ENABLE, alarm.isEnable());
        alarmValues.put(AlarmTableInfo.WEEK_INFO, String.valueOf(alarm.getWeekInfo()));

        if (alarm.getId() < 0) {
            mResolver.insert(AlarmContentProvider.ALARM_URI, alarmValues);
        } else {
            alarmValues.put(AlarmTableInfo.ID, alarm.getId());
            mResolver.update(AlarmContentProvider.ALARM_URI, alarmValues, "id = ?"
                    , new String[]{String.valueOf(alarm.getId())});
        }
    }

    @Override
    public List<AlarmBean> getAllAlarms() {
        if (mAlarmList == null) {
            mAlarmList = new ArrayList<>();
        }
        if (isDataChanged) {
            mAlarmList.clear();
            Cursor cursor = mResolver.query(AlarmContentProvider.ALARM_URI, null, null, null, null);
            if (!cursor.moveToFirst()) {
                return mAlarmList;
            }
            do {
                AlarmBean alarmBean = new AlarmBean();
                alarmBean.setId(cursor.getInt(cursor.getColumnIndex(AlarmTableInfo.ID)));
                alarmBean.setEnable(cursor.getInt(cursor.getColumnIndex(AlarmTableInfo.ENABLE)));
                alarmBean.setAlarmTime(cursor.getLong(cursor.getColumnIndex(AlarmTableInfo.ALARM_TIME)));
                alarmBean.setRepeat(cursor.getInt(cursor.getColumnIndex(AlarmTableInfo.REPEAT)));
                alarmBean.setResouce(Uri.parse(cursor.getString(cursor.getColumnIndex(AlarmTableInfo.ALARM_PATH))));
                //String weekinfo = cursor.getString(cursor.getColumnIndex(AlarmTableInfo.WEEK_INFO));
                mAlarmList.add(alarmBean);
            } while (cursor.moveToNext());
        }
        return mAlarmList;
    }
}
