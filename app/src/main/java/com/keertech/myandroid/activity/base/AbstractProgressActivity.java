package com.keertech.myandroid.activity.base;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

/**
 * *****************************************
 * Description ：带进度条的ActionBar
 * Created by cy on 2014/10/20.
 * *****************************************
 */
public abstract class AbstractProgressActivity extends AbstractActivity {

    protected boolean indeterminateProgress = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (indeterminateProgress) {
            supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        } else {
            supportRequestWindowFeature(Window.FEATURE_PROGRESS);
        }
        //这个必须要在supportRequestWindowFeature之后，不然报错。requestFeature() must be called before adding content
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (indeterminateProgress) {
            //设置actionbar上面显示进度条，true表示显示，如果是false表示不显示
            setSupportProgressBarIndeterminateVisibility(true);
        } else {
            //setSupportProgressBarIndeterminate(true);
            setSupportProgressBarVisibility(true);
        }
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
