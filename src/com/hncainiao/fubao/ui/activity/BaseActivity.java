package com.hncainiao.fubao.ui.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.hncainiao.fubao.R;
import com.hncainiao.fubao.application.FuBaoApplication;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.ui.fragment.Stlect_City;
import com.hncainiao.fubao.ui.views.NetLoadDialog;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.hncainiao.fubao.utils.ToastManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * 	项目：FuBaoHealth
 * 		@author liujie
 *	日期：2015-5-12上午10:27:59
 */
public class BaseActivity extends com.jmheart.base.BaseActivity implements BDLocationListener, OnScrollListener {
	
	NetLoadDialog hDialog;
	LocationClient mLocClient;
	public static ImageLoader imageLoader=ImageLoader.getInstance();
	public static DisplayImageOptions options,option1;//图片缓存
	 String code="";//验证码
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		SDKInitializer.initialize(getApplicationContext());
		cache();
		FuBaoApplication.getInstance().activities.add(this);
	//	getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//不弹出软键盘
		
	}
	 public void callPhone(String phoneNum)
	  {
		   //用intent启动拨打电话  
      Intent intent2 = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phoneNum));  
      startActivity(intent2);
	  }

	/**
	 * 图片缓存初始化
	 */
	public static void  cache()
	{
		options = new DisplayImageOptions.Builder()
		  .showStubImage(R.drawable.doc_img_moren)   //加载前显示的图片
		.showImageForEmptyUri(R.drawable.doc_img_moren)	// 设置图片Uri为空或是错误的时候显示的图片
		.showImageOnFail(R.drawable.doc_img_moren)		// 设置图片加载或解码过程中发生错误显示的图片	
		.cacheInMemory(true)						// 设置下载的图片是否缓存在内存中
		.cacheOnDisc(true)							// 设置下载的图片是否缓存在SD卡中	// 设置成圆角图片
		.cacheOnDisc(true)							// 设置下载的图片是否缓存在SD卡中
		.build();
		option1 = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.doc_img_moren)			// 设置图片下载期间显示的图片
		.showImageForEmptyUri(R.drawable.doc_img_moren)	// 设置图片Uri为空或是错误的时候显示的图片
		.showImageOnFail(R.drawable.doc_img_moren)		// 设置图片加载或解码过程中发生错误显示的图片	
		.cacheInMemory(true)						// 设置下载的图片是否缓存在内存中
		.cacheOnDisc(true)							// 设置下载的图片是否缓存在SD卡中	// 设置成圆角图片
		.build();
		
	}


	public void leftbuttonclick(View view) {
		
		this.finish();
		overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
	}
	
	/*@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		this.finish();
		overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
		super.onBackPressed();
	}*/
	
	/**
	 * @param view
	 * 选择城市 
	 */
	public void chossCity(View view) //
	{
		Intent intent=new Intent(this,Stlect_City.class);
		startActivityForResult(intent,123);
	}
	public void setTitle(String msg) {
		
		((TextView) findViewById(R.id.title_txt)).setText(msg);
	}

	/**
	 * 弹出Toast
	 * 
	 * @param msg
	 */
	public void showToast(String msg) {
		ToastManager.getInstance(this).showToast(msg);
	}
	/**
	 * 弹出Toast
	 * 
	 * @param msg
	 */
	public void showToastNotNet() {
		ToastManager.getInstance(this).showToast("没有网络，请先设置网络！");
	}
	/**
	 * 弹出Toast
	 * 
	 * @param msg
	 */
	public void showToastNetTime() {
		ToastManager.getInstance(this).showToast("网络请求超时！");
	}
	
	/**
	 * @param v
	 * @return
	 * 编辑框不为空
	 */
	public  static  boolean CheckEditViewNull(EditText v)
	{
		if (v.getText().toString().equals("")) {
			return true;
		}
		return false;
		
	}
	/**
	 * @param v
	 * @return
	 * 判断是否为手机号码
	 */
	public static boolean CheckPhone(EditText v)
	{
		if (v.getText().toString().matches("[1][34578]\\d{9}")) {
			return true;
		}
		
		return false;
	}
	/**
	 * @return
	 * 判断数据是否为json格式
	 */
	public static String CheckJson(byte[] resut)
	{
		
		 if (!new String(resut).equals("")&&new String(resut)!=null) {
			
			 if (new String(resut).substring(0, 1).equals("[")||new String(resut).substring(0, 1).equals("{")) {
				 return new String(resut);
			}
			
		}
		 
		return "";
	
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 展示加载动画
	 */
	public void Showloading()
	{
		hDialog =new NetLoadDialog(this);
		hDialog.SetMessage("操作中...");
		hDialog.showDialog();
		
	}
	/**
	 * 取消动画
	 */
	public void Dissloading()
	{
		hDialog .dismissDialog();
	}
	/**
	 * 得到验证码
	 */
	public String  getYzm(String phone)
	{
		if (NetworkUtil.isOnline(this)) {
			AsyncHttpClient  httpClient =new AsyncHttpClient();
			RequestParams params =new  RequestParams();
			params.put("phone", phone);
		
		    httpClient.post( Constant.url_YZM, params,new AsyncHttpResponseHandler(){
		    	@Override
		    	public void onStart() {
		    		// TODO Auto-generated method stub
		    		super.onStart();
		    		Showloading();
		    	}
		    	@Override
		    	public void onSuccess(int statusCode,
		    			org.apache.http.Header[] headers, byte[] responseBody) {
		    		// TODO Auto-generated method stub
		    		super.onSuccess(statusCode, headers, responseBody);
		    		Dissloading();
		    		
		    		
						//开始解析数据{"err":1,"msg":"\u7528\u6237\u4e0d\u5b58\u5728\uff08\u7528\u6237\u540d+\u5bc6\u7801\uff09"}
		    			try {
							
							JSONObject jsonObject =new JSONObject(new String(responseBody));
			
							  if (jsonObject.getInt("err")==1) {
							}else if(jsonObject.getInt("err")==0){
								showToast("获取成功");
								
								code=jsonObject.getString("vcode");

								
							}
							 
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    			
		    		
		    		
		    	}
		    	@Override
		    	public void onFailure(int statusCode,
		    			org.apache.http.Header[] headers, byte[] responseBody,
		    			Throwable error) {
		    		// TODO Auto-generated method stub
		    		 Dissloading();
		    		 showToastNetTime();//网络超时
		    	}
		    });
		  
		}
		else
		{
			showToastNotNet();
		}
		return code;
	}
	
	


	/**
	 * 定位初始化
	 */
	public LocationClient location()
	{
		// 定位初始化
					mLocClient = new LocationClient(this);
					mLocClient.registerLocationListener(this);
					LocationClientOption option = new LocationClientOption();
					option.setOpenGps(true);// 打开gps
					option.setCoorType("bd09ll"); // 设置坐标类型
					option.setScanSpan(1000);
					option.setIsNeedAddress(true);
					mLocClient.setLocOption(option);
					mLocClient.start();
					System.out.println("体检定位");
					
					return mLocClient;
					
		}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mLocClient!=null) {
			mLocClient.stop();

		}
		
	}
	@Override
	public void onReceiveLocation(BDLocation arg0) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 单位换算
	 */
	public String  locationkm(String lengString,int km)
	{
		if (km>=1000) {
			lengString=(km/1000)+"km";
		}
		else
		{
			lengString=km+"m";
		}
		return lengString;
	}
	/**
	 * @return
	 * 得到map对象
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String, Object> getMap(HashMap<String, Object> map,String mapString)
	{
		map=(HashMap<String, Object>) FuBaoApplication.getInstance().get(mapString);
		if (map==null) {
			map=new HashMap<String, Object>();
		}
		return map;
		
	}
	/**
	 * @param map
	 * @param mapString
	 * 设置map对象
	 */
	public static void setMap(HashMap<String, Object> map,String mapString)
	{
		if (map!=null) {
			FuBaoApplication.getInstance().set(mapString, map);

		}
	
	}
	/**
	 * @param listView
	 * 设置listview 加载更多
	 */
	public View  listsetFoot(ListView listView)
	{
		View view=LayoutInflater.from(this).inflate(R.layout.list_foote_intme, null);
		if (listView!=null) {
			listView.addFooterView(view);	
		}
		return view;
		
	
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
	}
	
	//判断是否为手机号码
	public  boolean Isphone(String phone ){
		Pattern p = Pattern.compile("^((13[0-9])|(14[^7,\\D])|(17[^7,\\D])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$");
        Matcher m = p.matcher(phone);
        System.out.println(m.matches() + "---");
        return m.matches();	
	}
	//时间戳转换为具体时间
	public String TimetoData(String str){
		String time="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
		long lcc_time = Long.valueOf(str);
		return time = sdf.format(new Date(lcc_time * 1000L));
		
		
	}


	
}
