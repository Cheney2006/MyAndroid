package com.keertech.myandroid.gesturelock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.keertech.myandroid.R;

/**
 * 手势密码图案提示
 */
public class LockIndicator extends View {

    private int numRow = 3;    // 行
    private int numCol = 3; // 列
    private int patternWidth = 40;
    private int patternHeight = 40;
    private int f = 5;
    private int g = 5;
    private int strokeWidth = 3;
    private Paint paint = null;
    private Drawable patternNoraml = null;
    private Drawable patternPressed = null;
    private String lockPassStr; // 手势密码

    public LockIndicator(Context paramContext) {
        super(paramContext);
    }

    public LockIndicator(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet, 0);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Style.STROKE);
        patternNoraml = getResources().getDrawable(R.drawable.lock_pattern_node_normal);
        patternPressed = getResources().getDrawable(R.drawable.lock_pattern_node_pressed);
        if (patternPressed != null) {
            //getIntrinsicWidth()得到的就是拉伸后的宽度.而不是真正图片的宽度.
            patternWidth = patternPressed.getIntrinsicWidth();
            patternHeight = patternPressed.getIntrinsicHeight();
            this.f = (patternWidth / 4);
            this.g = (patternHeight / 4);
            patternPressed.setBounds(0, 0, patternWidth, patternHeight);
            patternNoraml.setBounds(0, 0, patternWidth, patternHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if ((patternPressed == null) || (patternNoraml == null)) {
            return;
        }
        // 绘制3*3的图标
        for (int i = 0; i < numRow; i++) {
            for (int j = 0; j < numCol; j++) {
                paint.setColor(-16777216);
                int i1 = j * patternHeight + j * this.g;
                int i2 = i * patternWidth + i * this.f;
                //save用来保存Canvas的状态。save之后，可以调用Canvas的平移、放缩、旋转、错切、裁剪等操作。
                //save和restore要配对使用（restore可以比save少，但不能多），如果restore调用次数比save多，会引发Error。save和restore之间，往往夹杂的是对Canvas的特殊操作。
                canvas.save();
                //canvas.translate(10, 10);把当前画布的原点移到(10,10),后面的操作都以(10,10)作为参照点，默认原点为(0,0)
                canvas.translate(i1, i2);
                String curNum = String.valueOf(numCol * i + (j + 1));
                if (!TextUtils.isEmpty(lockPassStr)) {
                    if (lockPassStr.indexOf(curNum) == -1) {
                        // 未选中
                        patternNoraml.draw(canvas);
                    } else {
                        // 被选中
                        patternPressed.draw(canvas);
                    }
                } else {
                    // 重置状态
                    patternNoraml.draw(canvas);
                }
                //用来恢复Canvas之前保存的状态。防止save后对Canvas执行的操作对后续的绘制有影响。
                canvas.restore();
            }
        }
    }

    @Override
    protected void onMeasure(int paramInt1, int paramInt2) {
        if (patternPressed != null)
            setMeasuredDimension(numCol * patternHeight + this.g
                    * (-1 + numCol), numRow * patternWidth + this.f
                    * (-1 + numRow));
    }

    /**
     * 请求重新绘制
     *
     * @param paramString 手势密码字符序列
     */
    public void setPath(String paramString) {
        lockPassStr = paramString;
        invalidate();
    }
}