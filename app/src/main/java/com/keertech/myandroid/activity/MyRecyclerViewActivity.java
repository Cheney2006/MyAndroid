package com.keertech.myandroid.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.keertech.myandroid.R;
import com.keertech.myandroid.activity.base.AbstractBarActivity;
import com.keertech.myandroid.adapter.MyRecyclerAdapter;
import com.yftools.ViewUtil;
import com.yftools.view.annotation.ContentView;
import com.yftools.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * *****************************************
 * Description ：RecyclerView使用
 * Created by cy on 2014/10/31.
 * *****************************************
 */
@ContentView(R.layout.activity_recyclerview)
public class MyRecyclerViewActivity extends AbstractBarActivity {

    @ViewInject(R.id.mRecyclerView)
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtil.inject(this);
        getSupportActionBar().setTitle("RecyclerView例子");
        // improve performance if you know that changes in content
        // do not change the size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(mContext);
        //默认是垂直
       // mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<String> dataList = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            dataList.add("recyler item " + i);
        }
        // specify an adapter (see also next example)
        MyRecyclerAdapter mAdapter = new MyRecyclerAdapter(dataList);
        mAdapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                displayToast("选择" + position);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

}
