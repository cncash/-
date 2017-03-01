package com.yangxbfuj.pathologicalalarm.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.yangxbfuj.pathologicalalarm.Logger;
import com.yangxbfuj.pathologicalalarm.R;
import com.yangxbfuj.pathologicalalarm.bean.AlarmBean;
import com.yangxbfuj.pathologicalalarm.model.IAlarmModel;
import com.yangxbfuj.pathologicalalarm.presents.AlarmPresent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class AlarmManagerActivity extends AppCompatActivity implements IAlarmPage {

    private final String TAG = getClass().getSimpleName();

    private Toolbar mToolbar;

    private RecyclerView mAlarmListView;

    private FloatingActionButton mAddBtn;

    private IAlarmModel mAlarmModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initViewInteraction();
        bindData();
    }


    /**
     * 初始化 View 引用
     */
    private void initView() {
        setContentView(R.layout.activity_alarm_manager);
        mToolbar = (Toolbar) findViewById(R.id.alarm_manage_toolbar);
        mAlarmListView = (RecyclerView) findViewById(R.id.alarm_manage_list);
        mAddBtn = (FloatingActionButton) findViewById(R.id.alarm_manage_add_btn);
        setSupportActionBar(mToolbar);

        //TODO for test
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int oneDay = 24 * 60 * 60 * 1000;
                AlarmBean alarmBean = new AlarmBean();
                alarmBean.setResouce(Uri.EMPTY);
                alarmBean.setAlarmTime(new Date().getTime() + random.nextInt() % oneDay);
                alarmBean.setEnable(random.nextInt() % 2);
                alarmBean.setId(-1);
                mAlarmModel.saveAnAlarm(alarmBean);
            }
        });
    }

    /**
     * 初始化 View 的交互
     */
    private void initViewInteraction() {
        setAlarmListScrollingEffect();
    }

    /**
     * 设置当闹钟列表滚动时的影响
     */
    private void setAlarmListScrollingEffect() {
        // 设置 闹钟列表 布局方式
        mAlarmListView.setLayoutManager(new LinearLayoutManager(this));
        mAlarmListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                // 正在滚动，隐藏掉添加键
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING
                        || newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    mAddBtn.hide();
                } else {  // 滚动完成，显示添加键
                    mAddBtn.show();
                }
            }
        });
        mAlarmListView.addItemDecoration(new AlarmItemDecoration(this));
    }

    AlarmItemViewAdapter mAlarmListAdapter;

    private void bindData() {
        // 获得数据
        initPresent();

        mAlarmListAdapter = new AlarmItemViewAdapter(mAlarmModel.getAllAlarms());
        mAlarmListView.setAdapter(mAlarmListAdapter);
    }

    private void initPresent() {
        mAlarmModel = new AlarmPresent(this);
//        List<AlarmBean> alarmBeanList = new ArrayList<>();
//        Random random = new Random();
//        int oneDay = 24 * 60 * 60 * 1000;
//        for(int i = 1;i <= 20 ;i++){
//            AlarmBean alarmBean = new AlarmBean();
//            alarmBean.setAlarmTime(new Date().getTime() + random.nextInt() % oneDay);
//            alarmBean.setEnable(random.nextInt() % 2);
//            alarmBean.setId(i);
//            alarmBeanList.add(alarmBean);
//        }
//        AlarmItemViewAdapter adapter = new AlarmItemViewAdapter(alarmBeanList);
//        mAlarmListView.setAdapter(adapter);
    }

    @Override
    public void onDataChanged() {
        Logger.d(TAG,"onDataChanged");
        mAlarmListAdapter.mAlarmList = mAlarmModel.getAllAlarms();
        mAlarmListAdapter.notifyDataSetChanged();
        if(mAlarmListAdapter.mAlarmList.size()-1 <= 0){
            return;
        }
        mAlarmListView.smoothScrollToPosition(mAlarmListAdapter.mAlarmList.size()-1);
    }

    private static class AlarmItemViewAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private final static SimpleDateFormat sFormatter = new SimpleDateFormat("HH:mm");

        private List<AlarmBean> mAlarmList;

        AlarmItemViewAdapter(List<AlarmBean> alarmList) {
            mAlarmList = alarmList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public int getItemCount() {
            return mAlarmList.size();
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            AlarmBean currentAlarmBean = mAlarmList.get(position);
            holder.mTextClock.setText(sFormatter.format(new Date(currentAlarmBean.getAlarmTime())));
            holder.mAlarmInfo.setText("暂定");
            holder.mAlarmSwitch.setChecked(currentAlarmBean.isEnable() == AlarmBean.TRUE);
        }
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder {

        private int mDefaultColor;

        private int mHighLightColor;

        // 时间显示
        TextView mTextClock;

        // 其它信息显示
        TextView mAlarmInfo;

        // 启用开关
        Switch mAlarmSwitch;

        public MyViewHolder(View view) {
            super(view);
            mTextClock = (TextView) view.findViewById(R.id.item_alarm_textClock);
            mAlarmInfo = (TextView) view.findViewById(R.id.item_alarm_info);
            mAlarmSwitch = (Switch) view.findViewById(R.id.item_alarm_switch);

            mDefaultColor = ContextCompat.getColor(view.getContext(), android.R.color.darker_gray);
            mHighLightColor = ContextCompat.getColor(view.getContext(), android.R.color.white);

            mAlarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        mTextClock.setTextColor(mHighLightColor);
                    } else {
                        mTextClock.setTextColor(mDefaultColor);
                    }
                }
            });
        }

    }

    private static class AlarmItemDecoration extends RecyclerView.ItemDecoration {

        Paint mPaint;

        AlarmItemDecoration(Context context) {
            mPaint = new Paint();
            mPaint.setColor(ContextCompat.getColor(context, android.R.color.widget_edittext_dark));
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);

                //获得child的布局信息
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + 2;

                float offset = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 20,
                        parent.getContext().getResources().getDisplayMetrics());

                c.drawLine(left + offset, top, right - offset, bottom, mPaint);
            }
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(0, 0, 0, 2);
        }
    }
}
