package com.hncainiao.fubao.ui.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.hncainiao.fubao.R;
import com.hncainiao.fubao.net.ZBapi;
import com.jmheart.net.NetworkUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class YiyaoGongSi extends BaseFragment{
	/** 
	 * 医药公司
	 * */
	private View view;
	Context mContext;
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	  
	// 初始化全局 bitmap 信息，不用时及时 recycle
	BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.yiyaogongsi, null);
		mContext=getActivity();
		mMapView = (MapView) view.findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
		mBaiduMap.setMapStatus(msu);
		LatLng ll = new LatLng(Double.parseDouble(AmbServicesFragment.lat),Double.parseDouble(""+AmbServicesFragment.Long));
   		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
   		
       if( u!=null&&mBaiduMap!=null){
   	     	mBaiduMap.animateMapStatus(u);
		}
		getDate();
		return view;
	}
	
	private void getDate()
	{
		if (NetworkUtil.isOnline(getActivity())) {
			Showloading();
			ZBapi.getNearbyList("4", "1", AmbServicesFragment.lat, AmbServicesFragment.Long, "", AmbServicesFragment.strdistace, mhandler);
		}
		else
		{
			showToast("没有网络连接");
		}
	}
	AsyncHttpResponseHandler mhandler=new AsyncHttpResponseHandler()
	{
		public void onSuccess(String content) {
			Dissloading();
			try {
				JSONObject jsonObject =new JSONObject(content);
				if (jsonObject.getInt("err")==0) {
					JSONArray jArray=jsonObject.getJSONArray("ret");
					for (int i = 0; i < jArray.length(); i++) {
						//测试
			
						JSONObject jsonObject2 =jArray.getJSONObject(i);
						addlayout(jsonObject2.getString("lat"),jsonObject2.getString("lng"),jsonObject2.getString("name"),jsonObject2.getString("address"),jsonObject2.getString("distance"));
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};  
		public void onFailure(Throwable error) {
			Dissloading();
		};
	};
	private void addlayout(String lat,String lng ,String name,String adress,String  phone) {
		LatLng ll = new LatLng(Double.parseDouble(""+lat)-0.00005,Double.parseDouble(""+lng)-0.00005);
	    View view2=LayoutInflater.from(mContext).inflate(R.layout.over_map, null);
		 ((TextView)view2.findViewById(R.id.hospital_name)).setText("医院："+name);
		 ((TextView)view2.findViewById(R.id.adress)).setText("地址："+adress);
		 ((TextView)view2.findViewById(R.id.phone)).setText("距离："+Float.parseFloat(phone)/1000+"km");	
	    BitmapDescriptor bdA = BitmapDescriptorFactory.fromBitmap(textTobimap(view2));
		OverlayOptions ooA = new MarkerOptions().position(ll).icon(bdA).zIndex(9).draggable(true);
	    mBaiduMap.addOverlay(ooA);

	    LatLng point = new LatLng(Double.parseDouble(""+lat), Double.parseDouble(""+lng));  
	    //构建Marker图标  
	    BitmapDescriptor bitmap = BitmapDescriptorFactory  
	        .fromResource(R.drawable.icon_marka);  
	    //构建MarkerOption，用于在地图上添加Marker  
	    OverlayOptions option = new MarkerOptions()  
	        .position(point)  
	        .icon(bitmap);  
	    //在地图上添加Marker，并显示  
	    mBaiduMap.addOverlay(option);
	  
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
	@Override
	protected void creatFragmentUI() {
		// TODO Auto-generated method stub
		
	}
	
	

}
