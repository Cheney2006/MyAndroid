package com.keertech.myandroid.activity.base;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;

/**
 * *****************************************
 * Description ：带进度条的ActionBar
 * Created by cy on 2014/10/20.
 * *****************************************
 */
public abstract class AbstractProgressBarBelowActivity_4 extends AbstractActivity {

    protected boolean indeterminateProgress = true;
    private ProgressBar mLoadingProgressBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 20); //Use dp resources
        mLoadingProgressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        mLoadingProgressBar.setIndeterminate(true);
        mLoadingProgressBar.setLayoutParams(lp);

        final ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
        decorView.addView(mLoadingProgressBar);
        final ViewTreeObserver vto = decorView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            View contentView = decorView.findViewById(android.R.id.content);

            @Override
            public void onGlobalLayout() {
                mLoadingProgressBar.setY(contentView.getY() - 10);
                ViewTreeObserver observer = mLoadingProgressBar.getViewTreeObserver();
                observer.removeOnGlobalLayoutListener(this);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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

    protected void showProgressBar(){
        if (indeterminateProgress) {
            //设置actionbar上面显示进度条，true表示显示，如果是false表示不显示
            setSupportProgressBarIndeterminateVisibility(true);
        } else {
            setSupportProgressBarVisibility(true);
        }
    }

    public void updateProgressValue(int value) {
        // Valid ranges are from 0 to 10000 (both inclusive).
        // If 10000 is given, the progress bar will be completely filled and will fade out.
        setSupportProgress(value);
    }

    protected void hideProgressBar(){
        if (indeterminateProgress) {
            //设置actionbar上面显示进度条，true表示显示，如果是false表示不显示
            setSupportProgressBarIndeterminateVisibility(false);
        } else {
            setSupportProgressBarVisibility(false);
        }
    }

}
