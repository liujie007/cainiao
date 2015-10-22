package com.hncainiao.fubao.ui.activity.phyexam;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.application.FuBaoApplication;
import com.hncainiao.fubao.application.IsLogin;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.login.LoginActivity;
import com.hncainiao.fubao.ui.activity.map.GoinfoActivity;
import com.hncainiao.fubao.ui.adapter.Patient_Adape;
import com.hncainiao.fubao.ui.adapter.Patient_Adape.ViewHolder;
import com.hncainiao.fubao.ui.listener.OnClickAvoidForceListener;
import com.hncainiao.fubao.ui.views.CustomerDialog;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.hncainiao.fubao.utils.ToastManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author liujie
 * @version 2015年4月14日 下午1:19:51
 * 
 *           体检信息
 */
public class PhyExamMessageActivity extends BaseActivity {
	private Context mContext;
	/**
	 * 提交
	 */
	private Button btnSubmit, btnCancel, btnConfirm;

	ImageButton addperson;// 新建就诊人

	private CustomerDialog dialog;// 新建弹窗

	List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
	EditText name, Idcard, jiuzhen_card, phone;
	ListView tijian_List;
	Patient_Adape adapter;// 体检人列表适配器
	private ImageView imLocation;
	private int flag=-1;

	private RelativeLayout relaChossTime;
	private TextView tvTanCan,tvHostName,tvPrice,tvAddress,tvTime;
	final Calendar c = Calendar.getInstance();
	
	String jiage,taocan,hostey,id;
	/**
	 * 初始化控件
	 */
	private void setupView() {
		setTitle("体检信息");
		ScrollView scrollView =(ScrollView) findViewById(R.id.sc);
		scrollView.smoothScrollTo(0, 0);
		imLocation=(ImageView)findViewById(R.id.iv_location);
		btnSubmit = (Button) findViewById(R.id.btn_phyexam_submit);
		tvTanCan=(TextView)findViewById(R.id.tv_taocan);
		tvHostName=(TextView)findViewById(R.id.tv_hostname);
		tvPrice=(TextView)findViewById(R.id.tv_price);
		tvAddress=(TextView)findViewById(R.id.tv_address);
		tvTime=(TextView)findViewById(R.id.tv_phyexam_time);
		relaChossTime=(RelativeLayout)findViewById(R.id.rela_choss_time);
	}

