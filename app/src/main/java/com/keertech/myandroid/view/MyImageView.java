package com.keertech.myandroid.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.yftools.LogUtil;

/**
 * 次ImageView来自网络 很多这样的
 *
 * @author cy
 */
public class MyImageView extends ImageView {
    /**
     * 当前Matrix
     */
    Matrix mCurrentMatrix = new Matrix();
    /**
     * 模板Matrix，用以初始化
     */
    Matrix savedMatrix = new Matrix();
    /**
     * 位图对象
     */
    private Bitmap bitmap = null;
    /** 屏幕的分辨率*/
    //private DisplayMetrics dm;

    /**
     * 最小缩放比例
     */
    float minScaleR = 1.0f;

    /**
     * 最大缩放比例
     */
    static final float MAX_SCALE = 6f;

    /**
     * 初始状态
     */
    static final int NONE = 0;
    /**
     * 拖动
     */
    static final int DRAG = 1;
    /**
     * 缩放
     */
    static final int ZOOM = 2;

    /**
     * 当前模式
     */
    int mode = NONE;

    /**
     * 存储float类型的x，y值，就是你点下的坐标的X和Y
     */
    PointF prev = new PointF();
    PointF mid = new PointF();
    float startDis = 1f;
    private float dx;

    public MyImageView(Context context) {
        super(context);
        setupView();
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView();
    }


