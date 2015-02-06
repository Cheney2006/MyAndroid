package com.keertech.myandroid.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.yftools.LogUtil;

import java.lang.reflect.Field;

/**
 * 次ImageView来自网络 很多这样的
 *
 * @author cy
 */
public class MatrixImageView extends ImageView {
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
    /**
     * 两点放大两点间最小间距
     */
    private static final int DOUBLE_POINT_DISTANCE = 10;
    /**
     * 最小缩放比例
     */
    float minScaleR = 1.0f;

    /**
     * 最大缩放比例
     */
    static final float MAX_SCALE = 3f;

    static final int NONE = 0;
    static final int DRAG = 1; // 拖动操作
    static final int ZOOM = 2; // 放大缩小操作
    private int mode = NONE; // 当前模式

    /**
     * 存储float类型的x，y值，就是你点下的坐标的X和Y
     */
    PointF prev = new PointF();
    PointF mid = new PointF();

    float startDis; // 多点触摸两点距离
    float endDis; // 多点触摸两点距离

    float screenHeight;
    float screenWidth;
    float topHeight; // 状态栏高度和标题栏高度
    Bitmap primaryBitmap = null;

    float contentW; // 屏幕内容区宽度
    float contentH; // 屏幕内容区高度

    float primaryW; // 原图宽度
    float primaryH; // 原图高度

    Boolean isMoveX = true; // 是否允许在X轴拖动
    Boolean isMoveY = true; // 是否允许在Y轴拖动
    float limitRight;//右边可移动距离
    float limitLeft;//左边可移到距离
    float limitBottom;//下边可移到距离
    float limitTop;//上边可移到距离

    public MatrixImageView(Context context) {
        super(context);
        setupView();
    }

