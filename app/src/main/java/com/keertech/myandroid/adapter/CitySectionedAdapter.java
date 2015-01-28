package com.keertech.myandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keertech.common.view.pinnedHeaderListView.SectionedBaseAdapter;
import com.keertech.myandroid.R;
import com.keertech.myandroid.bean.City;
import com.yftools.ViewUtil;
import com.yftools.db.table.DbModel;
import com.yftools.view.annotation.ViewInject;

import java.util.List;


public class CitySectionedAdapter extends SectionedBaseAdapter {

    private LayoutInflater inflater;
    private List<DbModel> sectionList;
    private List<List<City>> itemList;

    public CitySectionedAdapter(Context mContext, List<DbModel> sectionList, List<List<City>> itemList) {
        this.sectionList = sectionList;
        this.itemList = itemList;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public Object getItem(int section, int position) {
        return itemList != itemList.get(section).get(position) ? sectionList.size() : null;
    }

    @Override
    public long getItemId(int section, int position) {
        return 0;
    }

    @Override
    public int getSectionCount() {
        return sectionList != null ? sectionList.size() : 0;
    }

    @Override
    public int getCountForSection(int section) {
        return itemList != null ? itemList.get(section).size() : 0;
    }

    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_item_view, null);
            ViewUtil.inject(viewHolder, convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title_tv.setText(itemList.get(section).get(position).name);
        return convertView;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_section_view, null);
            ViewUtil.inject(viewHolder, convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title_tv.setText(sectionList.get(section).getString("FIRST_LETTER"));
        return convertView;
    }

    static class ViewHolder {
        @ViewInject(R.id.title_tv)
        private TextView title_tv;
    }

}
