package com.keertech.myandroid.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.keertech.myandroid.R;
import com.keertech.myandroid.activity.base.AbstractBarActivity;
import com.keertech.myandroid.fragment.TestFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * PagerTabStrip：点击上面的标题可以实现ViewPager的切换。
 * PagerTitleStrip：点击上面的标题无反应。
 */
public class MyPagerTabStripActivity extends AbstractBarActivity {

    /**
     * 页面list *
     */
    List<Fragment> fragmentList = new ArrayList<Fragment>();
    /**
     * 页面title list *
     */
    List<String> titleList = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagetabstrip);

        ViewPager vp = (ViewPager) findViewById(R.id.viewPager);

        fragmentList.add(TestFragment.newInstance("页面1"));
        fragmentList.add(TestFragment.newInstance("页面2"));
        fragmentList.add(TestFragment.newInstance("页面3"));
        titleList.add("title 1 ");
        titleList.add("title 2 ");
        titleList.add("title 3 ");
        vp.setAdapter(new myPagerAdapter(getSupportFragmentManager(), fragmentList, titleList));
    }

    /**
     * 定义适配器
     */
    class myPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;
        private List<String> titleList;

        public myPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
            super(fm);
            this.fragmentList = fragmentList;
            this.titleList = titleList;
        }

        /**
         * 得到每个页面
         */
        @Override
        public Fragment getItem(int arg0) {
            return (fragmentList == null || fragmentList.size() == 0) ? null : fragmentList.get(arg0);
        }

        /**
         * 每个页面的title
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return (titleList.size() > position) ? titleList.get(position) : "";
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