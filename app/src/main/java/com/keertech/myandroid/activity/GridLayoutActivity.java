package com.keertech.myandroid.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayout;

import com.keertech.myandroid.R;
import com.keertech.myandroid.activity.base.AbstractBarActivity;
import com.yftools.ViewUtil;
import com.yftools.view.annotation.ContentView;
import com.yftools.view.annotation.ViewInject;

/**
 * *****************************************
 * Description ：
 * Created by cy on 2015/2/13.
 * *****************************************
 */
@ContentView(R.layout.activity_grid_layout)
public class GridLayoutActivity extends AbstractBarActivity {

    @ViewInject(R.id.gridLayout)
    private GridLayout gridLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtil.inject(this);
        gridLayout.setOrientation(GridLayout.HORIZONTAL);
    }
}
