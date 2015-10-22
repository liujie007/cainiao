package com.hncainiao.fubao.ui.activity.welcome;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.Updata.UpdateManager;
import com.hncainiao.fubao.application.FuBaoApplication;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.main.MainActivity;
import com.hncainiao.fubao.ui.adapter.SplashPagerAdapter;
import com.hncainiao.fubao.ui.views.CustomViewPager;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

/** 
 * @author liujie
 * @version 2015年4月1日 上午10:30:12
 * 
 *          欢迎界面
 */
public class SplashActiivty extends BaseActivity {

	public static final String TAG = "SplashActiivty";

	private Context mContext;
	String appverson="";//服务器端版本号
	public static  String Apkurl="";//服务端APK下载地址

	private CustomViewPager mCustomViewPager;

	private SplashPagerAdapter mSplashPagerAdapter;
     String currentVersion ;//本地版本号

	private ArrayList<View> mViews = new ArrayList<View>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		mContext = this;
		currentVersion= FuBaoApplication.getInstance().getVersionCode(this);
		      try {
		  		 getFUwuqiAPK();
			} catch (TimeoutException e) {
	
						Intent intent = new Intent();
						intent.setClass(mContext, MainActivity.class);
						intent.putExtra("Apkurl", "");
						startActivity(intent);
						finish();
					
			
			e.printStackTrace();
		}
			      

	}
	
/*	  private void   updatavesron(){

			if (!currentVersion.equals(appverson)&&!appverson.equals("")) {// 版本号与之前存储的不一致
				UpdateManager manager=new UpdateManager(mContext);
				manager.checkUpdateInfo();//版本更新
				
				
				findViewById(R.id.spalsh_img).setVisibility(View.GONE);
				mCustomViewPager = (CustomViewPager) findViewById(R.id.vPage_introduce);
				mCustomViewPager.setHorizontalScrollBarEnabled(false);
				LinearLayout.LayoutParams LP_FW = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.MATCH_PARENT);

				LinearLayout linearLayout1 = new LinearLayout(this);
				linearLayout1.setLayoutParams(LP_FW);
				linearLayout1.setBackgroundResource(R.drawable.direction_page1);

				LinearLayout linearLayout2 = new LinearLayout(this);
				linearLayout2.setLayoutParams(LP_FW);
				linearLayout2.setBackgroundResource(R.drawable.direction_page2);

				LinearLayout linearLayout3 = new LinearLayout(this);
				linearLayout3.setLayoutParams(LP_FW);
				linearLayout3.setBackgroundResource(R.drawable.direction_page3);

				linearLayout3.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						//SharedPreferencesConfig.saveStringConfig(
							//	SplashActiivty.this,
							//	Constant.LOOK_INTRDOUCE_VERSION, currentVersion);
						Intent intent = new Intent();
						intent.setClass(mContext, MainActivity.class);
						startActivity(intent);
						finish();
					}
				});

				mViews.add(linearLayout1);
				mViews.add(linearLayout2);
				mViews.add(linearLayout3);

				mSplashPagerAdapter = new SplashPagerAdapter(mContext, mViews, 3,
						mCustomViewPager);
				mSplashPagerAdapter.setTypeFlag(1);
				mCustomViewPager.setOnPageChangeListener(mSplashPagerAdapter);

				mCustomViewPager.setAdapter(mSplashPagerAdapter);
				mSplashPagerAdapter.notifyDataSetChanged();
				

				Intent intent = new Intent();
				intent.setClass(mContext, MainActivity.class);
				startActivity(intent);
				finish();
			} else {
				// 闪屏进入登录界面
				
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						Intent intent = new Intent();
						intent.setClass(mContext, MainActivity.class);
						startActivity(intent);
						finish();
					}
				}, 2000);
			}
	  }
*/


	public void createShortCut() {
		// 创建快捷方式的Intent
		Intent shortcutintent = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		// 不允许重复创建
		shortcutintent.putExtra("duplicate", false);
		// 需要现实的名称
		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME,
				getString(R.string.app_name));
		// 快捷方式的图片
		Parcelable icon = Intent.ShortcutIconResource.fromContext(
				getApplicationContext(), R.drawable.ic_launcher);
		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
		// 点击快捷图片，运行的程序主入口
		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(
				getApplicationContext(), mContext.getClass()));
		// 发送广播 OK
		sendBroadcast(shortcutintent);
		
		
		
	}
	private void getFUwuqiAPK()throws TimeoutException {
		 if(NetworkUtil.isOnline(mContext)){
			 AsyncHttpClient client = new AsyncHttpClient();
				String url = Constant.APPVersion;
				client.setTimeout(3000);
				client.post(url, new AsyncHttpResponseHandler(){
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						try {
							JSONObject object=new JSONObject(new String(responseBody));
							if(object.getInt("err")==0){
							JSONObject jsondata=object.getJSONObject("data"); 
							appverson=jsondata.getString("version");
							Apkurl="http://wx.zgcainiao.com"+jsondata.getString("file");
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								Intent intent = new Intent();
								intent.setClass(mContext, MainActivity.class);
								if (currentVersion.equals(appverson)) {
									intent.putExtra("Apkurl", "");
								}
								else {
									if (Apkurl.equals("")) {
										intent.putExtra("Apkurl", "");
									}
									else {
										intent.putExtra("Apkurl", Apkurl);
									}
									
								}
								startActivity(intent);
								finish();
							}
						}, 2000);
					}
					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								Intent intent = new Intent();
								
								intent.setClass(mContext, MainActivity.class);
								intent.putExtra("Apkurl", "");
								startActivity(intent);
								finish();
							}
						}, 2000);
						
					}
				});
			 	 
			 
		 }else{
			 showToast("无网络连接");
			 new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						Intent intent = new Intent();
						intent.setClass(mContext, MainActivity.class);
						intent.putExtra("Apkurl", "");
						startActivity(intent);
						finish();
					}
				}, 2000);
		 }
		
		
	}
	
	
}
