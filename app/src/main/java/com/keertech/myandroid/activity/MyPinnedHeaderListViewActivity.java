package com.keertech.myandroid.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.keertech.common.view.pinnedHeaderListView.PinnedHeaderListView;
import com.keertech.myandroid.R;
import com.keertech.myandroid.activity.base.AbstractBarActivity;
import com.keertech.myandroid.adapter.CitySectionedAdapter;
import com.keertech.myandroid.bean.City;
import com.keertech.myandroid.utils.DbOperationManager;
import com.keertech.myandroid.view.MySideBar;
import com.yftools.db.sqlite.Selector;
import com.yftools.db.table.DbModel;
import com.yftools.exception.DbException;

import java.util.ArrayList;
import java.util.List;


public class MyPinnedHeaderListViewActivity extends AbstractBarActivity {

    private MySideBar sideBar;
    private TextView dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pinned_header_list_view);
        PinnedHeaderListView listView = (PinnedHeaderListView) findViewById(R.id.pinnedListView);
//        LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        LinearLayout header1 = (LinearLayout) inflator.inflate(R.layout.item_list, null);
//        ((TextView) header1.findViewById(R.id.textItem)).setText("HEADER 1");
//        LinearLayout header2 = (LinearLayout) inflator.inflate(R.layout.item_list, null);
//        ((TextView) header2.findViewById(R.id.textItem)).setText("HEADER 2");
//        LinearLayout footer = (LinearLayout) inflator.inflate(R.layout.item_list, null);
//        ((TextView) footer.findViewById(R.id.textItem)).setText("FOOTER");
//        listView.addHeaderView(header1);
//        listView.addHeaderView(header2);
//        listView.addFooterView(footer);
        String headerSql = "SELECT substr(pys, 1, 1) AS FIRST_LETTER FROM t_city GROUP BY FIRST_LETTER order by FIRST_LETTER";
        //SELECT * FROM t_city where substr(pys, 1, 1)='A'
        try {
            List<City> cityList = null;
            List<DbModel> dbModelList = DbOperationManager.getInstance().getDbModels(headerSql);
            List<List<City>> cityItemList = new ArrayList<>();
            for (DbModel dbModel : dbModelList) {
                cityList = DbOperationManager.getInstance().getBeans(Selector.from(City.class).expr("substr(pys, 1, 1)='" + dbModel.getString("FIRST_LETTER") + "'"));
                cityItemList.add(cityList);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        CitySectionedAdapter sectionedAdapter = new CitySectionedAdapter();
        listView.setAdapter(sectionedAdapter);

        sideBar = (MySideBar) findViewById(R.id.sidebar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new MySideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
//                int position = adapter.getPositionForSection(s.charAt(0));
//                if(position != -1){
//                    sortListView.setSelection(position);
//                }
            }
        });
    }

}
