package com.keertech.myandroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.keertech.myandroid.R;
import com.keertech.myandroid.fragment.TestFragment;
import com.yftools.LogUtil;
import com.yftools.ViewUtil;
import com.yftools.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * *****************************************
 * Description ：仿微信TabPager
 * Created by cy on 2015/2/2.
 * *****************************************
 */
public class WeiXinIndexActivity extends FragmentActivity {
    private ArrayList<Fragment> fragmentList;
    @ViewInject(R.id.tabPager)
    private ViewPager tabPager;
    @ViewInject(R.id.img_main)
    private ImageView img_main;
    @ViewInject(R.id.img_classify)
    private ImageView img_classify;
    @ViewInject(R.id.img_track)
    private ImageView img_track;
    @ViewInject(R.id.img_me)
    private ImageView img_me;
    @ViewInject(R.id.img_main_on)
    private ImageView img_main_on;
    @ViewInject(R.id.img_classify_on)
    private ImageView img_classify_on;
    @ViewInject(R.id.img_track_on)
    private ImageView img_track_on;
    @ViewInject(R.id.img_me_on)
    private ImageView img_me_on;
    private ImageView[] tabOff;
    private ImageView[] tabOn;
    private MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weixin_index);
        ViewUtil.inject(this);
        init();
    }

    private void init() {
        tabOff = new ImageView[]{img_main, img_classify, img_track, img_me};
        tabOn = new ImageView[]{img_main_on, img_classify_on, img_track_on, img_me_on};
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(TestFragment.newInstance("首页"));
        fragmentList.add(TestFragment.newInstance("消息"));
        fragmentList.add(TestFragment.newInstance("好友"));
        fragmentList.add(TestFragment.newInstance("广场"));
        adapter = new MyPagerAdapter(getSupportFragmentManager(), fragmentList);
        tabPager.setAdapter(adapter);
        tabPager.setOnPageChangeListener(onPageChangeListener);
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            tabOn[arg0].setAlpha(1 - arg1);
            tabOff[arg0].setAlpha(arg1);
//            LogUtil.d("tabPager.getMoveLeft()="+tabPager.getMoveLeft());
//            if (tabPager.getMoveLeft()) {
//                if (arg0 != 0) {
//                    tabOn[arg0 - 1].setAlpha(arg1);
//                    tabOff[arg0 - 1].setAlpha(1 - arg1);
//                }
//            } else {
            if (arg0 != adapter.getCount() - 1) {
                tabOn[arg0 + 1].setAlpha(arg1);
                tabOff[arg0 + 1].setAlpha(1 - arg1);
            }

//            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

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

        /**
         * 页面的总个数
         */
        @Override
        public int getCount() {
            return fragmentList == null ? 0 : fragmentList.size();
        }
    }
}
