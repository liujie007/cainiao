package com.hncainiao.fubao.ui.activity.personalcenter;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.utils.IsIdCard;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.hncainiao.fubao.utils.ToastManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AddUsuallyPatient extends BaseActivity {
	
	Context mContext;
	EditText name,idcard,phone,ka_num;
	Button save;
	String relate=4+"";//就诊人关系 0 本人  1家人  2 朋友
	RadioGroup group;
	RadioButton myself,family,friend,radiobutton;
	IsIdCard boolCard;
	LinearLayout choosesex;
	TextView sex;
	String gender="1";
	Dialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_usually_patient);
		InitView();
		listen();
	}

	private void InitView() {
		mContext=this;
		setTitle("添加就诊人");
		name=(EditText) findViewById(R.id.edit_name);
		idcard=(EditText) findViewById(R.id.edit_idcard);
		//sex=(EditText) findViewById(R.id.edit_sex);
		//age=(EditText) findViewById(R.id.edit_age);
		phone=(EditText) findViewById(R.id.edit_phone);
	//	ka_num=(EditText) findViewById(R.id.ka_num);
		save=(Button) findViewById(R.id.save);
		group=(RadioGroup) findViewById(R.id.radiogroup);
		myself=(RadioButton) findViewById(R.id.myself);
		family=(RadioButton) findViewById(R.id.family);
		friend=(RadioButton) findViewById(R.id.friend);
		choosesex=(LinearLayout) findViewById(R.id.llchoosesex);
		sex=(TextView) findViewById(R.id.boyorgril);
		boolCard=new IsIdCard();
		choosesex.setOnClickListener(new l());

	
	}
	class l implements View.OnClickListener{

		@Override
		public void onClick(View arg0) {
			if(arg0.getId()==R.id.llchoosesex){
				choseSexDialog();
			}
			if(arg0.getId()==R.id.tv_wem){
				gender="0";
				sex.setText("女");
				dialog.dismiss();
			}
			if(arg0.getId()==R.id.tv_mem){
				gender="1";
				sex.setText("男");
				dialog.dismiss();
			}
			
			
		}
		
	}
	
	
	
	
	
	private void listen(){
		
		save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if(!NUlledit()){
					addUsually();//添加就診人
					Intent intent=new Intent(mContext,UsuallyPatient.class);
					startActivity(intent);

				}else{
					
						if(relate.equals("4")){
						showToast("请选择关系");
					}if(!Isphone(phone.getText().toString())){
						showToast("电话号码不正确");
						
					}if(!boolCard.validateCard(idcard.getText().toString())){
						showToast("身份证号码不正确");
					}if(name.getText().toString().equals("")){
						showToast("姓名不能为空");
					}
					
				}
			}
		});
		
		phone.setOnFocusChangeListener(new OnFocusChangeListener(){

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if(!arg1){
					if(!Isphone(phone.getText().toString())){
						showToast("请输入正确的手机号码");
					}
				}
			}
			
		});
		
		
		radiobutton=(RadioButton) group.findViewById(group.getCheckedRadioButtonId());

		group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				radiobutton=(RadioButton)group.findViewById(arg1);
				if(radiobutton.getText().toString().equals("本人")){
					relate=0+"";
				}
				if(radiobutton.getText().toString().equals("家人")){
					relate=1+"";
				}
				if(radiobutton.getText().toString().equals("朋友")){
					relate=2+"";
				}

				
			}
			
		});
		
		
	}
	
	/**
	 * 性别选择框
	 */
	private void choseSexDialog()
	{
		View view =LayoutInflater.from(this).inflate(R.layout.chose_sex_diaglog, null);
		TextView tvMem=(TextView)view.findViewById(R.id.tv_mem);
		TextView tvWem=(TextView)view.findViewById(R.id.tv_wem);
		tvMem.setOnClickListener(new l());
		tvWem.setOnClickListener(new l());
		dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(view);
		dialog.show();
		
	}
	
	
	private boolean NUlledit(){
		if(CheckEditViewNull(name)||CheckEditViewNull(phone)
				||CheckEditViewNull(idcard)
				||!Isphone(phone.getText().toString())||
				!boolCard.validateCard(idcard.getText().toString())
				||relate.equals("4")){
			return true;
			
		}
		return false;
	}
	
	private void addUsually() {
		// TODO Auto-generated method stub
		if(NetworkUtil.isOnline(mContext)){
			AsyncHttpClient client=new AsyncHttpClient();
			String url=Constant.ADDJIUZHENREN;
			client.setTimeout(5000);
			RequestParams params=new RequestParams();
			params.put("name",name.getText().toString().trim());
			params.put("phone",phone.getText().toString().trim());
			params.put("member_id",SharedPreferencesConfig.getStringConfig(mContext, "member_id"));
			params.put("idnumber",idcard.getText().toString().trim());
			//params.put("cardno",ka_num.getText().toString().trim());
			params.put("relate", relate);
			params.put("gender", gender);
			
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
					Dissloading();
					System.out.println("新建------------------"+new String(responseBody));
					if(!CheckJson(responseBody).equals("")){
						try {
							JSONObject object=new JSONObject(new String(responseBody));
							if(object.getInt("err")==0){
								ToastManager.getInstance(mContext).showToast(object.getString("msg"));
								
							}else{
								ToastManager.getInstance(mContext).showToast(object.getString("msg"));
							}
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						showToast("沒有数据");
					}
					
					super.onSuccess(statusCode, headers, responseBody);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					// TODO Auto-generated method stub
					Dissloading();
				}
				
				
			});
		}else{
			ToastManager.getInstance(mContext).showToast("无网络连接");
		}
		
		
	}
	
	
	

}
