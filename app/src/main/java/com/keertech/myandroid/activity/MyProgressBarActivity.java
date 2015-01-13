package com.keertech.myandroid.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import com.keertech.myandroid.R;
import com.keertech.myandroid.activity.base.AbstractProgressActivity;


/**
 * *****************************************
 * Description ：
 * Created by cy on 2014/10/24.
 * *****************************************
 */
public class MyProgressBarActivity extends AbstractProgressActivity {

    private int mProgress = 0;
    Handler mHandler = new Handler();
    Runnable mProgressRunner = new Runnable() {
        @Override
        public void run() {
            updateProgressValue(mProgress * 100);
            if (mProgress < 100) {
                mHandler.postDelayed(mProgressRunner, 500);
            }else{
                hideProgressBar();
            }
            mProgress += 2;
        }
    };

    protected void startProgress() {
        mProgress = 0;
        mHandler.post(mProgressRunner);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        indeterminateProgress = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_title_center);
        getSupportActionBar().setTitle("进度条");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            showProgressBar();
            startProgress();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
