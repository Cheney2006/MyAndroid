package com.keertech.myandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keertech.myandroid.R;
import com.keertech.myandroid.activity.base.AbstractBarActivity;
import com.keertech.myandroid.utils.AnimationUtil;
import com.keertech.myandroid.view.scrollview.MyScrollView;
import com.keertech.myandroid.view.scrollview.OnScrollChangedListener;
import com.yftools.LogUtil;
import com.yftools.ViewUtil;
import com.yftools.view.annotation.ViewInject;

/**
 * *****************************************
 * Description ：模仿赶集网引导页。上下滑动
 * Created by cy on 2015/2/2.
 * *****************************************
 */
public class ScrollerViewActivity extends AbstractBarActivity implements OnScrollChangedListener {
    @ViewInject(R.id.ll_anim)
    private LinearLayout mLLAnim;
    private MyScrollView mSVmain;
    private int mScrollViewHeight;
    private int mStartAnimateTop;
    private boolean hasStart = false;
    @ViewInject(R.id.tvInNew)
    private TextView tvInNew;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller_view);
        ViewUtil.inject(this);
        initView();
        setView();
    }

    private void initView() {
        mSVmain = (MyScrollView) findViewById(R.id.main_sv);
        tvInNew.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, MyActivity.class));
                AnimationUtil.finishActivityAnimation(ScrollerViewActivity.this);
            }
        });
    }

    private void setView() {
        mSVmain.setOnScrollChangedListener(this);
        mLLAnim.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mScrollViewHeight = mSVmain.getHeight();
        mStartAnimateTop = mScrollViewHeight / 5 * 4;//开始动画时的高度，
        LogUtil.d("mScrollViewHeight=" + mScrollViewHeight + ",mStartAnimateTop=" + mStartAnimateTop);
    }

    @Override
    public void onScrollChanged(int top, int oldTop) {
        int animTop = mLLAnim.getTop() - top;// mLLAnim距屏幕的高度
        LogUtil.d("animTop=" + animTop + ",mLLAnim.getTop()=" + mLLAnim.getTop() + ",top=" + top);//mStartAnimateTop=608
        if (top > oldTop) {// 向下滑动
            if (animTop < mStartAnimateTop && !hasStart) {
                Animation anim = AnimationUtils.loadAnimation(this, R.anim.show);
                mLLAnim.setVisibility(View.VISIBLE);
                mLLAnim.startAnimation(anim);
                hasStart = true;
            }
        } else {// 向上滑动
            if (animTop > mStartAnimateTop && hasStart) {
                Animation anim = AnimationUtils.loadAnimation(this,R.anim.close);
                mLLAnim.setVisibility(View.INVISIBLE);
                mLLAnim.startAnimation(anim);
                hasStart = false;
            }
        }
    }

}
