package com.keertech.myandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.keertech.myandroid.R;
import com.keertech.myandroid.activity.base.AbstractBarActivity;
import com.keertech.myandroid.adapter.MenuAdapter;
import com.keertech.myandroid.bean.MyMenuItem;
import com.yftools.ViewUtil;
import com.yftools.view.annotation.ViewInject;
import com.yftools.view.annotation.event.OnItemClick;

import java.util.ArrayList;

public class MyActivity extends AbstractBarActivity {

    @ViewInject(R.id.listView)
    private ListView listView;
    private ArrayList<MyMenuItem> myMenuItemList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        ViewUtil.inject(this);
        initData();
    }

    private void initData() {
        myMenuItemList = new ArrayList<MyMenuItem>();
        myMenuItemList.add(new MyMenuItem("校验控件", LoginActivity.class));
        myMenuItemList.add(new MyMenuItem("进度ActionBar", MyProgressBarActivity.class));
        myMenuItemList.add(new MyMenuItem("标题居中ActionBar", MyTitleCenterActivity.class));
        myMenuItemList.add(new MyMenuItem("FragmentTabHost底部菜单", BottomMenuActivity.class));
        myMenuItemList.add(new MyMenuItem("ViewPager+RadioGroup底部菜单", BottomMenuPagerActivity.class));
        myMenuItemList.add(new MyMenuItem("仿微信底部菜单", WeiXinIndexActivity.class));
        myMenuItemList.add(new MyMenuItem("进化版Tab", MyViewPagerIndicatorActivity.class));
//        myMenuItemList.add(new MyMenuItem("PagerTabStrip顶部导航", MyPagerTabStripActivity.class));//这个意义不大，使用MyTabPagerIndicatorActiivty代替
        myMenuItemList.add(new MyMenuItem("ViewPagerIndicator_Tab使用", MyTabPagerIndicatorActiivty.class));
        myMenuItemList.add(new MyMenuItem("ViewPagerIndicator_Circle使用", MyCirclesIndicatorPagerActivity.class));
        myMenuItemList.add(new MyMenuItem("SwipeRefreshLayout使用", MySwipeRefreshLayoutActivity.class));
        myMenuItemList.add(new MyMenuItem("RecyclerView使用", MyRecyclerViewActivity.class));
        myMenuItemList.add(new MyMenuItem("CardView使用", MyCardViewActivity.class));
        myMenuItemList.add(new MyMenuItem("PoiSearchNearBy使用", MyPoiSearchActivity.class));
        myMenuItemList.add(new MyMenuItem("IndexableListView使用", IndexableListViewActivity.class));
        myMenuItemList.add(new MyMenuItem("PinnedHeaderListView使用", MyPinnedHeaderListViewActivity.class));
        myMenuItemList.add(new MyMenuItem("VIEW绘制", CustomViewActivity.class));
        myMenuItemList.add(new MyMenuItem("圆形进度条", MyRoundProgressBarActivity.class));
        myMenuItemList.add(new MyMenuItem("圆角头像", CircleViewActivity.class));
        myMenuItemList.add(new MyMenuItem("圆形头像裁剪", MyImageCutActivity.class));
        myMenuItemList.add(new MyMenuItem("ScrollView引导页面", ScrollerViewActivity.class));
        myMenuItemList.add(new MyMenuItem("坚直ViewPager引导页面", VerticalViewPagerActivity.class));
        myMenuItemList.add(new MyMenuItem("坚直ViewPager-Property动画引导页面", VerticalViewPagerActivity.class));
        myMenuItemList.add(new MyMenuItem("二维码扫描", MyCodeActivity.class));
        myMenuItemList.add(new MyMenuItem("生成二维码", QRCodeGenerateActivity.class));
        myMenuItemList.add(new MyMenuItem("生成二维码比较好", MaxCardActivity.class));
        listView.setAdapter(new MenuAdapter(mContext, myMenuItemList));

    }

    @OnItemClick(R.id.listView)
    public void listItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mContext, myMenuItemList.get(position).getCls());
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(mContext, MyTitleCenterActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
