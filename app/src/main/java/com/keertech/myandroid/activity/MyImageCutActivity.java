package com.keertech.myandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.keertech.myandroid.R;
import com.keertech.myandroid.activity.base.AbstractBarActivity;
import com.yftools.ViewUtil;
import com.yftools.view.annotation.ViewInject;
import com.yftools.view.annotation.event.OnClick;

public class MyImageCutActivity extends AbstractBarActivity {

    @ViewInject(R.id.photo_iv)
    private ImageView photo_iv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_image_cut);
        ViewUtil.inject(this);
    }

    @OnClick(R.id.photo_iv)
    public void onClick(View v) {
        Intent intent = new Intent(mContext, CutPicActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 0:
                if (CutPicActivity.bitmap != null) {
                    photo_iv.setImageBitmap(CutPicActivity.bitmap);
                }
                break;
            default:
                break;
        }

    }

}
