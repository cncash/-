package com.yangxbfuj.pathologicalalarm;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.yangxbfuj.pathologicalalarm.view.AlarmManagerActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * Created by yangxb on 2017/3/1.
 */
@RunWith(AndroidJUnit4.class)
public class AlarmManagerActivityTest {

    @Rule
    public ActivityTestRule<AlarmManagerActivity> mActivityRule = new ActivityTestRule<>(
            AlarmManagerActivity.class);

    @Before
    public void initValidString() {

    }

}