    public MatrixImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView();
    }


    public void setupView() {
        //获取屏幕分辨率,需要根据分辨率来使用图片居中
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        getTopHeight(dm);
        //TODO 根据圆大小设定
        contentW = 400;
        contentH = 400;
        limitLeft = limitRight = (screenWidth - contentW) / 2;
        limitTop = limitBottom = (screenHeight - topHeight - contentH) / 2;
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
                        //savedMatrix.set(mCurrentMatrix);//初始化Matrix
                        prev.set(event.getRawX(), event.getRawY());
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
                        //  mouseMove(event);
                        if (mode == DRAG) {
                            // mCurrentMatrix.set(savedMatrix);//初始化Matrix
                            float endX = event.getRawX();
                            float endY = event.getRawY();
                            float transX = endX - prev.x;
                            float transY = endY - prev.y;
                           // LogUtil.d("transX=" + transX + ",transY=" + transY);
                           // LogUtil.d("limitLeft=" + limitLeft + ",limitRight=" + limitRight + ",limitTop=" + limitTop + ",limitBottom=" + limitBottom);
                            boolean isMoveX = false, isMoveY = false;
                            if ((transX >= 0 && transX < limitLeft) || (transX <= 0 && -transX < limitRight)) {
                                isMoveX = true;
                            }
                            if ((transY >= 0 && transY < limitTop) || (transY <= 0 && -transY < limitBottom)) {
                                isMoveY = true;
                            }
                            prev.set(endX, endY);
                            if (isMoveX && isMoveY) {
                                mCurrentMatrix.postTranslate(transX, transY);
                                if (isMoveX) {
                                    limitLeft -= transX;
                                    limitRight += transX;
                                }
                                if (isMoveY) {
                                    limitTop -= transY;
                                    limitBottom += transY;
                                }
                            }
                        } else if (mode == ZOOM) {
                            //TODO 默认时只能放大。不能缩小
                            //只有同时触屏两个点的时候才执行
                            if (event.getPointerCount() >= 2) {
                                float endDis = spacing(event);
                                if (endDis > DOUBLE_POINT_DISTANCE) {// 两个手指并拢在一起的时候像素大于10
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
                return true;
            }
        });
    }

    private void getTopHeight(DisplayMetrics dm) {
        TypedValue tv = new TypedValue();
        if (getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            int actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, dm);
            topHeight = actionBarHeight;
//            // 状态栏的高度,在OnCreate中获取不到高度。始终为0
//            Rect frame = new Rect();
//            context.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
//            int statusBarHeight = frame.top;
            Class<?> c = null;
            Object obj = null;
            Field field = null;
            int x = 0, sbar = 0;
            try {
                c = Class.forName("com.android.internal.R$dimen");
                obj = c.newInstance();
                field = c.getField("status_bar_height");
                x = Integer.parseInt(field.get(obj).toString());
                sbar = getResources().getDimensionPixelSize(x);
                topHeight += sbar;
            } catch (Exception e1) {
                LogUtil.e("get status bar height fail");
                e1.printStackTrace();
            }
        }
        //topHeight = getContext().getResources().getDimensionPixelSize(R.dimen.abc_action_bar_default_height);
    }


    public void mouseMove(MotionEvent event) {
        if ((mode == DRAG) && (isMoveX || isMoveY)) {
            float[] XY = getTranslateXY(mCurrentMatrix);
            float transX = 0;
            float transY = 0;

            if (isMoveX) {
                if ((XY[0] + transX) <= limitRight) {
                    transX = limitRight - XY[0];
                }
                if ((XY[0] + transX) >= limitLeft) {
                    transX = limitLeft - XY[0];
                }
            }
            if (isMoveY) {

                if ((XY[1] + transY) <= limitBottom) {
                    transY = limitBottom - XY[1];
                }
                if ((XY[1] + transY) >= limitTop) {
                    transY = limitTop - XY[1];
                }
            }

            mCurrentMatrix.postTranslate(transX, transY);
            // prev.set(endX, endX);
            //this.setImageMatrix(mCurrentMatrix);
        } else if (mode == ZOOM && event.getPointerCount() > 1) {
            endDis = spacing(event);
            float dif = endDis - startDis;
            if (Math.abs(dif) > DOUBLE_POINT_DISTANCE) {
                changeSize(mid.x, mid.y);
                mode = NONE;
            }
        }
    }

    /**
     * 图片放大缩小
     *
     * @param x 点击点X坐标
     * @param y 点击点Y坐标
     */
    private void changeSize(float x, float y) {
        float tScale = endDis / startDis;
        mCurrentMatrix.postScale(tScale, tScale, x, y); // 在原有矩阵后乘放大倍数
        float transX = -((tScale - 1) * x);
        float transY = -((tScale - 1) * (y - topHeight)); // (tScale-1)(y-statusBarHeight-subY)+2*subY;
        float currentWidth = primaryW * tScale; // 放大后图片大小
        float currentHeight = primaryH * tScale;
        // 如果图片放大后超出屏幕范围处理
        if (currentHeight > contentH) {
            limitBottom = -(currentHeight - contentH - 140); // 平移限制
            limitTop = 140;
            isMoveY = true; // 允许在Y轴上拖动

        } else {
            // 如果图片放大后没有超出屏幕范围处理，则不允许拖动
            isMoveY = false;
        }

        if (currentWidth > contentW) {
            limitRight = -(currentWidth - contentW - 40);
            limitLeft = 40;
            isMoveX = true;

        } else {
            isMoveX = false;
        }
        mCurrentMatrix.postTranslate(transX, transY);
        this.setImageMatrix(mCurrentMatrix);
    }

    /**
     * 横向、纵向居中
     */
    protected void center(boolean horizontal, boolean vertical) {
        Matrix m = new Matrix();
        m.set(mCurrentMatrix);
        RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        m.mapRect(rect);
        float height = rect.height();//534
        float width = rect.width();//800
        float deltaX = 0, deltaY = 0;
        if (vertical) {
            // 图片小于屏幕大小，则居中显示
            float contentHeight = screenHeight - topHeight;
            if (height < contentHeight) {
                deltaY = (contentHeight - height) / 2 - rect.top;
                limitTop -= deltaY;
                limitBottom = limitTop;
            } else {
                limitBottom += height - contentHeight;
            }
//            else if (rect.top > 0) {//。大于屏幕，上方留空则往上移，下方留空则往下移
//                deltaY = -rect.top;
//            } else if (rect.bottom < screenHeight) {
//                deltaY = this.getHeight() - rect.bottom;
//            }
        }

        if (horizontal) {
            if (width < screenWidth) {
                deltaX = (screenWidth - width) / 2 - rect.left;
                limitLeft -= deltaX;
                limitRight = limitLeft;
            } else {
                limitRight += width - screenWidth;
            }
//            else if (rect.left > 0) {
//                deltaX = -rect.left;
//            } else if (rect.right < screenWidth) {
//                deltaX = screenWidth - rect.right;
//            }
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
     * 获取变换矩阵中X轴偏移量和Y轴偏移量
     *
     * @param matrix 变换矩阵
     * @return
     */
    private float[] getTranslateXY(Matrix matrix) {
        float[] values = new float[9];
        matrix.getValues(values);
        float[] floats = new float[2];
        floats[0] = values[Matrix.MTRANS_X];
        floats[1] = values[Matrix.MTRANS_Y];
        return floats;
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
