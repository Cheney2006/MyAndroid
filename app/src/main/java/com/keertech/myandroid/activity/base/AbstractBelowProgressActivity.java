package com.keertech.myandroid.activity.base;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.keertech.myandroid.R;


/**
 * *****************************************
 * Description ：带水平进度条的ActionBar,进度条在ActionBar下面
 * Created by cy on 2014/10/20.
 * *****************************************
 */
public abstract class AbstractBelowProgressActivity extends AbstractActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);//不显示图标
        hideProgressBar();
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

    private ProgressBar mProgressBar;

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
        super.setContentView(R.layout.abstract_progressbar_below);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        //mProgressBar.setIndeterminate(true);
        mProgressBar.setMax(10000);
        return (ViewGroup) findViewById(R.id.content_fl);
    }

    protected void showProgressBar() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    protected void updateProgressValue(int value) {
        if (mProgressBar != null) {
            mProgressBar.setProgress(value);
        }
    }

    protected void hideProgressBar() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

}
