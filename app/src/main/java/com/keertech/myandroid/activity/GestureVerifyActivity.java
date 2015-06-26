package com.keertech.myandroid.activity;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.keertech.myandroid.R;
import com.keertech.myandroid.activity.base.AbstractActivity;
import com.keertech.myandroid.gesturelock.GestureContentView;
import com.keertech.myandroid.gesturelock.GestureDrawline;
import com.yftools.ViewUtil;
import com.yftools.view.annotation.ViewInject;

/**
 * 手势绘制/校验界面
 */
public class GestureVerifyActivity extends AbstractActivity implements View.OnClickListener {

    @ViewInject(R.id.text_cancel)
    private TextView mTextCancel;
    @ViewInject(R.id.text_tip)
    private TextView mTextTip;
    @ViewInject(R.id.gesture_container)
    private FrameLayout mGestureContainer;
    private GestureContentView mGestureContentView;
    @ViewInject(R.id.text_forget_gesture)
    private TextView mTextForget;
    @ViewInject(R.id.text_other_account)
    private TextView mTextOther;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_verify);
        getSupportActionBar().hide();
        ViewUtil.inject(this);
        setUpViews();
        setUpListeners();
    }


    private void setUpViews() {
        // 初始化一个显示各个点的viewGroup
        mGestureContentView = new GestureContentView(this, "1235789",
                new GestureDrawline.GestureCallBack() {

                    @Override
                    public void onGestureCodeInput(String inputCode) {

                    }

                    @Override
                    public void checkedSuccess() {
                        mGestureContentView.clearDrawlineState(0L);
                        displayToast("密码正确");
                        finish();
                    }

                    @Override
                    public void checkedFail() {
                        mGestureContentView.clearDrawlineState(1500L);
                        mTextTip.setVisibility(View.VISIBLE);
                        mTextTip.setText(Html.fromHtml("<font color='#c70c1e'>密码错误</font>"));
                        // 左右移动动画
                        Animation shakeAnimation = AnimationUtils.loadAnimation(GestureVerifyActivity.this, R.anim.shake);
                        mTextTip.startAnimation(shakeAnimation);
                    }
                });
        // 设置手势解锁显示到哪个布局里面
        mGestureContentView.setParentView(mGestureContainer);
    }

    private void setUpListeners() {
        mTextCancel.setOnClickListener(this);
        mTextForget.setOnClickListener(this);
        mTextOther.setOnClickListener(this);
    }

    private String getProtectedMobile(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 11) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(phoneNumber.subSequence(0, 3));
        builder.append("****");
        builder.append(phoneNumber.subSequence(7, 11));
        return builder.toString();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_cancel:
                this.finish();
                break;
            default:
                break;
        }
    }

}
