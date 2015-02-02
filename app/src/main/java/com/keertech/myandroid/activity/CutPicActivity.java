package com.keertech.myandroid.activity;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.keertech.myandroid.R;
import com.keertech.myandroid.view.ImageCut;
import com.yftools.LogUtil;
import com.yftools.ViewUtil;
import com.yftools.view.annotation.ViewInject;
import com.yftools.view.annotation.event.OnClick;

public class CutPicActivity extends Activity implements OnClickListener {

    @ViewInject(R.id.cut_pic_view)
	private ImageCut imageCut;
	public static Bitmap bitmap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cut_pic);
        ViewUtil.inject(this);
	}

	

	@OnClick(R.id.btn_done)
	public void onClick(View v) {
        bitmap = imageCut.onClip();
        setResult(0);
        finish();
	}
 
}
