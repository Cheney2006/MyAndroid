package com.keertech.myandroid.activity;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.keertech.myandroid.R;
import com.keertech.myandroid.activity.base.AbstractActivity;
import com.keertech.myandroid.gesturelock.GestureContentView;
import com.keertech.myandroid.gesturelock.GestureDrawline;
import com.keertech.myandroid.gesturelock.LockIndicator;
import com.yftools.ViewUtil;
import com.yftools.view.annotation.ViewInject;

/**
 * 手势密码设置界面
 */
public class GestureEditActivity extends AbstractActivity implements OnClickListener {

    @ViewInject(R.id.text_cancel)
    private TextView mTextCancel;
    @ViewInject(R.id.lock_indicator)
    private LockIndicator mLockIndicator;
    @ViewInject(R.id.text_tip)
    private TextView mTextTip;
    private GestureContentView mGestureContentView;
    @ViewInject(R.id.gesture_container)
    private FrameLayout mGestureContainer;
    @ViewInject(R.id.text_reset)
    private TextView mTextReset;
    private boolean mIsFirstInput = true;
    private String mFirstPassword = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_edit);
        ViewUtil.inject(this);
        getSupportActionBar().hide();
        setUpViews();
        setUpListeners();
    }

    private void setUpViews() {
        mTextReset.setClickable(false);
        mGestureContainer = (FrameLayout) findViewById(R.id.gesture_container);
        // 初始化一个显示各个点的viewGroup
        mGestureContentView = new GestureContentView(this, new GestureDrawline.GestureCallBack() {
            @Override
            public void onGestureCodeInput(String inputCode) {
                if (!isInputPassValidate(inputCode)) {
                    mTextTip.setText(Html.fromHtml("<font color='#c70c1e'>最少链接4个点, 请重新输入</font>"));
                    mGestureContentView.clearDrawlineState(0L);
                    return;
                }
                if (mIsFirstInput) {
                    mFirstPassword = inputCode;
                    updateCodeList(inputCode);
                    mGestureContentView.clearDrawlineState(0L);
                    mTextReset.setClickable(true);
                    mTextReset.setText(getString(R.string.reset_gesture_code));
                } else {
                    if (inputCode.equals(mFirstPassword)) {
                        displayToast("设置成功");
                        mGestureContentView.clearDrawlineState(0L);
                        finish();
                    } else {
                        mTextTip.setText(Html.fromHtml("<font color='#c70c1e'>与上一次绘制不一致，请重新绘制</font>"));
                        // 左右移动动画
                        Animation shakeAnimation = AnimationUtils.loadAnimation(mContext, R.anim.shake);
                        mTextTip.startAnimation(shakeAnimation);
                        // 保持绘制的线，1.5秒后清除
                        mGestureContentView.clearDrawlineState(1500L);
                    }
                }
                mIsFirstInput = false;
            }

            @Override
            public void checkedSuccess() {

            }

            @Override
            public void checkedFail() {

            }
        });
        // 设置手势解锁显示到哪个布局里面
        mGestureContentView.setParentView(mGestureContainer);
        updateCodeList("");
    }

    private void setUpListeners() {
        mTextCancel.setOnClickListener(this);
        mTextReset.setOnClickListener(this);
    }

    private void updateCodeList(String inputCode) {
        // 更新选择的图案
        mLockIndicator.setPath(inputCode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_cancel:
                this.finish();
                break;
            case R.id.text_reset:
                mIsFirstInput = true;
                updateCodeList("");
                mTextTip.setText(getString(R.string.set_gesture_pattern));
                break;
            default:
                break;
        }
    }

    private boolean isInputPassValidate(String inputPassword) {
        if (TextUtils.isEmpty(inputPassword) || inputPassword.length() < 4) {
            return false;
        }
        return true;
    }

}
