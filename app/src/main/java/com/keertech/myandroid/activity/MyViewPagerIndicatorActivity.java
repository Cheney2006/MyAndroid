package com.keertech.myandroid.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.keertech.myandroid.R;
import com.keertech.myandroid.activity.base.AbstractBarActivity;
import com.keertech.myandroid.fragment.TestFragment;
import com.keertech.myandroid.view.tabview.SlidingTabLayout;
import com.keertech.myandroid.view.tabview.SlidingTabStrip;
import com.yftools.ViewUtil;
import com.yftools.view.annotation.ContentView;
import com.yftools.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * *****************************************
 * Description ：自定义ViewPager指示器
 * Created by cywf on 2015/1/6.
 * *****************************************
 */
@ContentView(R.layout.activity_my_view_pager_indicator)
public class MyViewPagerIndicatorActivity extends AbstractBarActivity {

    @ViewInject(R.id.sliding_tabs)
    private SlidingTabLayout slidingTabLayout;
    @ViewInject(R.id.viewPager)
    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentList;
    private String[] titles = {"首页", "消息", "好友", "广场", "更多"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtil.inject(this);
        setTitle("自定义ViewPager指示器");
        initData();
    }

    private void initPager() {
        fragmentList = new ArrayList<Fragment>();
        for (String title : titles) {
            fragmentList.add(TestFragment.newInstance(title));
        }
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragmentList));
    }


    /**
     * 定义适配器
     */
    class MyPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;

        public MyPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        /**
         * 得到每个页面
         */
        @Override
        public Fragment getItem(int arg0) {
            return (fragmentList == null || fragmentList.size() == 0) ? null : fragmentList.get(arg0);
        }

        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        /**
         * 页面的总个数
         */
        @Override
        public int getCount() {
            return fragmentList == null ? 0 : fragmentList.size();
        }
    }

    private void initData() {
        initPager();
        slidingTabLayout.setIndicatorType(SlidingTabStrip.IndicatorType.TRIANGLE);
//        slidingTabLayout.isShowDivide(false);
//        slidingTabLayout.setCustomTabView(R.layout.tab_view, 0);//自定义view平分屏幕
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setTabViewTextBackground(R.drawable.bg_tab_click);
        slidingTabLayout.setViewPager(viewPager);
        slidingTabLayout.setSelectedIndicatorColors(Color.WHITE,Color.RED,Color.BLUE);
        slidingTabLayout.setDividerColors(0xffcdcdcd,Color.RED,Color.BLUE);
//        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
//            @Override
//            public int getIndicatorColor(int position) {
//                return Color.WHITE;
//            }
//
//            @Override
//            public int getDividerColor(int position) {
//                return 0xffcdcdcd;
//            }
//        });

    }

}
