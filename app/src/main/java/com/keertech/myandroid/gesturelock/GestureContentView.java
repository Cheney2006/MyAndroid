package com.keertech.myandroid.gesturelock;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.keertech.myandroid.R;
import com.yftools.util.AndroidUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 手势密码容器类
 */
public class GestureContentView extends ViewGroup {

    private int baseNum = 6;

    private int screenWidth;

    /**
     * 每个点区域的宽度
     */
    private int blockWidth;
    /**
     * 声明一个集合用来封装坐标集合
     */
    private List<GesturePoint> list;
    private Context context;
    private GestureDrawline gestureDrawline;

    /**
     *
     * @param context
     * @param callBack 手势绘制完毕的回调 将回调onGestureCodeInput
     */
    public GestureContentView(Context context, GestureDrawline.GestureCallBack callBack) {
        this(context, false, "", callBack);
    }

    /**
     *
     * @param context
     * @param password 用户传入的图形密码
     * @param callBack 手势绘制完毕的回调 将回调checkedSuccess、checkedFail
     */
    public GestureContentView(Context context, String password, GestureDrawline.GestureCallBack callBack) {
        this(context, true, password, callBack);
    }

    /**
     * 包含9个ImageView的容器，初始化
     *
     * @param context
     * @param isVerify 是否为校验手势密码
     * @param password 用户传入的图形密码
     * @param callBack 手势绘制完毕的回调
     */
    public GestureContentView(Context context, boolean isVerify, String password, GestureDrawline.GestureCallBack callBack) {
        super(context);
        screenWidth = AndroidUtil.getDisplayWidth(context);
        blockWidth = screenWidth / 3;
        this.list = new ArrayList<>();
        this.context = context;
        // 添加9个图标
        addChild();
        // 初始化一个可以画线的view
        gestureDrawline = new GestureDrawline(context, list, isVerify, password, callBack);
    }

    private void addChild() {
        for (int i = 0; i < 9; i++) {
            ImageView image = new ImageView(context);
            image.setBackgroundResource(R.drawable.gesture_node_normal);
            this.addView(image);
            invalidate();
            // 第几行
            int row = i / 3;
            // 第几列
            int col = i % 3;
            // 定义点的每个属性
            int leftX = col * blockWidth + blockWidth / baseNum;
            int topY = row * blockWidth + blockWidth / baseNum;
            int rightX = (col + 1) * blockWidth - blockWidth / baseNum;
            int bottomY = (row + 1) * blockWidth - blockWidth / baseNum;
            GesturePoint p = new GesturePoint(leftX, rightX, topY, bottomY, image, i + 1);
            this.list.add(p);
        }
    }

    public void setParentView(ViewGroup parent) {
        // 得到屏幕的宽度
        LayoutParams layoutParams = new LayoutParams(screenWidth, screenWidth);
        this.setLayoutParams(layoutParams);
        gestureDrawline.setLayoutParams(layoutParams);
        parent.addView(gestureDrawline);
        parent.addView(this);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View v;
        GesturePoint point;
        for (int i = 0; i < getChildCount(); i++) {
            v = getChildAt(i);
            point = list.get(i);
            v.layout(point.getLeftX(), point.getTopY(), point.getRightX(), point.getBottomY());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 遍历设置每个子view的大小
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            v.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * 保留路径delayTime时间长
     *
     * @param delayTime
     */
    public void clearDrawlineState(long delayTime) {
        gestureDrawline.clearDrawlineState(delayTime);
    }

}
