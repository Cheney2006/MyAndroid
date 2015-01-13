package com.keertech.myandroid.activity.base;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.keertech.myandroid.R;


/**
 * *****************************************
 * Description ：带文本居中的ActionBar
 * Created by cy on 2014/10/20.
 * *****************************************
 */
public abstract class AbstractTitleCenterActivity extends AbstractActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);//不显示图标
        getSupportActionBar().setTitle("返回");
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent_bg));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private TextView textView;

    @Override
    public void setContentView(View view) {
        init().addView(view);
    }

    @Override
    public void setContentView(int layoutResID) {
        getLayoutInflater().inflate(layoutResID, init(), true);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        init().addView(view, params);
    }

    private ViewGroup init() {
        super.setContentView(R.layout.abstract_title_center);
        textView = (TextView) findViewById(R.id.textView);
        return (ViewGroup) findViewById(R.id.content_fl);
    }


    protected void setTitle(String title) {
        if (textView != null) {
            textView.setText(title);
        }
    }

}
