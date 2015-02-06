package com.keertech.myandroid.activity;

import android.os.Bundle;
import android.view.View;

import com.keertech.myandroid.R;
import com.keertech.myandroid.activity.base.AbstractBarActivity;
import com.keertech.myandroid.view.RoundProgressBar;
import com.yftools.ViewUtil;
import com.yftools.view.annotation.ContentView;
import com.yftools.view.annotation.ViewInject;
import com.yftools.view.annotation.event.OnClick;

/**
 * *****************************************
 * Description ：圆形进度条
 * Created by cy on 2015/2/6.
 * *****************************************
 */
@ContentView(R.layout.activity_my_round_progress_bar)
public class MyRoundProgressBarActivity extends AbstractBarActivity{

    @ViewInject(R.id.roundProgressBar1)
    private RoundProgressBar mRoundProgressBar1;
    @ViewInject(R.id.roundProgressBar2)
    private RoundProgressBar mRoundProgressBar2 ;
    @ViewInject(R.id.roundProgressBar3)
    private RoundProgressBar mRoundProgressBar3;
    @ViewInject(R.id.roundProgressBar4)
    private RoundProgressBar mRoundProgressBar4;
    @ViewInject(R.id.roundProgressBar5)
    private RoundProgressBar mRoundProgressBar5;

    private int progress = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtil.inject(this);
    }

    @OnClick(R.id.start_btn)
    public void onClick(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progress <= 100){
                    progress += 3;
                    System.out.println(progress);
                    mRoundProgressBar1.setProgress(progress);
                    mRoundProgressBar2.setProgress(progress);
                    mRoundProgressBar3.setProgress(progress);
                    mRoundProgressBar4.setProgress(progress);
                    mRoundProgressBar5.setProgress(progress);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }
}