    public void setupView() {
        //获取屏幕分辨率,需要根据分辨率来使用图片居中
        //dm = context.getResources().getDisplayMetrics();

        //根据MyImageView来获取bitmap对象
        BitmapDrawable bd = (BitmapDrawable) this.getDrawable();
        if (bd != null) {
            bitmap = bd.getBitmap();
        }

        //设置ScaleType为ScaleType.MATRIX，这一步很重要
        this.setScaleType(ScaleType.MATRIX);
        this.setImageBitmap(bitmap);

        //bitmap为空就不调用center函数
        if (bitmap != null) {
            center(true, true);
        }
        this.setImageMatrix(mCurrentMatrix);
        //在ACTION_DOWN和ACTION_POINTER_DOWN中主要是进行当前事件模式的确定。
        // 当我们按下一个点时，会触发Down事件，而按下第二个点后，又会触发Action_Pointer_Down事件，
        // 在此我们把按下一个点标记为拖动事件，按下两个点标记为缩放事件
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    // 主点按下
                    case MotionEvent.ACTION_DOWN:
                        savedMatrix.set(mCurrentMatrix);//初始化Matrix
                        prev.set(event.getX(), event.getY());
                        mode = DRAG;
                        break;
                    // 副点按下 ,只有同时触屏两个点的时候才执行
                    case MotionEvent.ACTION_POINTER_DOWN:
                        startDis = spacing(event);
                        // 如果连续两点距离大于10，则判定为多点模式
                        if (startDis > 10f) {
                            savedMatrix.set(mCurrentMatrix);//初始化Matrix
                            midPoint(mid, event);
                            mode = ZOOM;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        mode = NONE;
                        //savedMatrix.set(mCurrentMatrix);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mode == DRAG) {
                            LogUtil.d("getLeft=" + getLeft() + ",event.getRawX()-event.getX()=" + (event.getRawX() - event.getX()));
                            //float[] values = new float[9];
                            mCurrentMatrix.set(savedMatrix);//初始化Matrix
                            //mCurrentMatrix.getValues(values);
                          //  LogUtil.d("values[Matrix.MTRANS_X] =" + values[Matrix.MTRANS_X] + ",event.getX() - prev.x=" + (event.getX() - prev.x));

                           // dx = checkDxBound(values, dx);
                           // int dy = checkDyBound(values, dy);
                            mCurrentMatrix.postTranslate(event.getX() - prev.x, event.getY() - prev.y);
                        } else if (mode == ZOOM) {
                            //只有同时触屏两个点的时候才执行
                            if (event.getPointerCount() >= 2) {
                                float endDis = spacing(event);
                                if (endDis > 10f) {// 两个手指并拢在一起的时候像素大于10
                                    float tScale = endDis / startDis;
                                    //startDis = endDis;//重置距离
                                    mCurrentMatrix.set(savedMatrix);//初始化Matrix
                                    //postScale(float sx,float sy)  如果使用这个方法来进行缩放就是以(0，0)点为中心进行缩放，
                                    // 例如原来的坐标(20,30)，缩放比例为2的话，那么缩放后的坐标就变为(40,60)
                                    //第二种方法postScale(float sx,float sy,float px,float py) 和第一种方法的区别就是缩放的中心点置为(px, py)
                                    mCurrentMatrix.postScale(tScale, tScale, mid.x, mid.y);
                                }
                            }
                        }
                        break;
                }
                setImageMatrix(mCurrentMatrix);
//			        CheckView();
                invalidate();
                return true;
            }
        });
    }

    /**
     * 和当前矩阵对比，检验dx，使图像移动后不会超出ImageView边界
     *
     * @param values
     * @param dx
     * @return
     */
    private float checkDxBound(float[] values, float dx) {
        float width = getWidth();
        int mImageWidth = bitmap.getWidth();
        if (mImageWidth * values[Matrix.MSCALE_X] < width)
            return 0;
        if (values[Matrix.MTRANS_X] + dx > 0)
            dx = -values[Matrix.MTRANS_X];
        else if (values[Matrix.MTRANS_X] + dx < -(mImageWidth * values[Matrix.MSCALE_X] - width))
            dx = -(mImageWidth * values[Matrix.MSCALE_X] - width) - values[Matrix.MTRANS_X];
        return dx;
    }

    /**
     * 和当前矩阵对比，检验dy，使图像移动后不会超出ImageView边界
     *
     * @param values
     * @param dy
     * @return
     */
    private float checkDyBound(float[] values, float dy) {
        float height = getHeight();
//        if (mImageHeight * values[Matrix.MSCALE_Y] < height)
//            return 0;
//        if (values[Matrix.MTRANS_Y] + dy > 0)
//            dy = -values[Matrix.MTRANS_Y];
//        else if (values[Matrix.MTRANS_Y] + dy < -(mImageHeight * values[Matrix.MSCALE_Y] - height))
//            dy = -(mImageHeight * values[Matrix.MSCALE_Y] - height) - values[Matrix.MTRANS_Y];
        return dy;
    }

    /**
     * 检验scale，使图像缩放后不会超出最大倍数
     *
     * @param scale
     * @param values
     * @return
     */
    private float checkMaxScale(float scale, float[] values) {
        //values[Matrix.MSCALE_X](事实上就是数组的第0个)代表了X轴的缩放级别
        if (scale * values[Matrix.MSCALE_X] > MAX_SCALE)
            scale = MAX_SCALE / values[Matrix.MSCALE_X];
        mCurrentMatrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);
        return scale;
    }

    /**
     * 横向、纵向居中
     */
    protected void center(boolean horizontal, boolean vertical) {
        Matrix m = new Matrix();
        m.set(mCurrentMatrix);
        RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        m.mapRect(rect);

        float height = rect.height();
        float width = rect.width();

        float deltaX = 0, deltaY = 0;

        if (vertical) {
            // 图片小于屏幕大小，则居中显示。大于屏幕，上方留空则往上移，下方留空则往下移
            int screenHeight = getHeight();
            if (height < screenHeight) {
                deltaY = (screenHeight - height) / 2 - rect.top;
            } else if (rect.top > 0) {
                deltaY = -rect.top;
            } else if (rect.bottom < screenHeight) {
                deltaY = this.getHeight() - rect.bottom;
            }
        }

        if (horizontal) {
            int screenWidth = getWidth();
            if (width < screenWidth) {
                deltaX = (screenWidth - width) / 2 - rect.left;
            } else if (rect.left > 0) {
                deltaX = -rect.left;
            } else if (rect.right < screenWidth) {
                deltaX = screenWidth - rect.right;
            }
        }
        mCurrentMatrix.postTranslate(deltaX, deltaY);
    }


    /**
     * 计算两个手指间的距离
     */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }

    /**
     * 两点的中点
     */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
}
