package com.keertech.myandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.keertech.myandroid.R;
import com.keertech.myandroid.activity.base.AbstractActivity;

/**
 * Created by Administrator on 2015/6/25.
 */
public class GestureLockActivity extends AbstractActivity implements View.OnClickListener {
    private Button mBtnSetLock;
    private Button mBtnVerifyLock;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_lock);
        setUpView();
        setUpListener();
    }

    private void setUpView() {
        mBtnSetLock = (Button) findViewById(R.id.btn_set_lockpattern);
        mBtnVerifyLock = (Button) findViewById(R.id.btn_verify_lockpattern);
    }

    private void setUpListener() {
        mBtnSetLock.setOnClickListener(this);
        mBtnVerifyLock.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_set_lockpattern:
                startSetLockPattern();
                break;
            case R.id.btn_verify_lockpattern:
                startVerifyLockPattern();
                break;
            default:
                break;
        }
    }

    private void startSetLockPattern() {
        Intent intent = new Intent(this, GestureEditActivity.class);
        startActivity(intent);
    }

    private void startVerifyLockPattern() {
        Intent intent = new Intent(this, GestureVerifyActivity.class);
        startActivity(intent);
    }
}

