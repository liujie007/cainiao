package com.hncainiao.fubao.ui.activity.map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View.MeasureSpec;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;
public class GoinfoActivity extends BaseActivity {


	MapView mMapView;
	BaiduMap mBaiduMap;
    Context mContext;
    LocationClient mlocation;
    String hostname;
    
    double lat,lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goinfo_activity);
        mContext=this;
        inintView();
        Intent intent =getIntent();
        if (intent!=null) {
        	hostname=intent.getStringExtra("hostname");
        	if (intent.getDoubleExtra("lat", 0)!=0) {
        		lat=intent.getDoubleExtra("lat", 0);
        		lng=intent.getDoubleExtra("lng", 0);
			}
        	else {
        		lat=28.264395;
        		lng=112.988264;
			}    	
		}
            LatLng llA =new LatLng(lat,lng);
        	addlayout(llA,""+hostname);
        	MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(llA);
        	if(u!=null&&mBaiduMap!=null)
        	{
				mBaiduMap.animateMapStatus(u);

			}
    }
    /**
     * 初始化
     */
    private void inintView()
    {
    	// 地图初始化
    	mMapView = (MapView) findViewById(R.id.map);
		mBaiduMap = mMapView.getMap();	
		mBaiduMap.setMyLocationEnabled(true);
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
		mBaiduMap.setMapStatus(msu);
    }
    /**
     * @param llA
     * @param str
     *  添加图层
     */
    private void addlayout(LatLng llA,String str )
    {
    	TextView tView =new TextView(this);
    	tView.setText(""+str);
    	tView.setTextSize(16);
    	tView.setBackgroundResource(R.color.red);
    	BitmapDescriptor bdA = BitmapDescriptorFactory.fromBitmap(textTobimap(tView));//fromResource(R.drawable.ic_launcher);
		OverlayOptions ooA = new MarkerOptions().position(llA).icon(bdA).zIndex(9).draggable(true);
	    Marker	mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));

    }
   
    /**
     * @param view
     * @return
     * 文字转bitmap
     */
    private Bitmap textTobimap(TextView view)
    {
    	
    	view.setDrawingCacheEnabled(true);  
    	view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));   
    	view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());  
    	Bitmap bitmap = view.getDrawingCache();
		return bitmap; 
    }
   
    @Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}
	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}
	@Override
	protected void onDestroy() {
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}
}
