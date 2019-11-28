package com.onepilltest.nearby;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ZoomControls;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
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
import com.onepilltest.R;
import com.onepilltest.index.HomeFragment;

import java.util.List;

public class NearFragment extends Fragment {

    private MapView mapView = null;
    private BaiduMap baiduMap;
    private LocationClient locationClient;
    private final int REQUEST_GPS = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        SDKInitializer.initialize(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.fragment_near, container, false);
        mapView = (MapView) view.findViewById(R.id.map_view);
        //地图控制器
        baiduMap = mapView.getMap();
        //1.设置为普通地图形式
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //2.修改默认比例尺为200m
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);
        baiduMap.setMapStatus(msu);
        //设置比例尺可缩放范围为：20m ~~~2KM
        baiduMap.setMaxAndMinZoomLevel(19.0f, 13.0f);
        //3.不显示百度LOGO
        View child = mapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            //隐藏图片
            child.setVisibility(View.GONE);//GONE隐藏 视图看不见，没有占据任何空间
//            child.setVisibility(View.INVISIBLE);// INVISIBLE隐藏  视图看不见，视图组件仍然占据一定空间
        }
        /**
         * 4.定位店铺当前位置
         */
        //1.创建定位客户端对象
        locationClient = new LocationClient(getActivity().getApplicationContext());
        //动态申请GPS权限
        NearFragment.this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_GPS);
        //设置定位图层的参数
        BitmapDescriptor locationIcon = BitmapDescriptorFactory.fromResource(R.drawable.icon_location);
        MyLocationConfiguration configuration = new MyLocationConfiguration
                (MyLocationConfiguration.LocationMode.COMPASS
                , true, locationIcon);
        //将定位图层添加到地图
        baiduMap.setMyLocationConfiguration(configuration);
        //启动定位图层
        baiduMap.setMyLocationEnabled(true);
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_GPS) {
            //2.创建定位参数选项对象
            LocationClientOption option = new LocationClientOption();
            //设置打开GPS
            option.setOpenGps(true);
            //设置坐标类型
            option.setCoorType("bd09ll");
            //设置定位结果中需要字符串地址
            option.setIsNeedAddress(true);
            //设置定位结果中需要Poi
            option.setIsNeedLocationPoiList(true);
            //设置定位模式:高精度定位模式
            option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
            //3.将定位参数应用到定位客户端
            locationClient.setLocOption(option);
            //4.给定位客户端注册定位成功接口
            locationClient.registerLocationListener(new BDAbstractLocationListener() {
                @Override
                public void onReceiveLocation(BDLocation bdLocation) {
                    //当定位成功之后回调
                    //BDLocation定位结果数据
                    Log.e("定位状态", bdLocation.getLocType() + "");
                    Log.e("定位结果", "纬度：" + bdLocation.getLatitude() +
                            " 经度：" + bdLocation.getLongitude() +
                            " 地址：" + bdLocation.getAddrStr() +
                            "国家：" + bdLocation.getCountry() +
                            "城市：" + bdLocation.getCity() +
                            "区/县：" + bdLocation.getDistrict());
                    //获取当前位置周围的POI信息点
                    List<Poi> poiList = bdLocation.getPoiList();
                    if (null != poiList) {
                        for (int i = 0; i < poiList.size(); i++) {
                            Log.e("周边名称", poiList.get(i).getName());
                            Log.e("周边位置", poiList.get(i).getAddr());
                        }

                        LatLng point = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                        //移动地图显示当前位置
                        MapStatusUpdate move = MapStatusUpdateFactory.newLatLng(point);
                        baiduMap.animateMapStatus(move);

                        //创建定位数据对象，让定位图层来显示
                        MyLocationData data = new MyLocationData.Builder()
                                .latitude(bdLocation.getLatitude())
                                .longitude(bdLocation.getLongitude())
                                .direction(bdLocation.getDirection())
                                .build();
                        //在地图中添加定位数据
                        baiduMap.setMyLocationData(data);
                    }
                }
            });
            //5.启动定位
            locationClient.start();
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
}
