package com.hncainiao.fubao.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.adapter.DoctorPagerAdapter;
import com.hncainiao.fubao.ui.views.IndexViewPager;
 
/**
 * @author liujie
 * 
 *        周边服务
 */
public class AmbServicesFragment extends Fragment implements BDLocationListener {

	private View view;
	Context mContext;
	IndexViewPager viewPager;
	MedicineShop shop;//药店
	ZhenSuo zhenSuo;//诊所
	YiyaoGongSi gongSi;//医药公司
	TextView yaodian,zhensu,yiyaosongsi;
	View yd_view,zs_view,gongsi_view;
	private ArrayList<Fragment> fragments;
	public static int currentPageIndex = 0;
	Button button;//返回
	TextView title;//标题
	TextView tvdistance;
	LocationClient  mLocClient;
	public static String lat,Long,strdistace="100000000";
	List<HashMap<String, Object>> mItems =new ArrayList<HashMap<String,Object>>();
	// 声明PopupWindow对象的引用  
    private PopupWindow popupWindow,yaopopupWindow;  
    RelativeLayout rl_select_distance;
    Button btnSearch;
    ImageView imxiala;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_ambitus_services, null);
		mContext=getActivity();
		 lat=SharedPreferencesConfig.getStringConfig(mContext, "locationlat");
		 Long=SharedPreferencesConfig.getStringConfig(mContext, "locationlong");
		loaction();
		InitView();
		loadDate();
		addListen();
		return view;
	}
	private void loaction()
	{
		mLocClient = new LocationClient(getActivity());
		mLocClient.registerLocationListener(this);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
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
    ImageView imMap;
    ImageView imList;
    protected void initYaoPopuptWindow() {  
        // TODO Auto-generated method stub  
        // 获取自定义布局文件activity_popupwindow_left.xml的视图  
    	
        View popupWindow_view2 = getActivity().getLayoutInflater().inflate(R.layout.yaopopwind, null,  
                false);  
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度  
        yaopopupWindow = new PopupWindow(popupWindow_view2, 150, LayoutParams.WRAP_CONTENT
        		, true);  
        // 设置动画效果  
         imMap=(ImageView)popupWindow_view2.findViewById(R.id.im_map);
         imList=(ImageView)popupWindow_view2.findViewById(R.id.im_List);
         imList.setImageResource(R.drawable.icon_list_nomoer);
		 imMap.setImageResource(R.drawable.icon_pass_map);
         imMap.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				imList.setImageResource(R.drawable.icon_list_nomoer);
				imMap.setImageResource(R.drawable.icon_pass_map);
				currentPageIndex=2;
				viewPager.setCurrentItem(currentPageIndex);
				yaopopupWindow.dismiss();
			}
		});
        imList.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				imList.setImageResource(R.drawable.icon_list_pass);
				imMap.setImageResource(R.drawable.icon_nomer_map);
				currentPageIndex=3;
				viewPager.setCurrentItem(currentPageIndex);
				yaopopupWindow.dismiss();
			}
		});
       // popupWindow.setAnimationStyle(R.style.AnimationFade);  
        // 点击其他地方消失  
        popupWindow_view2.setOnTouchListener(new OnTouchListener() {  
            @Override  
            public boolean onTouch(View v, MotionEvent event) {  
                // TODO Auto-generated method stub  
                if (yaopopupWindow != null && yaopopupWindow.isShowing()) {  
                	yaopopupWindow.dismiss();  
                	yaopopupWindow = null;  
                }  
                return false;  
            }

        });  
    }  
    /*** 
     * 获取PopupWindow实例 
     */  
    private void getyouPopupWindow() {  
        if (null != yaopopupWindow) { 
        	imList.setImageResource(R.drawable.icon_list_nomoer);
			imMap.setImageResource(R.drawable.icon_pass_map);
        	yaopopupWindow.dismiss();  
            return;  
        } else {  
        	initYaoPopuptWindow();  
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
   
	private void InitView() {
		viewPager=(IndexViewPager) view.findViewById(R.id.viewpager_);
		button=(Button) view.findViewById(R.id.comeback);
		button.setVisibility(ViewGroup.GONE);
		title=(TextView) view.findViewById(R.id.title_txt);
		title.setText("周边服务");
		btnSearch=(Button)view.findViewById(R.id.btn_search);
		btnSearch.setOnClickListener(new l());
		rl_select_distance=((RelativeLayout)view.findViewById(R.id.rl_select_distance));
		rl_select_distance.setVisibility(View.VISIBLE);
		rl_select_distance.setOnClickListener(new l());
		tvdistance=(TextView)view.findViewById(R.id.tv_distance);
		tvdistance.setText("不限");
		shop=new MedicineShop();
		gongSi=new YiyaoGongSi();
		
		zhenSuo=new ZhenSuo();
		imxiala=(ImageView)view.findViewById(R.id.im_xiala);
		yaodian=(TextView) view.findViewById(R.id.yaodian);
		zhensu=(TextView) view.findViewById(R.id.zhensuo);
		yiyaosongsi=(TextView) view.findViewById(R.id.yo);
		yd_view=view.findViewById(R.id.yaodian_view);
		zs_view=view.findViewById(R.id.zhensuo_view);
		gongsi_view=view.findViewById(R.id.yiyaogongsi_view);
		fragments = new ArrayList<Fragment>();
		fragments.add(shop);
		fragments.add(zhenSuo);
		fragments.add(gongSi);
		fragments.add(new YiliaoList());
		DoctorPagerAdapter adapter = new DoctorPagerAdapter(getChildFragmentManager(), fragments);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(currentPageIndex);
		setLine();
	}
   private void addListen() {
	   yaodian.setOnClickListener(new l());
	   zhensu.setOnClickListener(new l());
	   yiyaosongsi.setOnClickListener(new l());
	
	}
 
   class l implements OnClickListener{

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.yaodian:
			currentPageIndex=0;
			viewPager.setCurrentItem(currentPageIndex);
			setLine();
			break;
		case R.id.zhensuo:
			currentPageIndex=1;
			viewPager.setCurrentItem(currentPageIndex);
			setLine();
		break;
		case R.id.yo:
			
			getyouPopupWindow();
			yaopopupWindow.showAsDropDown(gongsi_view);
			currentPageIndex=2;
			setLine();
			viewPager.setCurrentItem(currentPageIndex);
			/*setLine();*/
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
	   
   };
   private void setLine(){
	   if(currentPageIndex==0){
		   imxiala.setVisibility(View.INVISIBLE);
		   yd_view.setVisibility(ViewGroup.VISIBLE); 
		   yaodian.setTextColor(Color.parseColor("#4b83e7"));
		   yiyaosongsi.setTextColor(Color.BLACK);
		   zhensu.setTextColor(Color.BLACK);
		   
		   zs_view.setVisibility(ViewGroup.INVISIBLE);
		   gongsi_view.setVisibility(ViewGroup.INVISIBLE);
	   }else 
		 if(currentPageIndex==1){
			 imxiala.setVisibility(View.INVISIBLE);
			 yd_view.setVisibility(ViewGroup.INVISIBLE);  
			   zs_view.setVisibility(ViewGroup.VISIBLE);
			   gongsi_view.setVisibility(ViewGroup.INVISIBLE); 
			   yaodian.setTextColor(Color.BLACK);
			   yiyaosongsi.setTextColor(Color.BLACK);
			   zhensu.setTextColor(Color.parseColor("#4b83e7"));
		 }
	   if(currentPageIndex==2){
		   imxiala.setVisibility(View.VISIBLE);
		   yd_view.setVisibility(ViewGroup.INVISIBLE);  
		   zs_view.setVisibility(ViewGroup.INVISIBLE);
		   gongsi_view.setVisibility(ViewGroup.VISIBLE); 
		   yaodian.setTextColor(Color.BLACK);
		   yiyaosongsi.setTextColor(Color.parseColor("#4b83e7"));
		   zhensu.setTextColor(Color.BLACK);
	   }
	   
	   
   }
	@Override
	public void onReceiveLocation(BDLocation arg0) {
		// TODO Auto-generated method stub
		if (arg0!=null) {
			lat=arg0.getLatitude()+"";
			Long=arg0.getLongitude()+"";
		}
		
	}
	
}
