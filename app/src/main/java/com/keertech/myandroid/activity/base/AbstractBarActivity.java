package com.keertech.myandroid.activity.base;

import android.os.Bundle;
import android.view.MenuItem;

/**
 * *****************************************
 * Description ：ActionBar标题栏
 * Created by cy on 2014/10/20.
 * *****************************************
 */
public abstract class AbstractBarActivity extends AbstractActivity {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

}
