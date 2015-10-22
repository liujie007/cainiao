package com.hncainiao.fubao.ui.activity.registration;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.location.Location_hospital;
import com.hncainiao.fubao.ui.activity.login.LoginActivity;
import com.hncainiao.fubao.ui.adapter.Patient_Adape;
import com.hncainiao.fubao.ui.adapter.Patient_Adape.ViewHolder;
import com.hncainiao.fubao.ui.listener.OnClickAvoidForceListener;
import com.hncainiao.fubao.ui.views.CustomerDialog;
import com.hncainiao.fubao.utils.IsIdCard;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.hncainiao.fubao.utils.ToastManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author zhaojing
 * @version 2010年8月9日 上午11:33:58
 * 
 *          挂号信息
 */
public class RegistrationMessageActivity extends BaseActivity {

	private static final String TAG = "RegistrationMessageActivity";

	private Context mContext;
	IsIdCard boolcard;//判断是否是身份证
	TextView sex;
	LinearLayout ll_selectsex;
	Dialog dialog2;
	String gender=1+"";
	
	TextView hospital_adr,hospital_name,keshi_name,doctor_name,time,money;
	boolean isjiukao=true;

	private ImageButton btnAdd;
	
	ListView jiuzhen_person;
	RelativeLayout hospital_adrmessage;

	private EditText etName, etID, etCard, etPhone;

	private Button btnCancel, btnConfirm;

	private Button btnSubmit;
	RadioGroup group;
	RadioButton radioButton;
//	int tag=0;//是否选择了就诊人

	private ImageView ivLocation,choose; // 定位
	
	Patient_Adape adapter;
	
	List<Map<String, Object>>mList=new ArrayList<Map<String,Object>>();
	String relate="4";
	
	List<Map<Integer,Boolean>>Select_base=new ArrayList<Map<Integer,Boolean>>();
	int flag=-1;
	
	int currentpeople=0;//当前就诊人位数
	EditText edJkao;
	RelativeLayout rela_jiu_hao;
	View view_jiu_hao;

	/**
	 * 新建就诊人对话框
	 */
	private CustomerDialog dialog;

	private void setupView() {
		setTitle("挂号信息");
		ivLocation = (ImageView) findViewById(R.id.hospital_adress);
		btnAdd = (ImageButton) findViewById(R.id.btn_add_person);
		btnSubmit = (Button) findViewById(R.id.btn_submit);
		edJkao=(EditText)findViewById(R.id.ed_jiu_hao);
		
		 rela_jiu_hao=(RelativeLayout)findViewById(R.id.rela_jiu_hao);
		 view_jiu_hao=(View)findViewById(R.id.view_jiu_hao);
	}

