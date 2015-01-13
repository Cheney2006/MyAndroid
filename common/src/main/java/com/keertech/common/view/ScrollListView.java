package com.keertech.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * *****************************************
 * Description ：解决ListView在ScrollView滑动冲突的
 *              多行TextView只能算一行高度的问题
 * Created by cy on 2014/3/28.
 * *****************************************
 */
public class ScrollListView extends ListView{

    public ScrollListView(Context context) {
        super(context);
    }

    public ScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
