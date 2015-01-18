package com.keertech.myandroid.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * *****************************************
 * Description ：http://blog.csdn.net/lmj623565791/article/details/24555655
 * Created by cy on 2015/1/16.
 * *****************************************
 */
public class RoundImageView extends ImageView {

    public RoundImageView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }

        Bitmap bitmapBorder=null ;//= BitmapFactory.decodeResource(getResources(), R.drawable.border);
        Bitmap bitmapMask=null ;//=BitmapFactory.decodeResource(getResources(), R.drawable.mask);

        int _width = bitmapBorder.getWidth();
        int _height = bitmapBorder.getHeight();

        Paint paint = new Paint();
        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);


        Bitmap bitmap =  ((BitmapDrawable)drawable).getBitmap() ;
        canvas.drawBitmap(bitmapBorder, 0, 0, paint);
        int saveFlags = Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG;
        canvas.saveLayer(0, 0, _width, _height, null, saveFlags);
        canvas.drawBitmap(bitmapMask, 0, 0, paint);
        paint.setXfermode(xfermode);
        int left = _width/2 - bitmap.getWidth() /2;
        int top = _height/2 - bitmap.getHeight()/2;
        canvas.drawBitmap(bitmap, left, top, paint);
        paint.setXfermode(null);
        canvas.restore();

    }

}
