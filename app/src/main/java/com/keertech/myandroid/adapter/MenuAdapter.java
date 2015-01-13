package com.keertech.myandroid.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keertech.myandroid.R;
import com.keertech.myandroid.adapter.base.AbstractAdapter;
import com.keertech.myandroid.bean.MyMenuItem;
import com.yftools.ViewUtil;
import com.yftools.view.annotation.ViewInject;

import java.util.List;

/**
 * *****************************************
 * Description ：
 * Created by cy on 2014/10/24.
 * *****************************************
 */
public class MenuAdapter extends AbstractAdapter<MyMenuItem> {

    public MenuAdapter(Context context, List<MyMenuItem> dataList) {
        super(context, dataList);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = getInflater().inflate(R.layout.item_menu, null);
            ViewUtil.inject(viewHolder, view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.title_tv.setText(getItem(position).getTitle());
        return view;
    }

    static class ViewHolder {
        @ViewInject(R.id.title_tv)
        private TextView title_tv;
    }
}