	private void addListener() {
		ivLocation.setOnClickListener(listener);
		btnAdd.setOnClickListener(listener);
		btnSubmit.setOnClickListener(listener);
		hospital_adrmessage.setOnClickListener(listener);
		//jiuzhen_person.setItemsCanFocus(false);
		jiuzhen_person.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		jiuzhen_person.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(
					AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
			final ViewHolder holder=(ViewHolder) arg1.getTag();
			    holder.choose.toggle();
			    flag=arg2;
				//adapter.isSelected.put(arg2, holder.choose.isChecked());	
				adapter.notifyDataSetChanged();		
		}
			
	});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration_message);
		mContext = this;
		initView();
		setupView();
		setData();
		addListener();
		dialog = new CustomerDialog(mContext);
		try {
			showList();
		} catch (SocketTimeoutException e) {
			// TODO Auto-generated catch block
			showToastNetTime();
			e.printStackTrace();
		}
	}

	private void setData() {
		
		if(NetworkUtil.isOnline(mContext)){
			AsyncHttpClient client=new AsyncHttpClient();
			String url=Constant.ORDER_JUTI;
			client.setTimeout(5000);
			Intent intent=getIntent();
			RequestParams params=new RequestParams();
			params.put("schedule_id", intent.getStringExtra("zuozhen_num"));
			showLog(params.toString());
			client.post(url, params, new AsyncHttpResponseHandler(){
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					super.onStart();
				}
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
						if(!CheckJson(responseBody).equals("")){
						try {
							JSONObject object=new JSONObject(new String(responseBody));
							if(object.getInt("err")==0){
								JSONArray array=object.getJSONArray("schedule");
								for(int i=0;i<array.length();i++){
									hospital_adr.setText(array.getJSONObject(i).getString("address"));
									hospital_name.setText(array.getJSONObject(i).getString("hospital_name"));
									if (array.getJSONObject(i).getString("his").equals("trasen")) {
										//不需要就诊卡号
										 rela_jiu_hao.setVisibility(View.GONE);
										 view_jiu_hao.setVisibility(View.GONE);
										 isjiukao=true;
									}
									else {
										isjiukao=false;
									}
									if(array.getJSONObject(i).getString("type").equals("1")){
									  keshi_name.setText(array.getJSONObject(i).getString("department_name")+"普通门诊");

									}else{
										keshi_name.setText(array.getJSONObject(i).getString("department_name")+"专家门诊");
									}
									doctor_name.setText(array.getJSONObject(i).getString("doctor_name"));
									time.setText(array.getJSONObject(i).getString("clinic_date")+
											//array.getJSONObject(i).getString("dayofweek")+
											array.getJSONObject(i).getString("clinic_time"));
									money.setText(array.getJSONObject(i).getString("register_fee")+"元");
									HashMap<String, Object> has = new HashMap<String, Object>();
									has.put("lng", array.getJSONObject(i).getString("lng"));
									has.put("lat", array.getJSONObject(i).getString("lat"));
									has.put("adress", array.getJSONObject(i).getString("address"));
									has.put("hospital_name", array.getJSONObject(i).getString("hospital_name"));
									has.put("phone", array.getJSONObject(i).getString("telphone"));
									setMap(has, "location_hospital");
								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						showToast("数据异常");
					}
					super.onSuccess(statusCode, headers, responseBody);
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					ToastManager.getInstance(mContext).showToast("当前无网络连接");
				}
			});
		}else{
			ToastManager.getInstance(mContext).showToast("当前无网络连接");
		}
	}
	private void initView() {
		hospital_adr=(TextView) findViewById(R.id.hospital_adr);
		hospital_name=(TextView) findViewById(R.id.hospital_name);
		keshi_name=(TextView) findViewById(R.id.keshi_name);
		doctor_name=(TextView) findViewById(R.id.doctor_name1);
		time=(TextView) findViewById(R.id.time);
		money=(TextView) findViewById(R.id.money);
		jiuzhen_person=(ListView) findViewById(R.id.jiuzhen_person);
		jiuzhen_person.setSelector(R.drawable.cannel_liseviewback);//去除Listview點擊背景
		hospital_adrmessage=(RelativeLayout) findViewById(R.id.hospital_adrmessage);
		boolcard=new IsIdCard();
		

		
		
	}
	class foce implements OnFocusChangeListener{

		@Override
		public void onFocusChange(View arg0, boolean arg1) {
			if(arg0.getId()==R.id.et_add_dialog_phone){
				if(!arg1){
					if(!Isphone(etPhone.getText().toString())){
						
					}
					
				}
			}else if(arg0.getId()==R.id.et_add_dialog_id){
				if(!arg1){
					if(!boolcard.validateCard(etID.getText().toString())){
						
					}
					
					
				}
				
			}
			
		}
		
	}
	
	
	
	private OnClickAvoidForceListener listener = new OnClickAvoidForceListener() {
		@Override
		public void onClickAvoidForce(View v) {
			Intent intent = null;
			switch (v.getId()) {
			case R.id.btn_add_person: // 新建就诊人
				if(currentpeople<5){
					initAddPersonDialog();	
				}else{
					showToast("您已添加了5位就诊人");
				}
				
				break;
			case R.id.ll_selectsex:
				choseSexDialog();
				break;

			case R.id.tv_wem:
				gender="0";
				sex.setText("女");
				dialog2.dismiss();
				break;
			case R.id.tv_mem:
				gender="1";
				sex.setText("男");
				dialog2.dismiss();
				break;
				
				
			case R.id.btn_add_dialog_cancel: // 取消
				if (dialog != null) {
					dialog.dismiss();
				}
				break;
			case R.id.btn_add_dialog_confirm:
				if(!NUlledit()){
					createPatient();
					dialog.dismiss();
			}else{
				if(relate.equals("4")){
					showToast("请选择关系");
				}
				if(!Isphone(etPhone.getText().toString())){
					showToast("手机输入有误");
				
				}if(!boolcard.validateCard(etID.getText().toString())){
					showToast("身份证号码有误");
				}if(etName.getText().toString().equals("")){
					showToast("姓名不能为空");
				}
			
			}
				//showList();
				break;
			case R.id.hospital_adrmessage: // 定位
                 Intent intent1=new Intent(mContext,Location_hospital.class);
                 startActivity(intent1);
				break;
			case R.id.btn_submit:
				
				if(currentpeople>0&&!SharedPreferencesConfig.getStringConfig(mContext, "member_id").equals("")){
					
					 if (isjiukao) {
						 intent = new Intent(mContext, ConfirmOrderActivity.class);
							intent.putExtra("edjkao", edJkao.getText().toString()+"");//就诊卡号
							SharedPreferencesConfig.saveStringConfig(mContext, "patient_id",mList.get(flag).get("patient_id")+"");
							if(intent!=null){
								intent.putExtra("patient_id", SharedPreferencesConfig.getStringConfig(mContext, "patient_id"));
								startActivity(intent);
							}
					}
					 else {
							if (edJkao.getText().toString().equals("")) {
								showToast("就诊卡号不能为空");
							}
							else {
								intent = new Intent(mContext, ConfirmOrderActivity.class);
								intent.putExtra("edjkao", edJkao.getText().toString());//就诊卡号
								SharedPreferencesConfig.saveStringConfig(mContext, "patient_id",mList.get(flag).get("patient_id")+"");
								if(intent!=null){
									intent.putExtra("patient_id", SharedPreferencesConfig.getStringConfig(mContext, "patient_id"));
									startActivity(intent);
								}
							}
					 }
					
				}else{
					if(SharedPreferencesConfig.getStringConfig(mContext, "member_id").equals("")){
						ToastManager.getInstance(mContext).showToast("您尚未登陆，请先登陆");
						 intent=new Intent(RegistrationMessageActivity.this,LoginActivity.class);
						 intent.putExtra("restologin", "login");
						startActivity(intent);
						finish();	
					}
					if(currentpeople==0){
						showToast("您还没有就诊人请添加");
						initAddPersonDialog();	


					}
					if(SharedPreferencesConfig.getStringConfig(mContext, "member_id").equals("")){
						
						
						showToast("您还没登录，请登录");
					}

				}
				break;
			}
		}
	};
	/**
	 * 性别选择框
	 */
	private void choseSexDialog()
	{
		View view =LayoutInflater.from(this).inflate(R.layout.chose_sex_diaglog, null);
		TextView tvMem=(TextView)view.findViewById(R.id.tv_mem);
		TextView tvWem=(TextView)view.findViewById(R.id.tv_wem);
		tvMem.setOnClickListener(listener);
		tvWem.setOnClickListener(listener);
		dialog2 = new Dialog(mContext);
		dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog2.setContentView(view);
		dialog2.show();
		
	}
	
	private void showList() throws SocketTimeoutException{
		//mList = new ArrayList<Map<String, Object>>();
		if(NetworkUtil.isOnline(mContext)){
			AsyncHttpClient client=new AsyncHttpClient();
			String url=Constant.LISTJIUZHENREN;
			client.setTimeout(5000);
			RequestParams params=new RequestParams();
			params.put("member_id", SharedPreferencesConfig.getStringConfig(mContext, "member_id"));
			client.post(url, params, new AsyncHttpResponseHandler(){
				@Override
				public void onStart() {
					Showloading();
					super.onStart();
				}
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					Dissloading();
					if(!CheckJson(responseBody).equals("")){
						try {
							mList.clear();
							Map<String, Object> map = null;
							JSONObject object=new JSONObject(new String(responseBody));
							if(object.getInt("err")==0){
								JSONArray array=object.getJSONArray("patient");
								currentpeople=array.length();
								for(int i=array.length()-1;i>=0;i--){
									map=new HashMap<String, Object>();
									map.put("name", array.getJSONObject(i).getString("name"));
									map.put("phone", array.getJSONObject(i).getString("phone"));
									map.put("idnumber", array.getJSONObject(i).getString("idnumber"));
									map.put("cardno", array.getJSONObject(i).getString("cardno"));
									map.put("patient_id", array.getJSONObject(i).getString("id"));
									map.put("status", array.getJSONObject(i).getString("status"));
									mList.add(map);																				
								}
								
							}
							adapter = new Patient_Adape(mContext,RegistrationMessageActivity.this);
							adapter.setList(mList);
							jiuzhen_person.setAdapter(adapter);
							adapter.selet(0);
							flag=0;
							jiuzhen_person.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
							adapter.notifyDataSetChanged();
							//jiuzhen_person.setSelection(jiuzhen_person.getCount()-1);//从底部开始显示

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						showToast("数据异常");
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
			showToast("无网络连接");
		}
		
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	private void createPatient(){
		if(NetworkUtil.isOnline(mContext)){
			AsyncHttpClient client=new AsyncHttpClient();
			String url=Constant.ADDJIUZHENREN;
			client.setTimeout(5000);
			RequestParams params=new RequestParams();
			params.put("name",etName.getText().toString().trim());
			params.put("phone",etPhone.getText().toString().trim());
			params.put("member_id",SharedPreferencesConfig.getStringConfig(mContext, "member_id"));
			params.put("idnumber",etID.getText().toString().trim());
			//params.put("cardno",etCard.getText().toString().trim());
			params.put("relate", relate);
			params.put("gender", gender);
			client.post(url, params, new AsyncHttpResponseHandler(){
				@Override
				public void onStart() {
					Showloading();
					super.onStart();
				}
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					System.out.println("新建------------------"+new String(responseBody));
					if(!CheckJson(responseBody).equals("")){
						try {
							Dissloading();
							JSONObject object=new JSONObject(new String(responseBody));
							if(object.getInt("err")==0){
//								ToastManager.getInstance(mContext).showToast(object.getString("msg"));
//								//成功添加
//								ToastManager.getInstance(mContext).showToast("添加成功！");
//									HashMap<String, Object>	map2 = new HashMap<String, Object>();
//									map2.put("name", etName.getText().toString().trim());
//									map2.put("phone", etPhone.getText().toString().trim());
//									map2.put("idnumber", etID.getText().toString().trim());
//									map2.put("patient_id", object.getString("people_id"));
//									map2.put("cardno", etCard.getText().toString());
//									map2.put("relate", relate);
//								//	map2.put("id", id);
//								
//									mList.add(map2);
//									adapter = new Patient_Adape(mContext);
//									adapter.setList(mList);
//									jiuzhen_person.setAdapter(adapter);
//									jiuzhen_person.setItemsCanFocus(false);
								
								try {
									showList();
								} catch (SocketTimeoutException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									showToastNetTime();
								}
								
							}else{
								ToastManager.getInstance(mContext).showToast(object.getString("msg"));
							}
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						showToast("数据异常");
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
	private boolean NUlledit(){
		if(CheckEditViewNull(etName)||CheckEditViewNull(etPhone)||CheckEditViewNull(etID)||
					!Isphone(etPhone.getText().toString())||
				!boolcard.validateCard(etID.getText().toString())
				||relate.equals("4")){
			return true;

		}
		return false;
	}

	private void initAddPersonDialog() {
		
		if(SharedPreferencesConfig.getStringConfig(mContext, "member_id").equals("")){
			ToastManager.getInstance(mContext).showToast("您尚未登陆，请先登陆");
			Intent intent=new Intent(RegistrationMessageActivity.this,LoginActivity.class);
			startActivity(intent);
			finish();
		}else{
			View view = LayoutInflater.from(mContext).inflate(
					R.layout.add_person_alertdialog, null);
			dialog.setDialogView(view);
			dialog.setDialogPostion(Gravity.CENTER, 0, 0,
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			dialog.setDialgCancelOutSide(true);
			dialog.show();
			etName = (EditText) view.findViewById(R.id.et_add_dialog_name);
			etID = (EditText) view.findViewById(R.id.et_add_dialog_id);
			//etCard = (EditText) view.findViewById(R.id.et_add_dialog_card);
			etPhone = (EditText) view.findViewById(R.id.et_add_dialog_phone);
			btnCancel = (Button) view.findViewById(R.id.btn_add_dialog_cancel);
			btnConfirm = (Button) view.findViewById(R.id.btn_add_dialog_confirm);
			sex=(TextView) view.findViewById(R.id.sex);
			ll_selectsex=(LinearLayout) view.findViewById(R.id.ll_selectsex);
			group=(RadioGroup) view.findViewById(R.id.radiogroup);
			radioButton=(RadioButton) group.findViewById(group.getCheckedRadioButtonId());
			group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(RadioGroup arg0, int arg1) {
					radioButton=(RadioButton)group.findViewById(arg1);
					if(radioButton.getText().toString().equals("本人")){
						relate=0+"";
					}
					if(radioButton.getText().toString().equals("家人")){
						relate=1+"";
					}
					if(radioButton.getText().toString().equals("朋友")){
						relate=2+"";
					}
				}
				
			});
			btnCancel.setOnClickListener(listener);
			btnConfirm.setOnClickListener(listener);
			ll_selectsex.setOnClickListener(listener);
			etPhone.setOnFocusChangeListener(new foce() );
			etID.setOnFocusChangeListener(new foce());

		}
	}
	
}
			
		
		
		

