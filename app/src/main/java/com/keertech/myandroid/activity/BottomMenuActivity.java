package com.keertech.myandroid.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.keertech.myandroid.R;
import com.keertech.myandroid.activity.base.AbstractBarActivity;
import com.keertech.myandroid.fragment.TextFragment;
import com.yftools.ViewUtil;
import com.yftools.view.annotation.ContentView;
import com.yftools.view.annotation.ViewInject;

@ContentView(R.layout.activity_bottom_menu)
public class BottomMenuActivity extends AbstractBarActivity {

	@ViewInject(android.R.id.tabhost)
	private FragmentTabHost mTabHost;
	 //定义一个布局  
    private LayoutInflater layoutInflater;  
          
    //定义数组来存放Fragment界面
    private Class fragmentArray[] = {TextFragment.class,TextFragment.class,TextFragment.class,TextFragment.class,TextFragment.class};

    //定义数组来存放按钮图片
    private int mImageViewArray[] = {R.drawable.tab_home_btn,R.drawable.tab_message_btn,R.drawable.tab_selfinfo_btn,
                                     R.drawable.tab_square_btn,R.drawable.tab_more_btn};  
      
    //Tab选项卡的文字  
    private String mTextviewArray[] = {"首页", "消息", "好友", "广场", "更多"}; 
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        ViewUtil.inject(this);
		layoutInflater=LayoutInflater.from(mContext);
		initView();
	}
	
	private void initView(){
		//实例化FragmentTabHost
		mTabHost.setup(mContext, getSupportFragmentManager(), R.id.realtabcontent);
		//得到fragment的个数  
        int count = fragmentArray.length;
        Bundle bundle;
        for(int i = 0; i < count; i++){
            bundle=new Bundle();
            bundle.putString("content", mTextviewArray[i]);
            //为每一个Tab按钮设置图标、文字和内容
            TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragmentArray[i], bundle);
            //设置Tab按钮的背景
            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
        }
        mTabHost.setCurrentTab(0);
    }
	
	 /** 
     * 给Tab按钮设置图标和文字 
     */  
    private View getTabItemView(int index){  
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);
      
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);  
        imageView.setImageResource(mImageViewArray[index]);  
          
        TextView textView = (TextView) view.findViewById(R.id.textview);          
        textView.setText(mTextviewArray[index]);  
      
        return view;  
    }

}
