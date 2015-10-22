package com.hncainiao.fubao.ui.activity.main;

import java.util.HashMap;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.hncainiao.fubao.R;
import com.hncainiao.fubao.Updata.UpdateManager;
import com.hncainiao.fubao.application.FuBaoApplication;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.around.AbFragment;
import com.hncainiao.fubao.ui.activity.healthshop.IndexHealthFragment;
import com.hncainiao.fubao.ui.fragment.IndexFragment;
import com.hncainiao.fubao.ui.fragment.PersonalCenterFragment;
import com.hncainiao.fubao.ui.fragment.Stlect_City;
import com.hncainiao.fubao.utils.ToastManager;
import com.jmheart.view.FragmentTabHost;

/**
 * @author liujie
 * @version 2015年4月15日 下午1:26:38
 *          主界面
 */
public class MainActivity extends FragmentActivity implements BDLocationListener {
	 //定义FragmentTabHost对象  
    private FragmentTabHost mTabHost;   
    public static String Apkurl;
    //定义一个布局  
    private LayoutInflater layoutInflater;  
    //定义数组来存放Fragment界面  HeathwchFragment.class,
	private Class fragmentArray[] = {IndexFragment.class,IndexHealthFragment.class,AbFragment.class,PersonalCenterFragment.class};     
    //定义数组来存放按钮图片  R.drawable.tab_per_btn,  
    private int mImageViewArray[] = {R.drawable.tab_home_btn,R.drawable.tab_cloud_btn,
                     R.drawable.tab_amb_btn,R.drawable.tab_person_btn};  
    //Tab选项卡的文字  
    private String mTextviewArray[] = {"首页", "健康商城", "周边服务", "个人中心"};  
    Context mContext;
    LocationClient  mLocClient;
    public void onCreate(Bundle savedInstanceState) { 
    
    	getWindow().setFormat(PixelFormat.TRANSLUCENT); 
        super.onCreate(savedInstanceState);  
        HashMap<String, Activity> map=   new HashMap<String, Activity>();
    	map.put("MainActivity", this);
        FuBaoApplication.getInstance().aHashMaps.add(map);
    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main_tab_layout);  
        loaction();
    	mContext = this;
        initView();  
        update();
    }   
    private void loaction()
	{
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(this);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
	}
    private void update()
    {
    		if (getIntent().getStringExtra("Apkurl")!=null) {
    		 Apkurl=getIntent().getStringExtra("Apkurl");
        	if (!Apkurl.equals("")&&Apkurl!=null) {
        		new UpdateManager(mContext).checkUpdateInfo();//版本更新
    		}
		
    	}
    }
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    }
	public void chossCity(View view)
	{
		Intent intent=new Intent(this,Stlect_City.class);
		startActivityForResult(intent,123);
	}
    /** 
     * 初始化组件 
     */  
    private void initView(){  
        //实例化布局对象  
        layoutInflater = LayoutInflater.from(this);     
        //实例化TabHost对象，得到TabHost  
        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);  
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);   
        //得到fragment的个数  
        int count = fragmentArray.length;     
        for(int i = 0; i < count; i++){    
            //为每一个Tab按钮设置图标、文字和内容  
            TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));  
            //将Tab按钮添加进Tab选项卡中  
            mTabHost.addTab(tabSpec, fragmentArray[i], null);   
        }
        mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String arg0) {
				// TODO Auto-generated method stub
				
				
			}
		});
    }              
    /** 
     * 给Tab按钮设置图标和文字 
     */  
    private View getTabItemView(int index){  
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);  
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);  
        imageView.setImageResource(mImageViewArray[index]);  
        TextView textView = (TextView) view.findViewById(R.id.textview);          
        textView.setText(mTextviewArray[index]);  
        return view;  
    }  
	/**
	 * 退出应用
	 */
	private long exitTime = 0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				ToastManager.getInstance(mContext).showToast("再按一次退出程序");
				exitTime = System.currentTimeMillis();
			} else {
				FuBaoApplication.getInstance().quitAllActivity();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void onReceiveLocation(BDLocation arg0) {
		// TODO Auto-generated method stub
		if (arg0!=null) {
			SharedPreferencesConfig.saveStringConfig(mContext, "locationlat", arg0.getLatitude()+"");
			SharedPreferencesConfig.saveStringConfig(mContext, "locationlong", arg0.getLongitude()+"");
			mLocClient.stop();
		}
		
	}
}