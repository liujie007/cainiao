package com.hncainiao.fubao.ui.fragment;

import java.util.HashMap;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.hncainiao.fubao.R;
import com.hncainiao.fubao.application.IsLogin;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.hospital.SelectHospitalActivity;
import com.hncainiao.fubao.ui.activity.login.LoginActivity;
import com.hncainiao.fubao.ui.activity.message.MyMessageActivity;
import com.hncainiao.fubao.ui.activity.personalcenter.MyFollowActivity;
import com.hncainiao.fubao.ui.activity.personalcenter.RegisOrderActivity;
import com.hncainiao.fubao.ui.activity.registration.Registration_search;
import com.hncainiao.fubao.ui.listener.OnClickAvoidForceListener;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.hncainiao.fubao.utils.ToastManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author liujie
 * @version 2015年4月15日 下午1:34:28
 * 
 *          首页
 */
public class IndexFragment extends BaseFragment implements BDLocationListener{

	private Context mContext;
	LocationClient mLocClient;
	private View view;
	TextView messageNums,tvMsg;//未读消息数量
	LinearLayout linyytj,linyygh,linyysc,yyzf,yysc,yyseach,lin_msg;
	private void setupView(View view) {
		location();
		((TextView)view.findViewById(R.id.tv_logo)).setVisibility(View.VISIBLE);
		((RelativeLayout)view.findViewById(R.id.rl_select_city)).setVisibility(View.VISIBLE);
		((Button)view.findViewById(R.id.comeback)).setVisibility(View.GONE);
		linyytj=(LinearLayout)view.findViewById(R.id.lin_yytj);
		linyygh=(LinearLayout)view.findViewById(R.id.lin_yygh);
		linyysc=(LinearLayout)view.findViewById(R.id.lin_jksc);
		lin_msg=(LinearLayout)view.findViewById(R.id.lin_msg);
		yyzf=(LinearLayout)view.findViewById(R.id.lin_wdzf);
		yysc=(LinearLayout)view.findViewById(R.id.lin_wdsc);
		yyseach=(LinearLayout)view.findViewById(R.id.lin_seach);
		tvMsg=(TextView)view.findViewById(R.id.tv_msg);
	}

