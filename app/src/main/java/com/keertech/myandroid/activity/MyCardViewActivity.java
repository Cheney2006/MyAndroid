package com.keertech.myandroid.activity;

import android.os.Bundle;

import com.keertech.myandroid.R;
import com.keertech.myandroid.activity.base.AbstractBarActivity;
import com.yftools.ViewUtil;
import com.yftools.view.annotation.ContentView;

/**
 * *****************************************
 * Description ：CardView使用
 * 圆角ListView意义不是很大。点击效果还不行。
 * Created by cy on 2014/10/31.
 * *****************************************
 */
@ContentView(R.layout.activity_my_cardview)
public class MyCardViewActivity extends AbstractBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtil.inject(this);
        getSupportActionBar().setTitle("CardView使用");
    }
}
