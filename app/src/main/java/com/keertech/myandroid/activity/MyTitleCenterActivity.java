package com.keertech.myandroid.activity;

import android.os.Bundle;
import android.view.Menu;

import com.keertech.myandroid.R;
import com.keertech.myandroid.activity.base.AbstractTitleCenterActivity;


/**
 * *****************************************
 * Description ：
 * Created by cy on 2014/10/24.
 * *****************************************
 */
public class MyTitleCenterActivity extends AbstractTitleCenterActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_title_center);
        setTitle("标题居中");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }
}
