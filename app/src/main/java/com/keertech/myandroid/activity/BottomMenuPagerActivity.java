package com.keertech.myandroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.keertech.myandroid.R;
import com.keertech.myandroid.activity.base.AbstractBarActivity;
import com.keertech.myandroid.fragment.TestFragment;
import com.yftools.LogUtil;
import com.yftools.ViewUtil;
import com.yftools.view.annotation.ContentView;
import com.yftools.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用RadioGroup来实现，
 * 状态是可以保存了，问题是无法实现气泡功能，不能自定义布局，
 * 因为RadioGroup里面只能包含RadioButton，不然状态切换不起用作，
 */
@ContentView(R.layout.activity_bottom_menu_pager)
public class BottomMenuPagerActivity extends AbstractBarActivity {

    @ViewInject(R.id.viewPager)
    private ViewPager viewPager;
    @ViewInject(R.id.group)
    private RadioGroup group;

    private List<Fragment> fragmentList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtil.inject(this);
        getSupportActionBar().setTitle("ViewPager+RadioGroup底部菜单");
        initView();
        initPager();
    }

    private void initView() {
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                LogUtil.d("checkedId=" + group.getCheckedRadioButtonId() + ",checkedId=" + checkedId);
                switch (checkedId) {
                    case R.id.home_rb:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.message_rb:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.friend_rb:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.square_rb:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.more_rb:
                        viewPager.setCurrentItem(4);
                        break;
                }
            }
        });
    }

    private void initPager() {
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(TestFragment.newInstance("首页"));
        fragmentList.add(TestFragment.newInstance("消息"));
        fragmentList.add(TestFragment.newInstance("好友"));
        fragmentList.add(TestFragment.newInstance("广场"));
        fragmentList.add(TestFragment.newInstance("更多"));
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragmentList));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int position) {
                LogUtil.d("position=" + position);
                //group.check(group.getChildAt(position).getId());//这个onPageSelected会调用多次。
                ((RadioButton)group.getChildAt(position)).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
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

        /**
         * 页面的总个数
         */
        @Override
        public int getCount() {
            return fragmentList == null ? 0 : fragmentList.size();
        }
    }

}
