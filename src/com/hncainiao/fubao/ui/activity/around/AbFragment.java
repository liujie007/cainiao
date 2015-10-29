package com.hncainiao.fubao.ui.activity.around;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.fragment.BaseFragment;
import com.jmheart.tools.CallPhone;

public class AbFragment extends BaseFragment implements BDLocationListener ,OnGetPoiSearchResultListener{

	static View view;
	PoiSearch mpointsearch;
	LocationClient mLocClient;
	List<PoiInfo> mlist;
	WebView web_;
	MapView mapview;
    BaiduMap mBaiduMap = null;
    Double lat,log;
    LatLng latLng ;
    PopupWindow popupWindow;  
    PopupWindow popupinfo; 
    TextView tvyiyao,tvzengsuo,tvyaodian;
    View yaodianview,zengsuoview,yiyaoview;
    String scontent="药店";
    public static String strdistace="100000";
    TextView tvdistance;
	List<HashMap<String, Object>> mItems =new ArrayList<HashMap<String,Object>>();
    RelativeLayout rl_select_distance;
   public static boolean isview=false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT); 
    	super.onCreate(savedInstanceState);
    }
    
	@Override
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
			 view=inflater.inflate(R.layout.ab_fragment, null);
			    initView();
				inintDate();
				ininttab();
				tvyaodian.setTextColor(getResources().getColor(R.color.blue));
				yaodianview.setVisibility(View.VISIBLE);
		    
		
		return view ;
	}
	
	
	/**
	 * findViewById(int id)书写简化,无须强制转换、
	 * 
	 * @param id
	 *            控件的id
	 * @return 返回指定View
	 */
	@SuppressWarnings("unchecked")
	public final <E extends View> E getView(int id) {
		try {
			return (E) view.findViewById(id);
		} catch (ClassCastException e) {
			Log.e("Base", "Can't cast the View.", e);
			throw e;
		}

	}
	private void ininttab()
	{
		tvyiyao.setTextColor(getResources().getColor(R.color.black));
		tvzengsuo.setTextColor(getResources().getColor(R.color.black));
		tvyaodian.setTextColor(getResources().getColor(R.color.black));
		yaodianview.setVisibility(View.INVISIBLE);
		zengsuoview.setVisibility(View.INVISIBLE);
		yiyaoview.setVisibility(View.INVISIBLE);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.tv_yiyao:
			ininttab();
			tvyiyao.setTextColor(getResources().getColor(R.color.blue));
			yiyaoview.setVisibility(View.VISIBLE);
			scontent="医药公司";
			mpointsearch.searchNearby(new PoiNearbySearchOption().location(latLng).keyword(scontent).radius(5000));
			
			break;
		case R.id.tv_zhensuo:
			ininttab();
			tvzengsuo.setTextColor(getResources().getColor(R.color.blue));
			zengsuoview.setVisibility(View.VISIBLE);
			scontent="诊所";
			mpointsearch.searchNearby(new PoiNearbySearchOption().location(latLng).keyword(scontent).radius(5000));
			
			break;
		case R.id.tv_yaodian:
			ininttab();
			tvyaodian.setTextColor(getResources().getColor(R.color.blue));
			yaodianview.setVisibility(View.VISIBLE);
			scontent="药店";
			mpointsearch.searchNearby(new PoiNearbySearchOption().location(latLng).keyword(scontent).radius(5000));
			
			break;
		case R.id.rl_select_distance:
			//弹出选择
			getPopupWindow();
		    popupWindow.showAsDropDown(rl_select_distance);
			break;
		default:
			break;
		}
		
	}
	private void initView()
	{
		tvyiyao=getView(R.id.tv_yiyao);
		tvzengsuo=getView(R.id.tv_zhensuo);
		tvyaodian=getView(R.id.tv_yaodian);
		tvyiyao.setOnClickListener(this);
		tvzengsuo.setOnClickListener(this);
		tvyaodian.setOnClickListener(this);
		
	    yaodianview=getView(R.id.yaodian_view);
	    zengsuoview=getView(R.id.zhensuo_view);
	    yiyaoview=getView(R.id.yiyao_view);
	    
	 ((Button) view.findViewById(R.id.comeback)).setVisibility(View.GONE);
	 ((TextView) view.findViewById(R.id.title_txt)).setText("周边服务");
	 mapview=(MapView)view.findViewById(R.id.bmapView);
	   rl_select_distance=((RelativeLayout)view.findViewById(R.id.rl_select_distance));
		rl_select_distance.setVisibility(View.VISIBLE);
		rl_select_distance.setOnClickListener(this);
		tvdistance=(TextView)view.findViewById(R.id.tv_distance);
		tvdistance.setText("不限");
	}
	private void inintDate()
	{
		mpointsearch=PoiSearch.newInstance();
		mpointsearch.setOnGetPoiSearchResultListener(this);
		
		mBaiduMap =  mapview.getMap();
		mLocClient = new LocationClient(getActivity());
		mLocClient.registerLocationListener(this);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		option.setIsNeedAddress(true);
		mLocClient.setLocOption(option);
		mLocClient.start();
	}
	@Override
	protected void creatFragmentUI() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReceiveLocation(BDLocation arg0) {
		// TODO Auto-generated method stub
		if (arg0!=null) {
			lat=arg0.getLatitude();
			log=arg0.getLongitude();
			latLng=new LatLng(arg0.getLatitude(), arg0.getLongitude());
			mpointsearch.searchNearby(new PoiNearbySearchOption().location(latLng).keyword(scontent).radius(5000));
			mLocClient.stop();	
		}
	}

	@Override
	public void onGetPoiDetailResult(PoiDetailResult arg0) {
		// TODO Auto-generated method stub
		if (arg0.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(getActivity(), "抱歉，未找到结果", Toast.LENGTH_SHORT)
					.show();
		} else {
			getPopupWindowInfo(arg0.getName(),arg0.getAddress(),arg0.getTelephone());  
	        popupinfo.showAsDropDown(yaodianview);
			/*Toast.makeText(getActivity(), arg0.getName() + ": " + arg0.getAddress(), Toast.LENGTH_SHORT)
			.show();*/
		}
	}

	
	@Override
	public void onGetPoiResult(PoiResult arg0) {
		// TODO Auto-generated method stub
		if (arg0 == null|| arg0.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
		
			return;
		}
		if (arg0.error == SearchResult.ERRORNO.NO_ERROR) {
			mBaiduMap.clear();
			PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
			mBaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(arg0);
			overlay.addToMap();
			overlay.zoomToSpan();
			return;
		}
		
	}
	private class MyPoiOverlay extends PoiOverlay {

		public MyPoiOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}
		

		@Override
		public boolean onPoiClick(int index) {
			super.onPoiClick(index);
			 PoiInfo poi = getPoiResult().getAllPoi().get(index);
			 mpointsearch.searchPoiDetail((new PoiDetailSearchOption()).poiUid(poi.uid));
			// }
			return true;
		}
		
		
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		mapview.onResume();
		mapview.setVisibility(View.VISIBLE);  
		mapview.onResume();  
	    super.onResume();  
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		mapview.onPause();
		mapview.setVisibility(View.INVISIBLE);  
		mapview.onPause();  
		super.onPause();  
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mLocClient.start();
		mapview.onDestroy();
	}
	/** 
     * 创建PopupWindow 
     */  
    protected void initPopuptWindow() {  
        // TODO Auto-generated method stub  
        // 获取自定义布局文件activity_popupwindow_left.xml的视图  
        View popupWindow_view = getActivity().getLayoutInflater().inflate(R.layout.chose_distance_itme, null,  
                false);  
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度  
        popupWindow = new PopupWindow(popupWindow_view, 98, LayoutParams.MATCH_PARENT, true);  
        // 设置动画效果  
        ListView listView=(ListView)popupWindow_view.findViewById(R.id.list);
        loadDate();
        listView.setAdapter(new SimpleAdapter(getActivity(), mItems, R.layout.distance_itme,  new String[]{"text"}, new int[]{R.id.text}));
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				 if (popupWindow != null && popupWindow.isShowing()) {  
	                    popupWindow.dismiss();  
	                    popupWindow = null;  
	                } 
				tvdistance.setText(mItems.get(arg2).get("text")+"");
				if (mItems.get(arg2).get("text").equals("不限")) {
					strdistace="100000000";
				}
				else {
					strdistace=""+((String) mItems.get(arg2).get("text")).substring(0, mItems.get(arg2).get("text").toString().length()-2);
				
				}
				mpointsearch.searchNearby(new PoiNearbySearchOption().location(latLng).keyword(scontent).radius(Integer.parseInt(strdistace)*1000));
				
			}
		});
       // popupWindow.setAnimationStyle(R.style.AnimationFade);  
        // 点击其他地方消失  
        popupWindow_view.setOnTouchListener(new OnTouchListener() {  
            @Override  
            public boolean onTouch(View v, MotionEvent event) {  
                // TODO Auto-generated method stub  
                if (popupWindow != null && popupWindow.isShowing()) {  
                    popupWindow.dismiss();  
                    popupWindow = null;  
                }  
                return false;  
            }

        });  
    }  
    
    /** 
     * 创建PopupWindow 
     */  
    protected void initPopupinfo(String name,String address,final String phone) {  
        // TODO Auto-generated method stub  
        // 获取自定义布局文件activity_popupwindow_left.xml的视图  
        View popupWindow_view2 = getActivity().getLayoutInflater().inflate(R.layout.ab_fragment_info, null,  
                false);  
       ((TextView)popupWindow_view2.findViewById(R.id.tv_name)).setText("名称："+name);
       ((TextView)popupWindow_view2.findViewById(R.id.tv_address)).setText("地址："+address);
      if (!phone.equals("")) {
    	    ((TextView)popupWindow_view2.findViewById(R.id.tv_phone)).setText("电话："+phone);
    	    
	}
      else
      {
    	  ((TextView)popupWindow_view2.findViewById(R.id.tv_phone)).setVisibility(View.GONE);
      }
        ((TextView)popupWindow_view2.findViewById(R.id.tv_phone)).setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			CallPhone.callphone(getActivity(),phone);
		}
	});
       // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度  
        popupinfo= new PopupWindow(popupWindow_view2, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);  
      
       // popupWindow.setAnimationStyle(R.style.AnimationFade);  
        // 点击其他地方消失  
        popupWindow_view2.setOnTouchListener(new OnTouchListener() {  
            @Override  
            public boolean onTouch(View v, MotionEvent event) {  
                // TODO Auto-generated method stub  
                if (popupinfo != null && popupinfo.isShowing()) {  
                	popupinfo.dismiss();  
                	popupinfo = null;  
                }  
                return false;  
            }

        });  
    }  
    /*** 
     * 获取PopupWindow实例 
     */  
    private void getPopupWindowInfo(String name,String address,String phone) {  
        if (null != popupinfo) {  
        	popupinfo.dismiss();  
            return;  
        } else {  
        	initPopupinfo(name,address,phone);  
        }  
    } 
    /*** 
     * 获取PopupWindow实例 
     */  
    private void getPopupWindow() {  
        if (null != popupWindow) {  
            popupWindow.dismiss();  
            return;  
        } else {  
            initPopuptWindow();  
        }  
    }  
    private void loadDate()
	{
		mItems.clear();
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("text", "不限");
		mItems.add(map);
		HashMap<String, Object> map1=	new HashMap<String, Object>();
		map1.put("text", "1km");
		mItems.add(map1);
		HashMap<String, Object> map2=new HashMap<String, Object>();
		map2.put("text", "3km");
		mItems.add(map2);
		HashMap<String, Object> map3=new HashMap<String, Object>();
		map3.put("text", "5km");
		mItems.add(map3);
		HashMap<String, Object> map4=	new HashMap<String, Object>();
		map4.put("text", "10km");
		mItems.add(map4);
		HashMap<String, Object> map5=	new HashMap<String, Object>();
		map5.put("text", "20km");
		mItems.add(map5);
	}
}
