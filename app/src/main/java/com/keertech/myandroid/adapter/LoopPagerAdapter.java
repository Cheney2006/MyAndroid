package com.keertech.myandroid.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.keertech.myandroid.R;
import com.keertech.myandroid.adapter.base.AbstractPagerListAdapter;

import java.util.List;

/**
 * Created by cy on 2015/6/26.
 */
public class LoopPagerAdapter extends AbstractPagerListAdapter<String> {

    private Context mContext;

    public LoopPagerAdapter(Context mContext,List<String> data) {
        super(data);
        this.mContext = mContext;
    }
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public View newView(int position) {
        ImageView imageView=new ImageView(mContext);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setBackgroundResource(R.drawable.circle_photo);
        return imageView;
    }
}
