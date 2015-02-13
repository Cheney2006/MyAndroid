package com.keertech.myandroid.activity;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.keertech.myandroid.R;
import com.keertech.myandroid.activity.base.AbstractBarActivity;
import com.yftools.ViewUtil;
import com.yftools.view.annotation.ContentView;
import com.yftools.view.annotation.ViewInject;
import com.yftools.view.annotation.event.OnClick;
import com.yftools.view.annotation.event.OnCompoundButtonCheckedChange;


@ContentView(R.layout.activity_layout_animation)
public class LayoutAnimationActivity extends AbstractBarActivity {

    @ViewInject(R.id.root_ll)
    private ViewGroup viewGroup;
    @ViewInject(R.id.appear_cb)
    private CheckBox mAppear;
    @ViewInject(R.id.change_appear_cb)
    private CheckBox mChangeAppear;
    @ViewInject(R.id.disappear_cb)
    private CheckBox mDisAppear;
    @ViewInject(R.id.change_disappear_cb)
    private CheckBox mChangeDisAppear;
    private GridLayout mGridLayout;
    private int mVal;
    private LayoutTransition mTransition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtil.inject(this);
        // 创建一个GridLayout
        mGridLayout = new GridLayout(this);
        // 设置每列5个按钮
        mGridLayout.setColumnCount(5);
        // 添加到布局中
        viewGroup.addView(mGridLayout);
        // 默认动画全部开启
        mTransition = new LayoutTransition();
        mTransition.setAnimator(LayoutTransition.APPEARING, (mAppear.isChecked() ? ObjectAnimator.ofFloat(this, "scaleX", 0, 1): null));
        mGridLayout.setLayoutTransition(mTransition);

    }

    @OnClick(R.id.add_btn)
    public void addBtnClick(View view) {
        final Button button = new Button(this);
        button.setText((++mVal) + "");
        mGridLayout.addView(button, Math.min(1, mGridLayout.getChildCount()));
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mGridLayout.removeView(button);
            }
        });
    }

    @OnCompoundButtonCheckedChange(value = {R.id.appear_cb, R.id.change_appear_cb, R.id.disappear_cb, R.id.change_disappear_cb})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mTransition = new LayoutTransition();
        mTransition.setAnimator(LayoutTransition.APPEARING, (mAppear.isChecked() ? ObjectAnimator.ofFloat(this, "scaleX", 0, 1) : null));
        mTransition.setAnimator(LayoutTransition.CHANGE_APPEARING, (mChangeAppear.isChecked() ? mTransition.getAnimator(LayoutTransition.CHANGE_APPEARING) : null));
        mTransition.setAnimator(LayoutTransition.DISAPPEARING, (mDisAppear.isChecked() ? mTransition.getAnimator(LayoutTransition.DISAPPEARING) : null));
        mTransition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, (mChangeDisAppear.isChecked() ? mTransition.getAnimator(LayoutTransition.CHANGE_DISAPPEARING) : null));
        mGridLayout.setLayoutTransition(mTransition);
    }
}
