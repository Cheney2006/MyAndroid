package com.keertech.myandroid.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keertech.myandroid.fragment.base.AbstractFragment;
import com.yftools.LogUtil;

public class TextFragment extends AbstractFragment {


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        LogUtil.d("TextFragment onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("TextFragment onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //		if (savedInstanceState != null)
        //			mColorRes = savedInstanceState.getInt("mColorRes");
        LogUtil.d("TextFragment onCreateView");
        String content = getArguments().getString("content");
        TextView text = new TextView(getActivity());
        text.setGravity(Gravity.CENTER);
        text.setText(content);
        text.setTextSize(20 * getResources().getDisplayMetrics().density);
        text.setPadding(20, 20, 20, 20);

        LinearLayout layout = new LinearLayout(getActivity());
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        layout.setGravity(Gravity.CENTER);
        layout.addView(text);

        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.d("TextFragment onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.d("TextFragment onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d("TextFragment onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d("TextFragment onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.d("TextFragment onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.d("TextFragment onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("TextFragment onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.d("TextFragment onDetach");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //		outState.putInt("mColorRes", mColorRes);
    }
}
