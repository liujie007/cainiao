package com.hncainiao.fubao.ui.activity.around;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.hncainiao.fubao.R;

public class ZhenSuoMapFragment extends Fragment {
	/**
	 * 诊所地图位置
	 * */
    private View view;
    Context mContext;
	String hospital_name;
	View view2;
	String adress;
	String phone;
	private  MapView mapView;//地图控件
	private BaiduMap mBaiduMap;//百度实例
	//定位的客户端
	private LocationClient mLocationClient;
	//定位的监视器
	public MyLocationListener  mMyLocationListener;
	private LocationMode mCurrentMode ;
	BitmapDescriptor  mCurrentMarker;
	HashMap<String, Object> map,location_map;
	boolean isFirstLoc=true;
	double lat,lng,location_lat,location_lng;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.zhensuomap, null);
		mContext=getActivity();
		mapView=(MapView)view. findViewById(R.id.bmapView2);
		mBaiduMap = mapView.getMap();
	    mBaiduMap.setMyLocationEnabled(true);
	    mCurrentMarker  = BitmapDescriptorFactory .fromResource(R.drawable.smalldian);  
		mCurrentMode = LocationMode.NORMAL;
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
		mBaiduMap.setMapStatus(msu);
		lat=28.291348;
		lng=113.229746;
		LatLng ll = new LatLng(lat,lng);
   		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
   		
       if( u!=null&&mBaiduMap!=null){
   	     	mBaiduMap.animateMapStatus(u);
		}
	    initMyLocation();
	    view2=LayoutInflater.from(mContext).inflate(R.layout.over_map, null);
	    addlayout(ll,view2);
		
		
		return view;
	}
	private void addlayout(LatLng llA,View view ) {
		TextView hospital,adress1,phone;
		hospital=(TextView) view.findViewById(R.id.hospital_name);
		adress1=(TextView) view.findViewById(R.id.adress);
		phone=(TextView) view.findViewById(R.id.phone);
		hospital.setText(hospital_name);
		adress1.setText(adress);
		phone.setText("电话:"+this.phone);
		BitmapDescriptor bdA = BitmapDescriptorFactory.fromBitmap(textTobimap(view));
		//fromResource(R.drawable.ic_launcher);
		OverlayOptions ooA = new MarkerOptions().position(llA).icon(bdA).zIndex(9).draggable(true);
	    Marker	mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
	}
	
	  /**
     * @param view
     * @return
     * 文字转bitmap
     */
    private Bitmap textTobimap(View view)
    {
    	view.setDrawingCacheEnabled(true);  
    	view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));   
    	view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());  
    	Bitmap bitmap = view.getDrawingCache();
		return bitmap; 
    }

	public void initMyLocation() {
		mLocationClient = new LocationClient(mContext);
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		// 设置定位的相关配置
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		option.setTimeOut(5000);
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}
	public class MyLocationListener implements BDLocationListener
	{
		@Override
		public void onReceiveLocation(BDLocation location)
		
		{
			// map view 销毁后不在处理新接收的位置
			if (location == null || mapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius()).
					direction(0).latitude( lat).
					longitude(lng).build();
			    	MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, 
			    		mCurrentMarker);  
			    	mBaiduMap.setMyLocationConfigeration(config);  
			    	
			    	if (locData != null) {
							mBaiduMap.setMyLocationData(locData);
						}
			    	
			    	if (isFirstLoc) {
						isFirstLoc = false;
						
					}
		}

	}
	@Override
	public void onPause() {
		super.onPause();
		// activity 暂停时同时暂停地图控件
		mapView.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		// activity 恢复时同时恢复地图控件
		mapView.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// activity 销毁时同时销毁地图控件
		mapView.onDestroy();
	}
}
