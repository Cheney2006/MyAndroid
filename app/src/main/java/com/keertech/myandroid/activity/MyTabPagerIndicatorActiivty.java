package com.keertech.myandroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.keertech.myandroid.R;
import com.keertech.myandroid.activity.base.AbstractBarActivity;
import com.keertech.myandroid.fragment.TestFragment;
import com.keertech.common.view.pageIndicator.TabPageIndicator;
import com.yftools.ViewUtil;
import com.yftools.view.annotation.ContentView;

@ContentView(R.layout.activity_my_tabpager_indicator)
public class MyTabPagerIndicatorActiivty extends AbstractBarActivity {
    // private static final String[] CONTENT = new String[]{"Recent", "Artists", "Albums", "Songs", "Playlists", "Genres"};
    private static final String[] CONTENT = new String[]{"Recent", "Artists", "Albums"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtil.inject(this);
        getSupportActionBar().setTitle("ViewPagerIndicator_Tab使用");
        FragmentPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(pager);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TestFragment.newInstance(CONTENT[position % CONTENT.length]);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }
    }
}
