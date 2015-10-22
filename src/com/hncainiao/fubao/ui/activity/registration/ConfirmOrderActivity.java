package com.hncainiao.fubao.ui.activity.registration;

import java.net.SocketTimeoutException;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.login.LoginActivity;
import com.hncainiao.fubao.ui.activity.personalcenter.PersonMyBank;
import com.hncainiao.fubao.ui.activity.personalcenter.RegisOrderMessage;
import com.hncainiao.fubao.ui.listener.OnClickAvoidForceListener;
import com.hncainiao.fubao.ui.views.CustomAlertDialog;
import com.hncainiao.fubao.ui.views.CustomerDialog;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.hncainiao.fubao.utils.ToastManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author liujie
 * @version 2015年4月10日 下午2:35:14
 * 
 *          确认订单
 */
public class ConfirmOrderActivity extends BaseActivity {

	// private static final String TAG = "ConfirmOrderActivity";

	private Context mContext;
	/**
	 * 银行 医院
	 */
	private int payway =1;//判断到院支付， 银联支付的标志  
	
	private RelativeLayout rlBank, rlHospital;
	private ImageView ivBank, ivHospital;
	
	/**
	 * 输入验证码dialog
	 */
	private CustomerDialog dialogcode;
	String id;
	
	/**
	 * 输入验证码iew
	 * 
	 */
    TextView telephone,sendagain;
    Button submitcode,cannelcode;
    EditText  code;
    String  phonenum="";
    String  codeyzm="";
    private  TimeCount time1;
	
	private Button btnConfirm;
	private  TextView doctor_name,hospital_name,keshi_name,time,money,type 
	                  ,patient_name,IDcard,jiuzhen_kahao,phone;
	/*
	 * 自定义的对话框
	 */
	private CustomAlertDialog dialog;
	private String jkao;//就诊卡号
	private void setupView() {
		setTitle("确认订单");
		rlBank = (RelativeLayout) findViewById(R.id.rl_select_bank);
		rlHospital = (RelativeLayout) findViewById(R.id.rl_select_hospital);
		ivBank = (ImageView) findViewById(R.id.iv_select_bank);
		ivHospital = (ImageView) findViewById(R.id.iv_select_hospital);
		btnConfirm = (Button) findViewById(R.id.btn_confirm_order);
	}