	private void addListener() {
		linyytj.setOnClickListener(l);
		linyygh.setOnClickListener(l);
		linyysc.setOnClickListener(l);
		yyzf.setOnClickListener(l);
		yysc.setOnClickListener(l);
		yyseach.setOnClickListener(l);
		tvMsg.setOnClickListener(l);
		lin_msg.setOnClickListener(l);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_index, null);
		mContext = getActivity();
		setupView(view);
		addListener();
		return view;
	}
	
	private void location()
	{
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
	public void onResume() {
		// TODO Auto-generated method stub
		getData();
		super.onResume();
	}

	private void getData() {
		if(NetworkUtil.isOnline(mContext)){
			AsyncHttpClient client=new AsyncHttpClient();
			String url=Constant.url_my_message;
			client.setTimeout(5000);
			RequestParams params=new RequestParams();
			params.put("member_id", SharedPreferencesConfig.getStringConfig(mContext, "member_id"));
			params.put("page", "");
			client.post(url, params, new AsyncHttpResponseHandler(){
				 @Override
				public void onStart() {
					// TODO Auto-generated method stub
					super.onStart();
				}
				 @Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					   showLog("首页消息"+new String(responseBody));
					 if(!BaseActivity.CheckJson(responseBody).equals("")){
						 try {
								JSONObject object=new JSONObject(new String(responseBody));
								if(object.getInt("err")==0){
								 JSONArray array=object.getJSONArray("message");
								// for (int i = 0; i < array.length(); i++) {
									//if (array.getJSONObject(i).getString("status").equals("1")) {
									//	messageNums.setVisibility(ViewGroup.VISIBLE);
										tvMsg.setText(array.getJSONObject(0).getString("content"));
									
								//}
									
								}else if(object.getInt("err")==1){
									//ToastManager.getInstance(mContext).showToast(object.getString("msg"));
								}
							 } catch (JSONException e) {
								
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					 }else{
						 ToastManager.getInstance(mContext).showToast("没有数据");
					 }
										 
					 
					super.onSuccess(statusCode, headers, responseBody);
				}
				 @Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					// TODO Auto-generated method stub

				}
				
			});
			
			
		}else{
			ToastManager.getInstance(mContext).showToast("当前无网络连接");
		}		
		
	}

	private OnClickAvoidForceListener l = new OnClickAvoidForceListener() {

		@Override
		public void onClickAvoidForce(View v) {
			// TODO Auto-generated method stub 
			Intent intent = null;
			switch (v.getId()) {
			case R.id.lin_yytj: //预约体检
				showToast("正在开发，敬请期待！");
			/*	intent = new Intent(mContext, PhyExamActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			*/	break;
			case R.id.lin_yygh: // 申请挂号
				/*intent = new Intent(mContext, RegistrationActivity.class);
				startActivity(intent);*/
				intent = new Intent(mContext, SelectHospitalActivity.class);
				intent.putExtra("flag", "registration");
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

				break;
			case R.id.lin_jksc: // 健康商场

				//startActivity(new Intent(getActivity(),IndexHealthActivity.class));
				intent=new Intent(mContext,Registration_search.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				break;
			case R.id.lin_wdzf:
				//我的支付
				showToast("正在开发，敬请期待！");
				/*if (IsLogin.isLogin()) {
					intent=new Intent(mContext,MainMyPay.class);
					startActivity(intent);
				}
				else {
					showToast("请先登录！");
					intent=new Intent(mContext,LoginActivity.class);
					startActivity(intent);
				}
				
				getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		*/
				break;
			case R.id.lin_wdsc:
				//我的预约
				if (IsLogin.isLogin()) {
					intent=new Intent(mContext,RegisOrderActivity.class);
					startActivity(intent);
				}
				else {
					showToast("请先登录！");
					intent=new Intent(mContext,LoginActivity.class);
					startActivity(intent);
				}
				
				getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		
				break;
			case R.id.lin_seach:
				//我的关注
				if (IsLogin.isLogin()) {
					intent=new Intent(mContext,MyFollowActivity.class);
					startActivity(intent);
				}
				else {
					showToast("请先登录！");
					intent=new Intent(mContext,LoginActivity.class);
					startActivity(intent);
				}
				getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		
				break;
			case R.id.lin_msg:
				//系统消息
				if (!IsLogin.isLogin()) {
					showToast("请先登录！");
					intent=new Intent(mContext,LoginActivity.class);
					startActivity(intent);
				}
				else {
					startActivity(new Intent(getActivity(),MyMessageActivity.class));
					getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				
				}
				
				break;
			/*case R.id.ib_doctor_consult: // 医生咨询

				//startActivity(new Intent(getActivity(),IndexConsultActivity.class));
				intent=new Intent(mContext,IndexConsultationActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

				break;
			case R.id.ib_ucc: // 急救中心
				startActivity(new Intent(getActivity(),IndexUCCActivity.class));
				getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				break;
			case R.id.btn_message:
				//信息列表
				startActivity(new Intent(getActivity(),MyMessageActivity.class));
				getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				break;
			case R.id.message_text:
				startActivity(new Intent(getActivity(),MyMessageActivity.class));
				getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

				break;*/
			}
		}
	};

	@Override
	protected void creatFragmentUI() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReceiveLocation(BDLocation arg0) {
		// TODO Auto-generated method stub
		if (arg0!=null) {
			HashMap<String, Object> map=new HashMap<String, Object>();
			map.put("lng", arg0.getLongitude());
		  	map.put("lat", arg0.getLatitude());
			BaseActivity.setMap(map, "location");
			mLocClient.stop();	
		}
	}
}
