package com.keertech.myandroid.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;


/**
 * *****************************************
 * Description ：数字动态变化
 * Created by cy on 2015/2/6.
 * *****************************************
 */
public class MagicTextView extends TextView {

    // 递减/递增 的变量值
    private double mRate;
    // 当前显示的值
    private double mCurValue;
    // 当前变化后最终状态的目标值
    private double mGalValue;

    DecimalFormat df = new DecimalFormat("0.00");

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (mCurValue < mGalValue) {
                setText(df.format(mCurValue));
                mCurValue += mRate;
                mHandler.sendEmptyMessageDelayed(0, 50);
            } else {
                setText(df.format(mGalValue));
            }
        }
    };

    public MagicTextView(Context context) {
        super(context);
    }

    public MagicTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MagicTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void setValue(double value) {
        mCurValue = 0.00;
        mGalValue = isShown() ? value : 0;
        mRate = value / 20.00;
        BigDecimal b = new BigDecimal(mRate);
        mRate = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        mHandler.sendEmptyMessageDelayed(0, 50);
    }


}
