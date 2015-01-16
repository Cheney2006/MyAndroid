package com.keertech.myandroid.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;

import com.keertech.myandroid.R;
import com.keertech.myandroid.activity.base.AbstractBarActivity;

/**
 * *****************************************
 * Description ：
 * Created by cy on 2015/1/16.
 * *****************************************
 */
public class CustomViewActivity extends AbstractBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
    }

    /**
     * 获取圆角位图的方法
     *
     * @param bitmap 需要转化成圆角的位图
     * @param pixels 圆角的度数，数值越大，圆角越大
     * @return 处理后的圆角位图
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        if (bitmap == null) {
            return null;
        }
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        /**
         * 画一个圆角矩形
         * rectF: 矩形
         * roundPx 圆角在x轴上或y轴上的半径
         */
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        //设置两张图片相交时的模式
        //setXfermode前的是 dst 之后的是src
        //在正常的情况下，在已有的图像上绘图将会在其上面添加一层新的形状。
        //如果新的Paint是完全不透明的，那么它将完全遮挡住下面的Paint；
        //PorterDuffXfermode就可以来解决这个问题
        //canvas原有的图片 可以理解为背景 就是dst
        //新画上去的图片 可以理解为前景 就是src
//      paint.setXfermode(new PorterDuffXfermode(Mode.SRC_OUT));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}
