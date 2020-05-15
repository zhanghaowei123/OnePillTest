package com.onepilltest.nearby;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.location.PoiRegion;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.CityInfo;
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
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.google.gson.Gson;
import com.onepilltest.R;
import com.onepilltest.index.HomeFragment;
import com.onepilltest.util.SharedPreferencesUtil;
import com.onepilltest.util.StatusBarUtil;

import java.util.List;

public class NearFragment extends Fragment {

    private MapView mapView = null;
    private BaiduMap baiduMap;
    private LocationClient locationClient = null;
    private final int REQUEST_GPS = 1;
    private MyListener myListener = new MyListener();
    PoiSearch mPoiSearch = null;//POI检索
    SuggestionSearch mSuggestionSearch = null;
    private MyLocationListener myLocationListener = null;
    BDLocation location = null;
    Button nowlo = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initBar();
    }

    private void initBar() {
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(getActivity());
        //设置状态栏paddingTop
//        StatusBarUtil.setRootViewFitsSystemWindows(getActivity(),true);
        //设置状态栏颜色
//        StatusBarUtil.setStatusBarColor(getActivity(),0xff56ced4);
        //设置状态栏神色浅色切换
        StatusBarUtil.setStatusBarDarkTheme(getActivity(),true);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        SDKInitializer.initialize(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.fragment_near, container, false);

        mapView = (MapView) view.findViewById(R.id.map_view);
        baiduMap = mapView.getMap();
        find(view);
        setMap();
        return view;
    }

    public void find(View view) {
        nowlo = view.findViewById(R.id.near_nowlocation);
        nowlo.setOnClickListener(myListener);
    }

    private void setMap() {
        /**
         * 1.切换地图类型
         * MAP_TYPE_NORMAL:普通地图
         * MAP_TYPE_SATELLITE：卫星地图
         * MAP_TYPE_NONE：空白地图
         */
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);//2.修改默认比例尺为200m
        baiduMap.setMapStatus(msu);
        baiduMap.setMaxAndMinZoomLevel(19.0f, 11.0f);//设置比例尺可缩放范围为：20m ~~~2KM
        View child = mapView.getChildAt(1);//3.不显示百度LOGO
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.GONE);
        }
        //4.定位当前位置
        locationClient = new LocationClient(getActivity().getApplicationContext());//1.创建定位客户端对象
        NearFragment.this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_GPS);//动态申请GPS权限
        BitmapDescriptor locationIcon = BitmapDescriptorFactory.fromResource(R.drawable.isfocus);//设置定位图层的参数
        MyLocationConfiguration configuration = new MyLocationConfiguration//配置定位图层显示方式
                (MyLocationConfiguration.LocationMode.COMPASS
                        , true, locationIcon);
        baiduMap.setMyLocationConfiguration(configuration);
        baiduMap.setMyLocationEnabled(true);//启动定位图层

        LocationClientOption option = new LocationClientOption();//2.创建定位参数选项对象
        option.setScanSpan(0);//可选，设置发起定位请求的间隔，int类型，单位ms
        option.setOpenGps(true);//可选，设置是否使用gps，默认false
        option.setCoorType("bd09ll");//可选，设置返回经纬度坐标类型，默认GCJ02
        option.setIsNeedAddress(true);//设置定位结果中需要字符串地址
        option.setAddrType("all");
        option.setIsNeedLocationPoiList(true);//设置定位结果中需要Poi

        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        option.setIgnoreKillProcess(true);//可选，设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)
        locationClient.setLocOption(option);//3.将定位参数应用到定位客户端
        myLocationListener = new MyLocationListener();//监听器
        locationClient.registerLocationListener(myLocationListener);//4.给定位客户端注册定位成功接口

        locationClient.start();//5.启动定位


        //设置检索监听器
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(listener);

        setSearchName();
        searchInCity("石家庄","医院");
    }


    //请求权限结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_GPS) {

        }
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null) {
                Toast.makeText(getContext(), "定位失败", Toast.LENGTH_SHORT).show();
                return;
            }
            //当定位成功之后回调
            //BDLocation定位结果数据
            Log.e("定位状态", bdLocation.getLocType() + "");
            Log.e("定位结果", "纬度：" + bdLocation.getLatitude() + "\n" +
                    " 经度：" + bdLocation.getLongitude() + "\n" +
                    " 地址：" + bdLocation.getAddrStr() + "\n" +
                    "国家：" + bdLocation.getCountry() + "\n" +
                    "城市：" + bdLocation.getCity() + "\n" +
                    "区/县：" + bdLocation.getDistrict() + "\n");

            location = bdLocation;



            //获取当前位置周围的POI信息点
            List<Poi> poiList = bdLocation.getPoiList();
            Log.e("ishave", "" + poiList.size());
            if (null != poiList) {

                for (int i = 0; i < poiList.size(); i++) {
                    Poi poi = poiList.get(i);
                    String poiName = poi.getName();    //获取POI名称
                    String poiTags = poi.getTags();  //获取POI类型
                    String poiAddr = poi.getAddr();    //获取POI地址 //获取周边POI信息

                    Log.e("poiName", i + "" + poiName);
                    Log.e("poiTags", i + "" + poiTags);
                    Log.e("poiAddr", i + "" + poiAddr);


                }

                //获得经纬度
                LatLng point = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                //移动地图显示当前位置
                MapStatusUpdate move = MapStatusUpdateFactory.newLatLng(point);
                baiduMap.animateMapStatus(move);

                //根据经纬度定位到当前位置
                MyLocationData data = new MyLocationData.Builder()
                        //获取精度
                        .accuracy(bdLocation.getRadius())
                        .latitude(bdLocation.getLatitude())
                        .longitude(bdLocation.getLongitude())
                        .direction(bdLocation.getDirection())
                        .build();
                //在地图中添加定位数据
                baiduMap.setMyLocationData(data);

                //保存数据
                SharedPreferences.Editor editor = SharedPreferencesUtil.getSharedEdit(getContext(),"Map");
                editor.putString("now",new Gson().toJson(location));
                editor.putString("poiList",new Gson().toJson(poiList));
                editor.commit();

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    //按钮监听器
    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.near_nowlocation:
                    Toast.makeText(getContext(), "当前位置", Toast.LENGTH_SHORT).show();
//                    locationClient.start();
                    startActivity(new Intent(getActivity(),NearMap.class));
                    break;

            }
        }
    }

    //POI监听器
    OnGetPoiSearchResultListener listener = new OnGetPoiSearchResultListener() {
        //获取信息回调
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            if (poiResult == null || poiResult.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(getActivity(), "未搜索到POI数据", Toast.LENGTH_SHORT).show();
                Log.e("BDPOI", "未搜索到POI数据");
            }
            if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                //获取POI检索结果
                Toast.makeText(getActivity(), "已搜索到POI数据", Toast.LENGTH_SHORT).show();
                Log.e("BDPOI", "已搜索到POI数据");
            }
            if (poiResult.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
                // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
                String strInfo = "在";
                for (CityInfo cityInfo : poiResult.getSuggestCityList()) {
                    strInfo += cityInfo.city;
                    strInfo += ",";
                }
                strInfo += "找到结果";
                Toast.makeText(getActivity(), strInfo, Toast.LENGTH_LONG).show();
                Log.e("BDPOI", "当输入关键字在本市没有找到：" + "\n" + strInfo);


            }
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }

        //废弃
        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }
    };

    //城市内检索
    public void searchInCity(String city, String type) {
        /**
         *  PoiCiySearchOption 设置检索属性
         *  city 检索城市
         *  keyword 检索内容关键字
         *  pageNum 分页页码
         */
        mPoiSearch.searchInCity(new PoiCitySearchOption()
                .city(city) //必填
                .keyword(type) //必填
                .pageNum(10));
    }

    //周边检索
    public void searchNearby(LatLng center, int radius, int num) {
        mPoiSearch.searchNearby(new PoiNearbySearchOption()
                .keyword("餐厅")
                .sortType(PoiSortType.distance_from_near_to_far)
                .location(center)//圆心位置
                .radius(radius)//半径
                .pageNum(num));//页码
    }

    //热词建议检索
    //设置创建热词索引
    public void setSearchName() {
        mSuggestionSearch = SuggestionSearch.newInstance();

        OnGetSuggestionResultListener listener = new OnGetSuggestionResultListener() {
            public void onGetSuggestionResult(SuggestionResult res) {
                if (res == null || res.getAllSuggestions() == null) {
                    return;
                    //未找到相关结果
                }
                //获取在线建议检索结果
//                public String key;关键字
//                public String city;城市
//                public String district;区
//                public LatLng pt;坐标
//                public String uid;
//                public String tag;
//                public String address;地址
//                public List<PoiChildrenInfo> poiChildrenInfoList;
                List<SuggestionResult.SuggestionInfo> resList = res.getAllSuggestions();
            }
        };

        mSuggestionSearch.setOnGetSuggestionResultListener(listener);
    }

    public void getSearchName(String keyword, String city) {
        // 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
        mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                .keyword(keyword)
                .city(city));
    }

    //跳转
    public void nongda(LatLng latLng) {
        //设置江西农业大学经度和纬度115.839391,28.76806
        //设置的时候经纬度是反的 纬度在前，经度在后
        LatLng latlng = new LatLng(28.76806, 115.839391);
        //1-20级 20级室内地图
        MapStatusUpdate mapStatusUpdate =
                MapStatusUpdateFactory.newLatLngZoom(latlng, 18);
        baiduMap.setMapStatus(mapStatusUpdate);
    }

}
