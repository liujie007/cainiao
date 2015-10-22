package com.hncainiao.fubao.ui.activity.doctor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.login.LoginActivity;
import com.hncainiao.fubao.ui.adapter.DoctorPagerAdapter;
import com.hncainiao.fubao.ui.fragment.DoctorBriefFragment;
import com.hncainiao.fubao.ui.fragment.DoctorConsultFragment;
import com.hncainiao.fubao.ui.fragment.OrderRegisFragment;
import com.hncainiao.fubao.ui.fragment.PatientCommentFragment;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.hncainiao.fubao.utils.StringUtil;
import com.hncainiao.fubao.utils.ToastManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * @author liujie
 * @version 2010年8月7日 下午10:36:39
 * 
 *          医生主页
 */
public class DoctorDetailActivity extends FragmentActivity {

	private static final String TAG = "DoctorDetailActivity";
	private Context mContext;
	private TextView doctor_name, doctor_type, Guanzhu, Huanzhe;
	private ViewPager viewPager;
	private ArrayList<Fragment> fragments;
	private TextView[] textViews = new TextView[4];
	private int currentPageIndex = 0;
	private LinearLayout btnBack;
	ImageView head_img;
	String subscribe_id="";
	Intent intent = null;
	DoctorBriefFragment fragment1;

