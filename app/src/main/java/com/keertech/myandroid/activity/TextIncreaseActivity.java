package com.keertech.myandroid.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.keertech.myandroid.R;
import com.keertech.myandroid.activity.base.AbstractBarActivity;
import com.keertech.myandroid.view.MagicTextView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorInflater;
import com.nineoldandroids.animation.ObjectAnimator;
import com.yftools.ViewUtil;
import com.yftools.view.annotation.ContentView;
import com.yftools.view.annotation.ViewInject;
import com.yftools.view.annotation.event.OnClick;

/**
 * *****************************************
 * Description ：数字动态变化
 * Created by cy on 2015/2/12.
 * *****************************************
 */
@ContentView(R.layout.activity_text_increase)
public class TextIncreaseActivity extends AbstractBarActivity {

    @ViewInject(R.id.income_mtv)
    private MagicTextView income_mtv;
    @ViewInject(R.id.total_mtv)
    private MagicTextView total_mtv;
    @ViewInject(R.id.arrowUp_iv)
    private ImageView arrowUp_iv;
    private boolean isDown = true;
    @ViewInject(R.id.clickMe_btn)
    private Button clickMe_btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtil.inject(this);
        income_mtv.setValue(42009842.98);
        total_mtv.setValue(7624234234.45);
    }

    @OnClick(R.id.total_rl)
    public void totalClick(View view) {
        Animator animator;
        if (isDown) {
            animator = AnimatorInflater.loadAnimator(mContext, R.animator.arrow_rotate_up);
        } else {
            animator = AnimatorInflater.loadAnimator(mContext, R.animator.arrow_rotate_down);
        }
//        arrowUp_iv.setPivotX(0);
//        arrowUp_iv.setPivotY(0);
//        //显示的调用invalidate
//        arrowUp_iv.invalidate();
        animator.setTarget(arrowUp_iv);
        animator.start();
        //API 12以下报错。
//        if(isDown){
//            arrowUp_iv.animate().rotation(180);
//        }else{
//            arrowUp_iv.animate().rotation(360);
//        }
        isDown = !isDown;
    }

    @OnClick(R.id.clickMe_btn)
    public void clickMeClick(View view) {
        float width = clickMe_btn.getWidth();
        if (clickMe_btn.getX() == 0) {//3.0以下报错
            ObjectAnimator translationRight = ObjectAnimator.ofFloat(clickMe_btn, "x", width);
            translationRight.setDuration(1500);
            translationRight.start();
        } else {
            ObjectAnimator translationLeft =ObjectAnimator.ofFloat(clickMe_btn, "x", 0f);
            translationLeft.setDuration(1500);
            translationLeft.start();
        }
    }
}