	/**
	 * 添加事件监听
	 */
	private void addListener() {
		btnSubmit.setOnClickListener(l);
		addperson.setOnClickListener(l);
		imLocation.setOnClickListener(l);
		relaChossTime.setOnClickListener(l);
		tijian_List.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0,View arg1, int arg2,long arg3) {

				final ViewHolder holder = (ViewHolder) arg1.getTag();
				holder.choose.toggle();
				flag=arg2;
				adapter.notifyDataSetChanged();
			}

		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.activity_phyexam_message);
		Intent intent =getIntent();
		mContext = PhyExamMessageActivity.this;	
		setupView();
		initView();
		addListener();
		if (!SharedPreferencesConfig.getStringConfig(mContext, "phy_hospital_address").equals("")) {
			tvAddress.setText(""+SharedPreferencesConfig.getStringConfig(mContext, "phy_hospital_address"));
		}
		if (intent!=null) {
			id=intent.getStringExtra("id");
			tvPrice.setText(""+intent.getStringExtra("tvPrice")+"元");
			tvTanCan.setText(""+intent.getStringExtra("taoCan"));
			tvHostName.setText(""+SharedPreferencesConfig.getStringConfig(mContext, "phy_hospital_name"));
		}
		 tvTime.setText(c.get(Calendar.YEAR)+"年"+(c.get(Calendar.MONTH)+1)+"月"+(c.get(Calendar.DAY_OF_MONTH)+1)+"日");
			
		showPersonList();
	}

	/**
	 * 展示体检联系人
	 */
	private void showPersonList() {
	
		if (NetworkUtil.isOnline(mContext)) {
			AsyncHttpClient client = new AsyncHttpClient();
			String url = Constant.Tijianperson_List;
			client.setTimeout(5000);
			RequestParams params = new RequestParams();
			params.put("member_id", SharedPreferencesConfig.getStringConfig(mContext, "member_id"));
			client.post(url, params, new AsyncHttpResponseHandler() {
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
					  if (!CheckJson(responseBody).equals("")) {
					try {
						Map<String, Object> map = null;
						JSONObject object = new JSONObject(new String(responseBody));
						if (object.getInt("err") == 0) {
							JSONArray array = object.getJSONArray("people");
							for (int i = 0; i < array.length(); i++) {
								map = new HashMap<String, Object>();
								map.put("name", array.getJSONObject(i).getString("name"));
								map.put("phone", array.getJSONObject(i).getString("phone"));
								map.put("idnumber", array.getJSONObject(i).getString("idnumber"));
								map.put("patient_id", array.getJSONObject(i).getString("id"));
							
								jiage=tvPrice.getText().toString();
								taocan=tvTanCan.getText().toString();
								hostey=tvHostName.getText().toString();
								map.put("tvPrice",tvPrice.getText().toString());
								map.put("tvTanCan", tvTanCan.getText().toString());
							    map.put("id", id);
								map.put("tvHostName", tvHostName.getText().toString());
								map.put("tvTime", tvTime.getText().toString());
							
								mList.add(map);
							}
							adapter = new Patient_Adape(mContext,PhyExamMessageActivity.this);
							adapter.setList(mList);
							tijian_List.setAdapter(adapter);
							adapter.selet(0);
							flag=0;
							tijian_List.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
							
							

						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
					  else {
						showToast("数据异常！");
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

		} else {
			showToast("无网络连接");
		}

	}
	private void initView() {
		addperson = (ImageButton) findViewById(R.id.btn_add_person);
		dialog = new CustomerDialog(mContext);
		tijian_List = (ListView) findViewById(R.id.tijianperson_Lsit);
	}

	private OnClickAvoidForceListener l = new OnClickAvoidForceListener() {

		@Override
		public void onClickAvoidForce(View v) {
			// TODO Auto-generated method stub
			Intent intent = null;
			switch (v.getId()) {
			case R.id.btn_phyexam_submit: // 提交体检信息
				if (flag>=0) {
				    FuBaoApplication.getInstance().set("tj", mList.get(flag));
					intent = new Intent(mContext, PhyExamOrderActivity.class);
					startActivity(intent);
				}
				else
				{
					showToast("请选择联系人");
				}
			
				break;
			case R.id.btn_add_person:
				initAddPersonDialog();
				break;
			case R.id.btn_add_dialog_cancel:
				if (dialog != null) {
					dialog.dismiss();
				}
				break;

			case R.id.btn_add_dialog_confirm:
				if (checkEd()) {
					if (name.getText().toString().length()>=2&&name.getText().toString().length()<=8) {
						if (Idcard.getText().toString().length()==18) {
							CreatePerson();
							dialog.dismiss();
						}
						else
						{
							showToast("身份证必须是18位！");
						}
						
					}
					else {
						showToast("姓名必须为2-8个字符，请从新填写！");
					}
				
				}
				else {
					showToast("手机号码填写有误，请从新填写！");
				}
				
				
				break;
			case R.id.iv_location:
				//---
				Intent intent2 = new Intent(mContext,GoinfoActivity.class);
				intent2.putExtra("hostname",tvHostName.getText().toString());
				intent2.putExtra("lng",0);
				intent2.putExtra("lat",0);
				startActivity(intent2);
				break;
			case R.id.rela_choss_time:
				//时间选择
				chossTime();
				break;
			}
		}
		
	
	
		/**
		 * 选择时间
		 */
		private void  chossTime()
		{
			new DatePickerDialog(PhyExamMessageActivity.this,
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

		/**
		 * 创建体检人
		 */
		private void CreatePerson() {
			if (NetworkUtil.isOnline(mContext)) {
				AsyncHttpClient client = new AsyncHttpClient();
				String url = Constant.NEWtijian_person;
				client.setTimeout(5000);
				RequestParams params = new RequestParams();
				params.put("name", name.getText().toString().trim());
				params.put("phone", phone.getText().toString().trim());
				params.put("member_id", SharedPreferencesConfig.getStringConfig(mContext, "member_id"));
				params.put("idnumber", Idcard.getText().toString().trim());
				client.post(url, params, new AsyncHttpResponseHandler() {
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
						Showloading();
					}

					@SuppressWarnings({ "unchecked", "rawtypes" })
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						 Dissloading();
						 if (!CheckJson(responseBody).equals("")) {
						try {
							JSONObject object = new JSONObject(new String(responseBody));
							if (object.getInt("err") == 0) {
								//成功添加
								ToastManager.getInstance(mContext).showToast("添加成功！");
									HashMap	map2 = new HashMap<String, Object>();
									map2.put("name", name.getText().toString().trim());
									map2.put("phone", phone.getText().toString().trim());
									map2.put("idnumber", Idcard.getText().toString().trim());
									map2.put("patient_id", object.getString("people_id"));
									map2.put("id", id);
									map2.put("tvPrice",jiage);
									map2.put("tvTanCan", taocan);
								
									map2.put("tvHostName", hostey);
									map2.put("tvTime", tvTime.getText().toString());
							
									mList.add(map2);
									adapter = new Patient_Adape(mContext);
									adapter.setList(mList);
									tijian_List.setAdapter(adapter);
									tijian_List.setItemsCanFocus(false);
								
							} else {
								ToastManager.getInstance(mContext).showToast(object.getString("msg"));
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 }
						 else {
							showToast("数据异常！");
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
			} else {
				ToastManager.getInstance(mContext).showToast("无网络连接");
			}

		}

		/**
		 * 添加对话框
		 */
		private void initAddPersonDialog() {
			if (!IsLogin.isLogin()) {
				
				ToastManager.getInstance(mContext).showToast("您尚未登陆，请先登陆!");
				Intent intent = new Intent(mContext, LoginActivity.class);
				startActivity(intent);
			} else {
				View view = LayoutInflater.from(mContext).inflate(R.layout.add_tijian_person, null);
				dialog.setDialogView(view);
				dialog.setDialogPostion(Gravity.CENTER, 0, 0,LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				dialog.setDialgCancelOutSide(true);
				dialog.show();
				name = (EditText) view.findViewById(R.id.et_add_dialog_name);
				Idcard = (EditText) view.findViewById(R.id.et_add_dialog_id);
				jiuzhen_card = (EditText) view.findViewById(R.id.et_add_dialog_card);
				phone = (EditText) view.findViewById(R.id.et_add_dialog_phone);
				btnCancel = (Button) view.findViewById(R.id.btn_add_dialog_cancel);
				btnConfirm = (Button) view.findViewById(R.id.btn_add_dialog_confirm);
				btnCancel.setOnClickListener(l);
				btnConfirm.setOnClickListener(l);

			}
		}
		/**
		 * @return
		 * 判断内容不能为空
		 */
	
		private boolean checkEd()
		{
			
			if (!CheckPhone(phone)) {
				return false;
			}
			return true;
		}
	};

}
