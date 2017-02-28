package com.yangxbfuj.pathologicalalarm.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.yangxbfuj.pathologicalalarm.R;

public class AlarmManagerActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private RecyclerView mAlarmListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView(){
        setContentView(R.layout.activity_alarm_manager);
        mToolbar = (Toolbar) findViewById(R.id.alarm_manage_toolbar);
        mAlarmListView = (RecyclerView) findViewById(R.id.alarm_manage_list);
        setSupportActionBar(mToolbar);
    }

    /**
     * 增加一个闹铃
     */
    private void addAnAlarm(){

    }

    /**
     * 删除一个闹铃
     */
    private void deleteAnAlarm(){

    }

    /**
     * 更新一个闹铃
     */
    private void updataAnAlarm(){

    }

    /**
     * 查寻一个闹铃
     */
    private void queryAnAlarm(){

    }

    /**
     * 查询所有闹铃
     */
    private void queryAllAlarms(){

    }
}
