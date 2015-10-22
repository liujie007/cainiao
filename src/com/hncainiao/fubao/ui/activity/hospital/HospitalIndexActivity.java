package com.hncainiao.fubao.ui.activity.hospital;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.login.LoginActivity;
import com.hncainiao.fubao.ui.activity.personalcenter.RegisOrderActivity;
import com.hncainiao.fubao.ui.activity.registration.SelectOfficesActivity;
import com.hncainiao.fubao.ui.adapter.MainGridViewAdapter;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.hncainiao.fubao.utils.ToastManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/* 
 * @author zhaojing
 * @version 2010年8月7日 下午4:35:17
 *          医院主页
 */
public class HospitalIndexActivity extends BaseActivity {
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private Context mContext;
	private GridView mGridView;
	ImageView hospital_img;
	String hospital_id,hospital_name1;

	private MainGridViewAdapter mAdapter;
	TextView hospital_name, attention_nums;
	List<HashMap<String, String>> list = null;
	Intent intent = null;
	String subscribe_id="";

	private void setupView() {
		setTitle("医院主页");
		((Button) findViewById(R.id.title_right_collect))
				.setVisibility(View.VISIBLE);
		((Button) findViewById(R.id.title_right_collect))
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View arg0) {
						
						if (SharedPreferencesConfig.getStringConfig(mContext,
								"member_id").equals("")) {
							ToastManager.getInstance(mContext).showToast(
									"您尚未登陆，请先登陆");
							Intent intent = new Intent(
									HospitalIndexActivity.this,
									LoginActivity.class);
							startActivity(intent);
							finish();
						} else {
							if(NetworkUtil.isOnline(HospitalIndexActivity.this)){
								if(intent==null){
									
								}
								 AsyncHttpClient client = new AsyncHttpClient(); 
									String url = Constant.CONNER_zt;
									RequestParams params = new RequestParams();
									params.put("object_id",intent.getStringExtra("hospital_id"));
									params.put("member_id", SharedPreferencesConfig.getStringConfig
											(mContext, "member_id"));
									params.put("type", "1");
									client.post(url, params, new AsyncHttpResponseHandler(){
										
										public void onStart() {
											Showloading();
											
										};
										
										  public void onSuccess(int statusCode, Header[] headers,
												  byte[] responseBody) {
											  Dissloading();
											  if(!BaseActivity.CheckJson(responseBody).equals("")){ 
												try {
												JSONObject object=new JSONObject(new String(responseBody));
												if(object.getInt("err")==1){
													addconner();//添加关注
													System.out.println("運行到添加關注");
													
												}else if(object.getInt("err")==0){
													subscribe_id=object.getString("subscribe_id");
													CannelGUANzhu();//取消
													System.out.println("取消關注");
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
										  public void onFailure(int statusCode, Header[] headers, byte[] 
												  responseBody, Throwable error) {
											  Dissloading();
										  };
										
									});
							 }
						}
					}
				});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hospital_index);
		mContext = this;
		intent = getIntent();
		hospital_name = (TextView) findViewById(R.id.hospital_name);
		attention_nums = (TextView) findViewById(R.id.attention_nums);
		hospital_img = (ImageView) findViewById(R.id.hospital_img);
		setupView();
		try {
			settop();
		} catch (SocketTimeoutException e) {
			// TODO Auto-generated catch block
			showToastNetTime();
			e.printStackTrace();
		}
		mGridView = (GridView) findViewById(R.id.index_hospital_gridview);
	
		getData();
		mAdapter = new MainGridViewAdapter(mContext);
		mAdapter.setList(Constant.getMenuList());
	    //mAdapter.setList(list);
		 mGridView.setAdapter(mAdapter);
		 
	    	mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				 Intent intent=null;
				switch (position) {
				case 1:
					
					intent=new Intent(mContext,SelectOfficesActivity.class);
					intent.putExtra("hospital_id", hospital_id);
					intent.putExtra("hospital_name", hospital_name1);
				    startActivity(intent);
					break;
				case 2:
					intent=new Intent(mContext,RegisOrderActivity.class);
					//intent.putExtra("", value);
					startActivity(intent);
					break;

				default:
					break;
				}

			
			}
		});
	}
	private void addconner() {

		if (NetworkUtil.isOnline(HospitalIndexActivity.this)) {
			AsyncHttpClient client = new AsyncHttpClient();
			String url = Constant.ADDATTENTION;
			client.setTimeout(5000);
			RequestParams params = new RequestParams();
			params.put("member_id", SharedPreferencesConfig
					.getStringConfig(mContext, "member_id"));
			params.put("object_id",
					intent.getStringExtra("hospital_id"));
			params.put("type", "1");
			client.post(url, params,
					new AsyncHttpResponseHandler() {
						public void onStart() {

						};
						public void onSuccess(int statusCode,Header[] headers,byte[] responseBody) {
							if (!CheckJson(responseBody).equals("")) {
								try {
									JSONObject object = new JSONObject(new String(responseBody));
									if (object.getInt("err") == 0) {
										ToastManager.getInstance(mContext).showToast(object.getString("msg"));
										((Button) findViewById(R.id.title_right_collect)).setBackgroundResource(R.drawable.btn_collect_pressed);		
									} else if (object.getInt("err") == 1) {
									}
								} catch (JSONException e) {
									// TODO Auto-generated
									// catch
									// block
									e.printStackTrace();
								}
							} else {
								showToast("没有数据");
							}
						};
						public void onFailure(int statusCode,Header[] headers,byte[] responseBody,
								Throwable error) {
							showToast("请重试");
						};

					});

		} else {
			ToastManager.getInstance(mContext).showToast(
					"当前无网络连接");
		}
		
	}
	
	private void CannelGUANzhu() {
		 if(NetworkUtil.isOnline(HospitalIndexActivity.this)){
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
								((Button) findViewById(R.id.title_right_collect)).
									setBackgroundResource(R.drawable.btn_collect_normal);
								}
		
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
	                  super.onSuccess(statusCode, headers, responseBody);
					}

				});
			 
			 
		 }
		
	}
	

	private void settop() throws SocketTimeoutException{

		if (NetworkUtil.isOnline(HospitalIndexActivity.this)) {

			AsyncHttpClient client = new AsyncHttpClient();
			String url = Constant.MAIN_HOSPITAL2;
			client.setTimeout(5000);
			RequestParams params = new RequestParams();
			params.put("hospital_id", intent.getStringExtra("hospital_id"));
			client.post(url, params, new AsyncHttpResponseHandler() {
				@Override
				public void onStart() {
					Showloading();
					super.onStart();
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					Dissloading();
				
					if (!CheckJson(responseBody).equals("")) {
						try {
							JSONObject object = new JSONObject(new String(
									responseBody));
							if (object.getInt("err") == 0) {
								JSONArray array = object
										.getJSONArray("hospital");
								for (int i = 0; i < array.length(); i++) {
									hospital_name.setText(array.getJSONObject(i).getString("name"));
									attention_nums.setText("关注量："+ array.getJSONObject(i).getString("subscribe_count"));
									BaseActivity.imageLoader.displayImage(Constant.URL_IMAGE_HOST_STRING+ 
									array.getJSONObject(i).getString("img"),
									hospital_img, BaseActivity.options,
									animateFirstListener);
									hospital_id=array.getJSONObject(i).getString("id");
									hospital_name1=array.getJSONObject(i).getString("name");
								}

							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						showToast("没有数据");
					}
					super.onSuccess(statusCode, headers, responseBody);
				}

				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					Dissloading();
					showToast("请重试");
				}
			});
		} else {
			ToastManager.getInstance(mContext).showToast("当前无网络连接");
		}

	}

	private void getData() {
		if (NetworkUtil.isOnline(HospitalIndexActivity.this)) {
			
			list = new ArrayList<HashMap<String, String>>();
			AsyncHttpClient client = new AsyncHttpClient();
			String url = Constant.MAIN_HOSPITAL;
			client.setTimeout(5000);
			RequestParams params = new RequestParams();
			params.put("hospital_id", intent.getStringExtra("hospital_id"));
			client.post(url, params, new AsyncHttpResponseHandler() {
				@Override
				public void onStart() {
					super.onStart();
				}
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					 showLog("0"+new String(responseBody));
					if (!CheckJson(responseBody).equals("")) {
						try {
							JSONObject object = new JSONObject(new String(responseBody));
							if (object.getInt("err") == 0) {
								JSONArray array = object.getJSONArray("hospital_module");
							    HashMap<String, String> map1 = new HashMap<String, String>();
								String id1=array.getJSONObject(0).getString("id");
								String status1=array.getJSONObject(0).getString("status");
                                if(status1.equals("1")){
                                	map1.put("classId", id1);
                                	map1.put("imageId", "btn_intelligent_normal");  
                                }else if(status1.equals("0")){
                                	map1.put("classId", id1);
                                	map1.put("imageId", "btn_intelligent_pressed"); 
                                }
                                list.add(map1);
                               
							    HashMap<String, String> map2 = new HashMap<String, String>();
                            	String id2=array.getJSONObject(1).getString("id");
								String status2=array.getJSONObject(1).getString("status");
                                if(status2.equals("1")){
                                	map2.put("classId", id2);
                                	map2.put("imageId", "btn_order_regis_normal");  
                                }else if(status2.equals("0")){
                                	map2.put("classId", id2);
                                	map2.put("imageId", "btn_order_regis_pressed"); 
                                }
                                list.add(map2);
                               
                                HashMap<String, String> map3 = new HashMap<String, String>();
                            	String id3=array.getJSONObject(2).getString("id");
								String status3=array.getJSONObject(2).getString("status");
                                if(status3.equals("1")){
                                	map3.put("classId", id3);
                                	map3.put("imageId", "btn_wait_doctor_normal");  
                                }else if(status2.equals("0")){
                                	map3.put("classId", id3);
                                	map3.put("imageId", "btn_wait_doctor_pressed"); 
                                }
                                list.add(map3);
                               
                                HashMap<String, String> map4 = new HashMap<String, String>();
                            	String id4=array.getJSONObject(3).getString("id");
								String status4=array.getJSONObject(3).getString("status");
                                if(status4.equals("1")){
                                	map4.put("classId", id4);
                                	map4.put("imageId", "btn_take_medicine_normal");  
                                }else if(status4.equals("0")){
                                	map4.put("classId", id4);
                                	map4.put("imageId", "btn_take_medicine_pressed"); 
                                }
                                list.add(map4);
                                
                                HashMap<String, String> map5 = new HashMap<String, String>();
                            	String id5=array.getJSONObject(4).getString("id");
								String status5=array.getJSONObject(4).getString("status");
                                if(status5.equals("1")){
                                	map5.put("classId", id5);
                                	map5.put("imageId", "btn_hospital_brief_normal");  
                                }else if(status5.equals("0")){
                                	map5.put("classId", id5);
                                	map5.put("imageId", "btn_hospital_brief_pressed"); 
                                }
                                list.add(map5);
                                
                                HashMap<String, String> map6 = new HashMap<String, String>();
                            	String id6=array.getJSONObject(5).getString("id");
								String status6=array.getJSONObject(5).getString("status");
                                if(status6.equals("1")){
                                	map6.put("classId", id6);
                                	map6.put("imageId", "btn_take_medicine_normal");  
                                }else if(status6.equals("0")){
                                	map6.put("classId", id6);
                                	map6.put("imageId", "btn_take_medicine_pressed"); 
                                }
                                list.add(map6);
                                
                                
                                HashMap<String, String> map7 = new HashMap<String, String>();
                            	String id7=array.getJSONObject(6).getString("id");
								String status7=array.getJSONObject(6).getString("status");
                                if(status7.equals("1")){
                                	map7.put("classId", id7);
                                	map7.put("imageId", "btn_wipe_off_normal");  
                                }else if(status7.equals("0")){
                                	map7.put("classId", id7);
                                	map7.put("imageId", "btn_wipe_off_pressed"); 
                                }
                                list.add(map7);
                                
                                HashMap<String, String> map8 = new HashMap<String, String>();
                            	String id8=array.getJSONObject(7).getString("id");
								String status8=array.getJSONObject(7).getString("status");
                                if(status8.equals("1")){
                                	map8.put("classId", id8);
                                	map8.put("imageId", "btn_referral_normal");  
                                }else if(status8.equals("0")){
                                	map8.put("classId", id8);
                                	map8.put("imageId", "btn_referral_normal"); 
                                }
                                list.add(map8);
                               
                                
                               /* HashMap<String, String> map9 = new HashMap<String, String>();
                            	String id9=array.getJSONObject(8).getString("id");
								String status9=array.getJSONObject(8).getString("status");
                                if(status9.equals("1")){
                                	map9.put("classId", id9);
                                	map9.put("imageId", "btn_hospital_services_normal");  
                                }else if(status9.equals("0")){
                                	map9.put("classId", id9);
                                	map9.put("imageId", "btn_hospital_services_normal"); 
                                }
                                list.add(map9);*/
                                
						}
						System.out.println("已经开通的项目有"+list.toString());
							
						
					} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						//showToast("数据异常");
					}
					super.onSuccess(statusCode, headers, responseBody);
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					// TODO Auto-generated method stub
					showToast("请重试");

				}

			});
		} else {
			ToastManager.getInstance(mContext).showToast("当前无网络连接");
		}

	}
	@Override
	protected void onResume() {
		
		if(NetworkUtil.isOnline(HospitalIndexActivity.this)){
			 AsyncHttpClient client = new AsyncHttpClient(); 
				String url = Constant.CONNER_zt;
				RequestParams params = new RequestParams();
				params.put("object_id",intent.getStringExtra("hospital_id"));
				params.put("member_id", SharedPreferencesConfig.getStringConfig
						(mContext, "member_id"));
				params.put("type", "1");
				client.post(url, params, new AsyncHttpResponseHandler(){
					  public void onSuccess(int statusCode, Header[] headers,
							  byte[] responseBody) {
						  if(!BaseActivity.CheckJson(responseBody).equals("")){ 
							try {
							JSONObject object=new JSONObject(new String(responseBody));
							if(object.getInt("err")==1){
								((Button) findViewById(R.id.title_right_collect)).
								   setBackgroundResource(R.drawable.btn_collect_normal);
							}else if(object.getInt("err")==0){
								  ((Button) findViewById(R.id.title_right_collect)).
								   setBackgroundResource(R.drawable.btn_collect_pressed);
							}
							  
							 } catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							  
							  
						  }
						  else{
							//	ToastManager.getInstance(mContext).showToast("数据异常");

						  }
						  
					  }
					
				});
				
				
		}

		super.onResume();
	} 

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				// 是否第一次显示
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					// 图片淡入效果
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}
