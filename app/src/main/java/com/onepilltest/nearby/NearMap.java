package com.onepilltest.nearby;

import android.app.Activity;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onepilltest.BaseActivity;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.util.SharedPreferencesUtil;
import com.onepilltest.util.StatusBarUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class NearMap extends BaseActivity {
    private MyListener myListener = new MyListener();
    LinearLayout hospital = null;
    LinearLayout pill = null;
    LinearLayout bank = null;
    LinearLayout bus = null;
    PoiSearch mPoiSearch = null;
    OnGetPoiSearchResultListener poiListener = null;
    EditText edit = null;
    Button back = null;
    Button search = null;
    ListView positionList = null;
    List<Poi> poiList = null;
    List<PoiInfo> poiInfos = null;
    LatLng latLng = null;
    LocationAdapter locationAdapter = null;
    BDLocation bdLocation = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xffffffff);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_near_map);
        find();

        poiListener = new OnGetPoiSearchResultListener() {
            public void onGetPoiResult(PoiResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(NearMap.this, "未搜索到POI数据", Toast.LENGTH_SHORT).show();
                }
                if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    //获取POI检索结果
                    Toast.makeText(NearMap.this, "已搜索到POI数据", Toast.LENGTH_SHORT).show();
                }
                if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
                    // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
                    String strInfo = "在";
                    for (CityInfo cityInfo : result.getSuggestCityList()) {
                        strInfo += cityInfo.city;
                        strInfo += ",";
                    }
                    strInfo += "找到结果";
                    Log.e("NearMap:",""+strInfo);
                    Toast.makeText(NearMap.this, strInfo, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }

            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }

        };

        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);

        initDate();
        locationAdapter = new LocationAdapter(poiInfos,getApplicationContext(),R.layout.nearmap_item);
        positionList.setAdapter(locationAdapter);

        initBar(this);
    }

    private void initBar(Activity activity) {

        //设置状态栏paddingTop
        StatusBarUtil.setRootViewFitsSystemWindows(activity,true);
        //设置状态栏颜色0xff56ced4
//        StatusBarUtil.setStatusBarColor(activity,0xff56ced4);
        //设置状态栏神色浅色切换
        StatusBarUtil.setStatusBarDarkTheme(activity,true);

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_near_map;
    }

    private void initDate() {
//        Type listType = new TypeToken<ArrayList<Poi>>(){}.getType();
        String str = SharedPreferencesUtil.getShared(getApplicationContext(),"Map").getString("now","");
        bdLocation = new Gson().fromJson(str,BDLocation.class);
//        poiList = new Gson().fromJson(json,listType);
        latLng = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
    }

    //在城市中查询
    public void searchInCity(String city,String name,int num){
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city(city)
                .keyword(name)
                .pageNum(num));
    }

    //周边查询
    public void searchNear(LatLng center,int radius,int num,String key){
        mPoiSearch.searchNearby(new PoiNearbySearchOption()
                .keyword(key)
                .sortType(PoiSortType.distance_from_near_to_far)
                .location(center)
                .radius(radius)
                .pageNum(num));
    }

    //POI监听器
    OnGetPoiSearchResultListener listener = new OnGetPoiSearchResultListener() {
        //获取信息回调
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            if (poiResult == null) {
                Toast.makeText(NearMap.this, "未搜索到POI数据", Toast.LENGTH_SHORT).show();
                Log.e("BDPOI", "未搜索到POI数据");
            }
            if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                //获取POI检索结果
                Toast.makeText(NearMap.this, "已搜索到POI数据", Toast.LENGTH_SHORT).show();
                Log.e("BDPOI", "已搜索到POI数据");

                poiInfos = poiResult.getAllPoi();
                locationAdapter.notifyDataSetChanged();

            }
            if (poiResult.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
                // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
                String strInfo = "在";
                for (CityInfo cityInfo : poiResult.getSuggestCityList()) {
                    strInfo += cityInfo.city;
                    strInfo += ",";
                }
                strInfo += "找到结果";
                Toast.makeText(NearMap.this, strInfo, Toast.LENGTH_LONG).show();
                Log.e("BDPOI", "当输入关键字在本市没有找到：" + "\n" + strInfo);


            }
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }

        @Override
        public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };

    private void find() {
        positionList = findViewById(R.id.nearmap_list);
        hospital = findViewById(R.id.nearmap_hospital);
        hospital.setOnClickListener(myListener);
        pill = findViewById(R.id.nearmap_pill);
        pill.setOnClickListener(myListener);
        bank = findViewById(R.id.nearmap_bank);
        bank.setOnClickListener(myListener);
        bus = findViewById(R.id.nearmap_bus);
        bus.setOnClickListener(myListener);
        edit = findViewById(R.id.nearmap_edit);
        edit.setOnClickListener(myListener);
        search = findViewById(R.id.nearmap_search);
        search.setOnClickListener(myListener);
        back = findViewById(R.id.nearmap_back);
        back.setOnClickListener(myListener);
    }

    private class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.nearmap_hospital:

                    searchInCity("石家庄","公司",10);
                    break;
                case R.id.nearmap_pill:
                    Toast.makeText(getApplicationContext(),"点击",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nearmap_bank:
                    Toast.makeText(getApplicationContext(),"点击",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nearmap_bus:
                    Toast.makeText(getApplicationContext(),"点击",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nearmap_search:
                    break;
                case R.id.nearmap_back:
                    break;

            }
        }
    }


}