	private void setupView() {
		mContext = this;
		((TextView) findViewById(R.id.title_txt)).setText("医生主页");
		textViews[1] = (TextView) findViewById(R.id.tv_doctor_brief);
		textViews[0] = (TextView) findViewById(R.id.tv_order_regis);
		textViews[2] = (TextView) findViewById(R.id.tv_patient_comment);
		textViews[3] = (TextView) findViewById(R.id.tv_doctor_consult);
		viewPager = (ViewPager) findViewById(R.id.viewpager_doctor);
		btnBack = (LinearLayout) findViewById(R.id.title_btn_left);
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		doctor_name = (TextView) findViewById(R.id.doctor_name);
		doctor_type = (TextView) findViewById(R.id.doctor_type);
		Guanzhu = (TextView) findViewById(R.id.guanzhunums);
		Huanzhe = (TextView) findViewById(R.id.huanzhenums);
		head_img = (ImageView) findViewById(R.id.img_doctor_head);
		// 收藏按钮
		((Button) findViewById(R.id.doctor_guanzhu)).setVisibility(View.VISIBLE);
		((Button) findViewById(R.id.doctor_guanzhu)).setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						if (SharedPreferencesConfig.getStringConfig(mContext,"member_id").equals("")) {
							ToastManager.getInstance(mContext).showToast("您尚未登陆，请先登陆");
							Intent intent = new Intent(DoctorDetailActivity.this,LoginActivity.class);
							intent.putExtra("mlogin", "self");
							startActivity(intent);
							
						} else {
							
							if(NetworkUtil.isOnline(DoctorDetailActivity.this)){
								 AsyncHttpClient client = new AsyncHttpClient(); 
									RequestParams params = new RequestParams();
									params.put("object_id", getIntent().getStringExtra("Doctor_id"));
									params.put("member_id", SharedPreferencesConfig.getStringConfig(mContext, "member_id"));
									params.put("type", "2");
									client.post(Constant.CONNER_zt, params, new AsyncHttpResponseHandler(){
										  public void onSuccess(int statusCode, Header[] headers,
												  byte[] responseBody) {
											  if(!BaseActivity.CheckJson(responseBody).equals("")){ 
												try {
												JSONObject object=new JSONObject(new String(responseBody));
												if(object.getInt("err")==1){
													addconner();//添加关注
													
												}else if(object.getInt("err")==0){
													subscribe_id=object.getString("subscribe_id");
													CannelGUANzhu();//取消
												}
												  
												 } catch (JSONException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}  
												  
											  }
											  else{
													ToastManager.getInstance(mContext).showToast("数据异常");

											  }
										  }
									});
						}

					}
					}
				});

	}

	private void addListener() {
		for (TextView textView : textViews) {
			textView.setOnClickListener(listener);
		}
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				currentPageIndex = arg0;
				updateButtonBackground();
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor_detail);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getDoctorInfo();
		setupView();
		addListener();
		fragments = new ArrayList<Fragment>();
		fragment1 = new DoctorBriefFragment();
		OrderRegisFragment fragment2 = new OrderRegisFragment();
		PatientCommentFragment fragment3 = new PatientCommentFragment();
		DoctorConsultFragment fragment4 = new DoctorConsultFragment();
		fragments.add(fragment2);
		fragments.add(fragment1);
		fragments.add(fragment3);
		fragments.add(fragment4);
		DoctorPagerAdapter adapter = new DoctorPagerAdapter(this.getSupportFragmentManager(), fragments);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(currentPageIndex);
		updateButtonBackground();
	}

	private void CannelGUANzhu() {
		 if(NetworkUtil.isOnline(DoctorDetailActivity.this)){
			 AsyncHttpClient client = new AsyncHttpClient();
				String url = Constant.DEL_CONNER;
				client.setTimeout(5000);
				RequestParams params = new RequestParams();
				params.put("subscribe_id", subscribe_id);
				client.post(url, params, new AsyncHttpResponseHandler(){
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						// TODO Auto-generated method stub
						if(!BaseActivity.CheckJson(responseBody).equals("")){
							try {
								JSONObject object=new JSONObject(new String(responseBody));
								if(object.getInt("err")==0){
								((Button) findViewById(R.id.doctor_guanzhu)).
									setBackgroundResource(R.drawable.guanzhu_doctor_hui);
								}
		
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					}

				});
		
		 }
			
	}
	
	private void addconner(){
		if (NetworkUtil.isOnline(DoctorDetailActivity.this)) {
			AsyncHttpClient client = new AsyncHttpClient();
			String url = Constant.ADDATTENTION;
			client.setTimeout(5000);
			intent = getIntent();
			RequestParams params = new RequestParams();
			params.put("member_id", SharedPreferencesConfig.getStringConfig(mContext, "member_id"));
			params.put("object_id",intent.getStringExtra("Doctor_id"));
			params.put("type", "2");
			client.post(url, params,new AsyncHttpResponseHandler() {

						public void onStart() {
						};

						public void onSuccess(int statusCode,
								Header[] headers,
								byte[] responseBody) {
							if (!BaseActivity.CheckJson(responseBody).equals("")) {
								try {
									JSONObject object = new JSONObject(new String(responseBody));
									if (object.getInt("err") == 0) {
										ToastManager.getInstance(mContext).showToast(object.getString("msg"));
										((Button) findViewById(R.id.doctor_guanzhu)).setBackgroundResource(R.drawable.guanzhu_doctor_red);
									} else {
										
										CannelGUANzhu();
									}
										
									

								} catch (JSONException e) {
									// TODO Auto-generated
									// catch
									// block
									e.printStackTrace();
								}
							} else {

								ToastManager.getInstance(mContext).showToast("数据异常");
							}
						};

						public void onFailure(
								int statusCode,
								Header[] headers,
								byte[] responseBody,
								Throwable error) {
						};

					});

		} else {
			ToastManager.getInstance(mContext).showToast(
					"当前无网络连接");
		}
		
		
		
	}
		
	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.tv_doctor_brief://
				currentPageIndex = 1;
				break;
			case R.id.tv_order_regis://
				currentPageIndex = 0;
				break;
			case R.id.tv_patient_comment://
				currentPageIndex = 2;
				break;
			case R.id.tv_doctor_consult://
				currentPageIndex = 3;
				break;
			}
			viewPager.setCurrentItem(currentPageIndex);
			updateButtonBackground();
		}
	};
	/**
	 * 改变按钮的背景颜色
	 */
	private void updateButtonBackground() {
		// TODO Auto-generated method stub
		for (int i = 0; i < textViews.length; i++) {
			if (i == this.currentPageIndex) {
				textViews[i].setTextColor(Color.rgb(250, 250, 250));
				textViews[i].setBackgroundColor(Color.rgb(76, 131, 231));
			} else {
				textViews[i].setBackgroundColor(Color.rgb(250, 250, 250));
				textViews[i].setTextColor(Color.rgb(34, 34, 34));
			}
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if(NetworkUtil.isOnline(DoctorDetailActivity.this)){
			 AsyncHttpClient client = new AsyncHttpClient(); 
				String url = Constant.CONNER_zt;
				RequestParams params = new RequestParams();
				intent = getIntent();
				String Doctor_id = intent.getStringExtra("Doctor_id");
				params.put("object_id", Doctor_id);
				params.put("member_id", SharedPreferencesConfig.getStringConfig(mContext, "member_id"));
				params.put("type", "2");
				client.post(url, params, new AsyncHttpResponseHandler(){
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
					}
					
					
					  public void onSuccess(int statusCode, Header[] headers,
							  byte[] responseBody) {
						  if(!BaseActivity.CheckJson(responseBody).equals("")){ 
							try {
							JSONObject object=new JSONObject(new String(responseBody));
							if(object.getInt("err")==1){
								((Button) findViewById(R.id.doctor_guanzhu)).
								   setBackgroundResource(R.drawable.guanzhu_doctor_hui);
								
							}else if(object.getInt("err")==0){
								 ((Button) findViewById(R.id.doctor_guanzhu)).
								   setBackgroundResource(R.drawable.guanzhu_doctor_red);
							}
							  
							 } catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}  
							  
						  }
						  else{
								ToastManager.getInstance(mContext).showToast("数据异常");

						  }
						  
					  }
					  @Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						// TODO Auto-generated method stub

					}

				});
				
				
		}		
		super.onResume();
	} 
	
	private void getDoctorInfo() {
		
		if (NetworkUtil.isOnline(DoctorDetailActivity.this)) {
			AsyncHttpClient client = new AsyncHttpClient();
			client.setTimeout(5000);
			RequestParams params = new RequestParams();
			params.put("doctor_id", getIntent().getStringExtra("Doctor_id"));
			client.post(Constant.DOCTORINFO, params, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
				
					if (!BaseActivity.CheckJson(responseBody).equals("")) {
						try {
							JSONObject object = new JSONObject(new String(
									responseBody));
							if (object.getInt("err") == 0) {
								JSONArray array = object.getJSONArray("doctor");
								for (int i = 0; i < array.length(); i++) {
									doctor_name.setText(array.getJSONObject(i)
											.getString("name"));
									doctor_type.setText(array.getJSONObject(i).getString("title"));
									if(StringUtil.isBlank(array.getJSONObject(i).getString("subscribe_count"))){
										Guanzhu.setText("0");
									}else{
										Guanzhu.setText(array.getJSONObject(i).getString("subscribe_count"));
									}
									if(StringUtil.isBlank(array.getJSONObject(i).getString("patient_sum"))){
										Huanzhe.setText("0");
									}else{
										Huanzhe.setText(array.getJSONObject(i).getString("patient_sum"));
									}
									
									BaseActivity.imageLoader.displayImage(
													Constant.URL_IMAGE_HOST_STRING+ array.getJSONObject(i).getString("avatar"),
													head_img,
													BaseActivity.options);

								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						ToastManager.getInstance(mContext).showToast("数据异常");
					}

					super.onSuccess(statusCode, headers, responseBody);
				}

				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					ToastManager.getInstance(mContext).showToast("获取数据失败");
				}
			});
		} else {
			ToastManager.getInstance(mContext).showToast("当前无网络连接");
		}

	}

}
