package com.keertech.myandroid.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.keertech.common.vo.LocationInfo;
import com.keertech.myandroid.R;
import com.keertech.myandroid.activity.base.BaseMapActivity;
import com.keertech.myandroid.utils.LocationProviderHelper;
import com.yftools.LogUtil;
import com.yftools.ViewUtil;
import com.yftools.view.annotation.ContentView;
import com.yftools.view.annotation.ViewInject;
import com.yftools.view.annotation.event.OnClick;

/**
 * *****************************************
 * Description ：
 * Created by cy on 2014/11/10.
 * *****************************************
 */
@ContentView(R.layout.activity_my_poi_serch)
public class MyPoiSearchActivity extends BaseMapActivity implements OnGetPoiSearchResultListener {

    @ViewInject(R.id.searchKey_et)
    private EditText searchKey_et;
    private LocationProviderHelper locationProviderHelper;
    private PoiSearch mPoiSearch;
    private LocationInfo lastLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtil.inject(this);
        getSupportActionBar().setTitle("POI搜索");
        mBaiduMap = ((SupportMapFragment) (getSupportFragmentManager()
                .findFragmentById(R.id.map))).getBaiduMap();
        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        locationProviderHelper = new LocationProviderHelper(mContext, new LocationProviderHelper.LocationFinished() {
            @Override
            public void getLocation(final LocationInfo location) {
                lastLocation = location;
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                addPopWindow(null, "定位地址：" + location.getAddress(), ll);
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, 19.0f);
                mBaiduMap.animateMapStatus(u);
            }
        });
        locationProviderHelper.startLocation(10 * 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPoiSearch.destroy();
        if (locationProviderHelper != null) {
            locationProviderHelper.stopLocation();
        }
    }

    @OnClick(R.id.search_btn)
    public void searchClick(View view) {
        if (TextUtils.isEmpty(searchKey_et.getText())) {
            displayToast("请输入关键字");
        } else {
            LatLng ll = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            mPoiSearch.searchNearby(new PoiNearbySearchOption().keyword(searchKey_et.getText().toString()).location(ll).radius(100));
        }
    }


    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        if (poiResult == null|| poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            return;
        }
        for (PoiInfo poiInfo : poiResult.getAllPoi()) {
            LogUtil.d("名字=" + poiInfo.name + ",地址=" + poiInfo.address);
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }
}