	private void addListener() {
		rlBank.setOnClickListener(listener);
		rlHospital.setOnClickListener(listener);
		btnConfirm.setOnClickListener(listener);
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_order);
		mContext = this;
		jkao=getIntent().getStringExtra("edjkao");
		setupView();
		initView();
		getDoctorData();
		getPatientData();
		addListener();
	}

	private void getPatientData() {
		if(NetworkUtil.isOnline(mContext)){
			AsyncHttpClient client=new AsyncHttpClient();
			String url=Constant.Jiuzhenren_Juti;
			client.setTimeout(5000);
			RequestParams params=new RequestParams();
			params.put("patient_id", SharedPreferencesConfig.getStringConfig(mContext, "patient_id"));
			System.out.println("当前选择ID"+SharedPreferencesConfig.getStringConfig(mContext, "patient_id"));
			client.post(url, params, new AsyncHttpResponseHandler(){
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					try {
						JSONObject object=new JSONObject(new String(responseBody));
						if(object.getInt("err")==0){
							JSONArray array=object.getJSONArray("patient");
							for(int i=0;i<array.length();i++){
								patient_name.setText(array.getJSONObject(i).getString("name"));
								IDcard.setText(array.getJSONObject(i).getString("idnumber"));
								//jiuzhen_kahao.setText(array.getJSONObject(i).getString("cardno"));
								phonenum=array.getJSONObject(i).getString("phone");
								phone.setText(phonenum);
	
							}
						
						}
	
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					super.onSuccess(statusCode, headers, responseBody);
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					// TODO Auto-generated method stub
					ToastManager.getInstance(mContext).showToast("当前无网络连接");
				}
				
			});
			
		}else{
			ToastManager.getInstance(mContext).showToast("当前无网络连接");
		}
		
		
	}

	private void initView() {
		doctor_name=(TextView) findViewById(R.id.tv_order_doctor_name);
		hospital_name=(TextView) findViewById(R.id.confire_hospital_name);
		keshi_name=(TextView) findViewById(R.id.confire_keshi);
		time=(TextView) findViewById(R.id.confire_time);
		money=(TextView) findViewById(R.id.confire_money);	
		type=(TextView) findViewById(R.id.confire_type);
		patient_name=(TextView) findViewById(R.id.confire_name);
		IDcard=(TextView) findViewById(R.id.IDcard);
		jiuzhen_kahao=(TextView) findViewById(R.id.confire_kahao);
		jiuzhen_kahao.setText(jkao);
		if (jkao.equals("")) {
			((RelativeLayout)findViewById(R.id.rela_jiuka)).setVisibility(View.GONE);

		}
		phone=(TextView) findViewById(R.id.confire_phone);
		dialogcode=new CustomerDialog(mContext);
		time1 = new TimeCount(60000, 1000);

	}

	private void getDoctorData() {
		if(NetworkUtil.isOnline(mContext)){
			AsyncHttpClient client=new AsyncHttpClient();
			String url=Constant.ORDER_JUTI;
			client.setTimeout(5000);
			RequestParams params=new RequestParams();
			params.put("schedule_id", SharedPreferencesConfig.getStringConfig(mContext, "zuozhen_num"));
			client.post(url, params, new AsyncHttpResponseHandler(){
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					super.onStart();
					Showloading();
				}
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					Dissloading();
					try {
						JSONObject object=new JSONObject(new String(responseBody));
						if(object.getInt("err")==0){
							JSONArray array=object.getJSONArray("schedule");
							for(int i=0;i<array.length();i++){
								doctor_name.setText(array.getJSONObject(i).getString("doctor_name"));
								hospital_name.setText(array.getJSONObject(i).getString("hospital_name"));
								keshi_name.setText(array.getJSONObject(i).getString("department_name"));
								
								time.setText(array.getJSONObject(i).getString("clinic_date")+
										//array.getJSONObject(i).getString("dayofweek")+
										array.getJSONObject(i).getString("clinic_time"));
								money.setText(array.getJSONObject(i).getString("register_fee")+"元");
								
								if(array.getJSONObject(i).getString("type").equals("1")){
									type.setText("普通门诊");
								}else{
									type.setText("专家门诊");
								}
								
							}
						}
						}catch (JSONException e) {
								// TODO: handle exception
							}
					
					super.onSuccess(statusCode, headers, responseBody);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					Dissloading();
					ToastManager.getInstance(mContext).showToast("获取信息失败");

				}
				
			});
		}else{
			ToastManager.getInstance(mContext).showToast("当前无网络连接");
		}
		
	}

	private void PrintfCode() {
		View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_itemlay, null);
		dialogcode.setDialogView(view);
		dialogcode.setDialogPostion(Gravity.CENTER, 0, 0,LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		dialogcode.setDialgCancelOutSide(false);
		dialogcode.show();
		telephone=(TextView) view.findViewById(R.id.telephone);
		telephone.setText("已经向"+phonenum+"手机发送验证码");
		sendagain=(TextView)view. findViewById(R.id.sendagin);
		submitcode=(Button)view. findViewById(R.id.btn_submit);
		cannelcode=(Button) view.findViewById(R.id.cannel);
		code=(EditText) view.findViewById(R.id.code_editview);
		sendagain.setOnClickListener(listener);
		submitcode.setOnClickListener(listener);
		cannelcode.setOnClickListener(listener);
		
		
	}
	
	private void Addguahao(String paytype) {
		if(NetworkUtil.isOnline(mContext)){
			AsyncHttpClient client=new AsyncHttpClient();
			String url=Constant.CreatOrder;
			client.setTimeout(5000);
			RequestParams params=new RequestParams();
			params.put("paytype", paytype);
			params.put("card_id", ""+jkao);
			params.put("schedule_id", SharedPreferencesConfig.getStringConfig(mContext, "zuozhen_num"));
			params.put("patient_id",  SharedPreferencesConfig.getStringConfig(mContext, "patient_id"));
			params.put("member_id",SharedPreferencesConfig.getStringConfig(mContext, "member_id") );
			//params.put("order", "12345"+(new Random().nextInt(899999)+100000)+"");
			showLog("提交挂号信息提交："+params.toString());
			client.post(url, params, new AsyncHttpResponseHandler(){
				
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					Showloading();
					super.onStart();
				}
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					// TODO Auto-generated method stub
					showLog("提交挂号信息返回："+new String(responseBody));
					 Dissloading();
					 /*
					  * {"\u64cd\u4f5c\u6210\u529f":{"register_id":352,"order_id":"124923"},"err":0}
					  */
					 //13975884188  
					try {
						JSONObject object=new JSONObject(new String(responseBody));
						
						if(object.getInt("err")==0){
							if (payway==1) {
								//银联支付﻿{"data":{"register_id":507},"err":0}
								Intent intent =new Intent(ConfirmOrderActivity.this,PersonMyBank.class);
								intent.putExtra("chosebank", "请选择银行卡进行支付");
								intent.putExtra("flag", "挂号");
								intent.putExtra("register_id", object.getJSONObject("data").getString("register_id"));
								intent.putExtra("txnAmt", money.getText().toString().substring(0, money.getText().toString().length()-1));
								startActivity(intent);
							}
							else
							{
									dialogcode.dismiss();
									id=object.getJSONObject("data").getString("register_id");
									Intent intent=new Intent(mContext,RegisOrderMessage.class);
									intent.putExtra("register_d", id);
									intent.putExtra("message_id", "");
									startActivity(intent);
							}
									
								}else{
									Dissloading();
									showToast(object.getString("msg"));
								}
		
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					// TODO Auto-generated method stub
					showLog("提交体检信息返回：失败");
					Dissloading();
				}
					
			});
			

			
		}else{
			showToast("无网络连接");
		}
		
		
		
		
	}
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {// 计时完毕时触发
			sendagain.setText("重新验证");
			sendagain.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			sendagain.setClickable(false);
			sendagain.setText("请等待" + millisUntilFinished / 1000 + "秒");
		}
	}
	private OnClickAvoidForceListener listener = new OnClickAvoidForceListener() {

		@Override
		public void onClickAvoidForce(View v) {
			// TODO Auto-generated method stub
			Intent intent = null;
			switch (v.getId()) {
			case R.id.rl_select_bank:
				     payway=1;
					ivBank.setImageResource(R.drawable.img_select);
					ivHospital.setImageResource(R.drawable.img_unselect);
					rlBank.setBackgroundResource(R.drawable.corners_selected_bg);
					rlHospital.setBackgroundResource(R.drawable.corners_unselected_bg);
				
				//银联支付
				break;
			case R.id.rl_select_hospital:
				    payway=0;
					ivHospital.setImageResource(R.drawable.img_select);
					ivBank.setImageResource(R.drawable.img_unselect);
					rlHospital.setBackgroundResource(R.drawable.corners_selected_bg);
					rlBank.setBackgroundResource(R.drawable.corners_unselected_bg);
				break;
			case R.id.btn_confirm_order: // 确认订单
				if(SharedPreferencesConfig.getStringConfig(mContext, "member_id").equals("")){
					intent =new Intent(mContext,LoginActivity.class);
					startActivity(intent);
				}else{
					dialog = new CustomAlertDialog(mContext, listener);
					dialog.setTitle("提 示");
					dialog.setMessage("确认提交订单吗?");
				}
				
				break;
			case R.id.btn_submit:
				
				if(codeyzm.equals(code.getText().toString())&&!code.getText().toString().equals("")){
					Addguahao("1");//添加挂号
				}else{
					showToast("您输入的验证码不正确");
				}
				break;
			case R.id.sendagin:
				try {
					GetCode();

				} catch (SocketTimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
			case R.id.cannel:
				dialogcode.dismiss();
				break;
				
				
			case R.id.btn_custom_alertdialog_confirm:
				
				if (dialog != null) {
					dialog.dismiss();
				}
				if(payway==1){
					
					Addguahao("2");//银联支付
					
					break;
				}else if(payway==0){
					 
					showToast("功能正在完善，暂时未开放！");
					/*  try {
						GetCode();
					} catch (SocketTimeoutException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					  PrintfCode();//验证码弹窗
*/					 
					break;
				}
			
			}
		}

		
		
	};
	private void GetCode() throws SocketTimeoutException{
		if(NetworkUtil.isOnline(mContext)){
			AsyncHttpClient client=new AsyncHttpClient();
			String url=Constant.YZM_STRING;
			client.setTimeout(5000);
			RequestParams params=new RequestParams();
			params.put("phone", phonenum);
			System.out.println("获取验证码的手机"+phonenum);
			client.post(url, params, new AsyncHttpResponseHandler(){
				
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					super.onStart();
					Showloading();
				}
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					// TODO Auto-generated method stub
					Dissloading();
					super.onSuccess(statusCode, headers, responseBody);
					System.out.println("获得验证码"+new String(responseBody));
						
						try {
							System.out.println("开始获取");
							JSONObject object=new JSONObject(new String(responseBody));
							if(object.getInt("err")==0){
								System.out.println("获取成功");
								codeyzm=object.getString("vcode");
								time1.start();
								
							}else{
								showToast(object.getString("msg"));
								dialogcode.dismiss();
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					// TODO Auto-generated method stub
					Dissloading();
					showToast("请重试");
					dialogcode.dismiss();
				}
				
			});
			
			
			
			
		}else{
			showToast("无网络连接");
		}
		
	
		
	}

	
	
}
