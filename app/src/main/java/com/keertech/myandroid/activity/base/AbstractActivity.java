package com.keertech.myandroid.activity.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

/**
 * *****************************************
 * Description ：基础类，一些基础的操作（eg:全局广播，信息提示，退出等。）
 * Created by cy on 2014/10/20.
 * *****************************************
 */
public abstract class AbstractActivity extends ActionBarActivity {

    protected Context mContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = AbstractActivity.this;
        //注册全局广播
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void displayToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
