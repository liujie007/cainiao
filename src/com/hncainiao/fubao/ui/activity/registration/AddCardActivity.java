package com.hncainiao.fubao.ui.activity.registration;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.views.CustomerDialog;
import com.hncainiao.fubao.utils.IsIdCard;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author liujie
 * @version 2015年4月10日 下午6:40:42
 *         添加银行卡
 */
@SuppressLint("NewApi")
public class AddCardActivity extends BaseActivity {

	 Context mContext;
	 final Calendar c = Calendar.getInstance();
	 EditText edPeople,edBankNuam,edPeopleNum,edPhone,edCheckNum;
	 TextView tvTime;
	 TimeCount time;//
	 Button btnCheck;
	 String code="";//验证码
	 RelativeLayout type,timechoose;
	 TextView showCard;
	 CheckBox box;
	 IsIdCard iscard;
	/**
		 * ka类型
		 */
		private CustomerDialog dialog;
		RadioButton radioButton;
		ImageView Cannel;
		RadioGroup group;
		Spinner spBank;
		String Type="";//1储蓄卡，2信用卡
	 
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_bank_card);
		setTitle("添加银行卡");
		mContext = this;
		initView();
		getBank();
	}
	private void initView(){	
		spBank=(Spinner)findViewById(R.id.sp_bank);
		edPeople=(EditText)findViewById(R.id.person_name);//持卡人
		edBankNuam=(EditText)findViewById(R.id.ed_canum);//卡号
		edPeopleNum=(EditText)findViewById(R.id.ed_idnum);//身份证
		edPhone=(EditText)findViewById(R.id.ed_phonenum);//预留手机
		edCheckNum=(EditText) findViewById(R.id.code1);//验证码
		btnCheck=(Button) findViewById(R.id.Code1);
		type=(RelativeLayout) findViewById(R.id.cardTyoe);
		timechoose=(RelativeLayout) findViewById(R.id.timechoose);
		iscard=new IsIdCard();
		time = new TimeCount(60000, 1000);
		showCard=(TextView) findViewById(R.id.showcrad);
		dialog=new CustomerDialog(mContext);
		tvTime=(TextView) findViewById(R.id.timea);
		box=(CheckBox) findViewById(R.id.rad_sure);	
		((Button)findViewById(R.id.btn_bind_card)).setOnClickListener(this);//确定按钮
		btnCheck.setOnClickListener(this);
		type.setOnClickListener(this);
		timechoose.setOnClickListener(this);
	
	}
	List<String> mItems =new ArrayList<String>();
	List<String> mid =new ArrayList<String>();
	private void getBank()
	{
		if (NetworkUtil.isOnline(this)) {
			AsyncHttpClient getClient =new AsyncHttpClient();
			getClient.post(Constant.url_getbank, new AsyncHttpResponseHandler()
			{
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					super.onStart();
					Showloading();
				}
				@Override
				public void onSuccess(String content) {
					// TODO Auto-generated me
					// 建立Adapter并且绑定数据源
					Dissloading();
					if (content!=null) {
						try {
							JSONObject jsonObject = new JSONObject(content);
							if (jsonObject.getInt("err")==0) {
								//获取成功
								JSONArray jArray =jsonObject.getJSONArray("bank");
								for (int i = 0; i < jArray.length(); i++) {
									
									mItems.add(jArray.getJSONObject(i).getString("name"));
									mid.add(jArray.getJSONObject(i).getString("id"));
									
								}
								ArrayAdapter<String> _Adapter=new ArrayAdapter<String>(AddCardActivity.this,R.layout.add_bank_itme, mItems);
								//绑定 Adapter到控件
								spBank.setAdapter(_Adapter);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
							
					}
				
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					// TODO Auto-generated method stub
					Dissloading();
				}
			});
		}
	}
	/**
	 * 添加银行卡
	 */
	private void addBank()
	{
		if (NetworkUtil.isOnline(this)) {
			AsyncHttpClient httpClient =new AsyncHttpClient();
			RequestParams params =new RequestParams();
			params.put("member_id", SharedPreferencesConfig.getStringConfig(this, "member_id"));
			params.put("name", edPeople.getText().toString());
			params.put("type", Type);
			params.put("bank_id",mid.get(spBank.getSelectedItemPosition()) );
			params.put("cardno", edBankNuam.getText().toString());
			params.put("idnumber", edPeopleNum.getText().toString());
			params.put("phone", edPhone.getText().toString());
			params.put("expiretime", tvTime.getText().toString());
			showLog("添加银行卡："+params.toString());
			httpClient.post(Constant.url_addbankcard,params, new AsyncHttpResponseHandler()
			{
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
					 if (!CheckJson(responseBody).equals("")) {
						//开始解析数据
						 JSONObject jsonObject;
						try {
							jsonObject = new JSONObject(new String(responseBody));
							 if (jsonObject.getInt("err")==0) {
									showToast(""+jsonObject.getString("msg"));
									finish();
								}
								 else {
									 showToast(""+jsonObject.getString("msg"));
								}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					 else {
						showToast("添加银行卡失败！");
					}
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					// TODO Auto-generated method stub
					Dissloading();
					showToastNetTime();
				}
			});
		}
		else {
				showToastNotNet();
		}
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		
		case R.id.cardTyoe://卡类型选择
			
			SelectType();
			break;
		case R.id.timechoose:
			
			chossTime();//选择时间
			break;
		case R.id.del:
			dialog.dismiss();
			break;
		case R.id.Code1:
				try {
					GetCode();
				} catch (SocketTimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				
			}
		
			break;
		case R.id.btn_bind_card:
			if (check()) {
				addBank();
			}
			break;

		default:
			break;
		}
	}
	
	
	private boolean check()
	{
		if(edCheckNum.getText().toString().equals("")){
			showToast("验证码不能为空");
			return false;
		}
		if(!box.isChecked()){
			showToast("必须同意用户协议");
			return false;
		}
		if(edPhone.getText().toString().equals("")){
			showToast("电话号码不能为空");
			return false;
			
		}
		if(!code.equals(edCheckNum.getText().toString())){
			showToast("验证码输入不正确");
			return false;
		}
		if(!iscard.validateCard(edPeopleNum.getText().toString())){
			showToast("身份证号码有误");
			return false;
		}
		
		if(edPeopleNum.getText().toString().equals("")){
			showToast("身份证不能为空");
			return false;
			
		}
		

		if(Type.equals("")){
			showToast("请选择卡类型");
			return false;
		}
		if(Type.equals("2")){
			if(tvTime.getText().toString().equals("")){
				showToast("请选择有效期");
				return false;
			}
			
		}
		if(edBankNuam.getText().toString().equals("")){
			showToast("卡号不能为空");
			return false;
			
		}
		if(edPeople.getText().toString().equals("")){
			
			showToast("持卡人不能为空");
			return false;
		}
		return true;
	}
	
	/**
	 * 获取验证码
	 * */
	private void GetCode() throws SocketTimeoutException{
		if(NetworkUtil.isOnline(mContext)){
			AsyncHttpClient client=new AsyncHttpClient();
			String url=Constant.YZM_STRING;
			client.setTimeout(5000);
			RequestParams params=new RequestParams();
			params.put("phone",edPhone.getText().toString() );
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
								code=object.getString("vcode");
								time.start();
								
							}else{
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
					Dissloading();
					showToast("请重试");
				}
				
			});
			
			
			
			
		}else{
			showToast("无网络连接");
		}
		
	
		
	}
	

	
	/**
	 * 选择时间
	 */
	private void  chossTime()
	{
		new DatePickerDialog(mContext,
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub
						if (year>=c.get(Calendar.YEAR)) {
							if (monthOfYear>=c.get(Calendar.MONTH)) {
							
								if (monthOfYear==c.get(Calendar.MONTH)) {
									if (dayOfMonth>c.get(Calendar.DAY_OF_MONTH)) {
										showLog(c.get(Calendar.YEAR)+"-"+year);
										String startTime =  year+ "年"+ (((monthOfYear + 1) < 10) ? ("0" + (monthOfYear + 1))
												: (monthOfYear + 1))
										+ "月"+ (dayOfMonth < 10 ? ("0" + dayOfMonth)+"日": dayOfMonth+"日");
							
											tvTime.setText(startTime);
									}
									else {
										showToast("日期不能选择之前的时间！");
									}
								}
								else {
									showLog(c.get(Calendar.YEAR)+"-"+year);
									String startTime =  year+ "-"+ (((monthOfYear + 1) < 10) ? ("0" + (monthOfYear + 1))
											: (monthOfYear + 1))
									+ "-"+ (dayOfMonth < 10 ? ("0" + dayOfMonth): dayOfMonth);
						
										tvTime.setText(startTime);
								}
								
							}	
							else {
								showToast("日期不能选择之前的时间！");
							}
								
						}
						else {
							showToast("日期不能选择之前的时间！");
						}
				         
					
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
				(c.get(Calendar.DAY_OF_MONTH))+1).show();
		
		
	}

	private void SelectType() {
		
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.add_bank_type, null);
		dialog.setDialogView(view);
		dialog.setDialogPostion(Gravity.CENTER, 0, 0,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		dialog.setDialgCancelOutSide(true);
		dialog.show();
		Cannel=(ImageView) view.findViewById(R.id.del);
		group=(RadioGroup) view.findViewById(R.id.group);
		radioButton=(RadioButton) group.findViewById(group.getCheckedRadioButtonId());
		group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				radioButton=(RadioButton)group.findViewById(arg1);
				if(radioButton.getText().toString().equals("储蓄卡")){
					Type=1+"";
					showCard.setText("储蓄卡");
					timechoose.setVisibility(ViewGroup.GONE);
					dialog.dismiss();
				}
				if(radioButton.getText().toString().equals("信用卡")){
					Type=2+"";
					showCard.setText("信用卡");
					timechoose.setVisibility(ViewGroup.VISIBLE);
					dialog.dismiss();
					
				}
			}
			
		});
		
		Cannel.setOnClickListener(this);

	}
		

		
	
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {// 计时完毕时触发
			btnCheck.setText("重新验证");
			btnCheck.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			btnCheck.setClickable(false);
			btnCheck.setText("请等待" + millisUntilFinished / 1000 + "秒");
		}
   }
}

	

