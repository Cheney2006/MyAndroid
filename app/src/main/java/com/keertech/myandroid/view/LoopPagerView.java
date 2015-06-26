package com.keertech.myandroid.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.keertech.myandroid.R;
import com.yftools.HttpUtil;
import com.yftools.ViewUtil;
import com.yftools.exception.HttpException;
import com.yftools.http.RequestParams;
import com.yftools.http.ResponseInfo;
import com.yftools.http.callback.RequestCallBack;
import com.yftools.json.Json;
import com.yftools.util.AndroidUtil;
import com.yftools.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * *****************************************
 * Description ：循环滚动的广告
 * Created by cy on 14-3-20.
 * *****************************************
 */
public class LoopPagerView extends LinearLayout {

    private final static int INTERVAL_TIME = 3000;

    @ViewInject(R.id.viewPager)
    private ViewPager viewPager;
    @ViewInject(R.id.dot_layout)
    private LinearLayout dot_layout;

    private AtomicInteger what = new AtomicInteger(0);
    private Handler mHandler = new Handler();
    private int total;

    private Runnable mRunnable = new Runnable() {
        public void run() {
            what.incrementAndGet();
            if (what.get() > Integer.MAX_VALUE - 1) {
                what.getAndAdd(-Integer.MAX_VALUE);
            }
            viewPager.setCurrentItem(what.get());
            mHandler.postDelayed(this, INTERVAL_TIME);
        }
    };

    public LoopPagerView(Context context) {
        super(context);
        initView();
    }

    public LoopPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View convertView = inflater.inflate(R.layout.view_loop_pager, this);
        ViewUtil.inject(this, convertView);
        viewPager.setOnPageChangeListener(new PageChangeListener());
        viewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        mHandler.removeCallbacks(mRunnable);
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler.postDelayed(mRunnable, INTERVAL_TIME);
                        break;
                }
                return false;
            }
        });
    }


    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int state) {
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            what.getAndSet(position);
            for (int i = 0, len = dot_layout.getChildCount(); i < len; i++) {
                dot_layout.getChildAt(i).setEnabled(false);
            }
            if (dot_layout.getChildAt(position % total) != null) {//清除后，按home键出现些情况
                dot_layout.getChildAt(position % total).setEnabled(true);
            }
        }

    }

    public void setPagerAdapter(PagerAdapter adapter, int total) {
        this.total = total;
        this.viewPager.setAdapter(adapter);
        dot_layout.removeAllViews();
        ImageView imageView;
        for (int i = 0, len = total; i < len; i++) {
            //初始化dot
            imageView = new ImageView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, AndroidUtil.dip2px(getContext(), 4), 0);
            imageView.setLayoutParams(params);
            imageView.setImageResource(R.drawable.ad_dot);
            imageView.setEnabled(false);
            dot_layout.addView(imageView);
        }
        int index = total * 100;
        viewPager.setCurrentItem(index);
        what.set(index);
    }

    public void startLoop() {
        if (mHandler != null) {
            mHandler.postDelayed(mRunnable, INTERVAL_TIME);
        }
    }

    public void stopLoop() {
        if (mHandler != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    public ViewPager getViewPager() {
        return viewPager;
    }


}
