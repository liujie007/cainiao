package com.hncainiao.fubao.ui.activity.personalcenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.registration.AddCardActivity;
import com.hncainiao.fubao.ui.adapter.MyBankCardAdapter;
import com.hncainiao.fubao.ui.listener.OnClickAvoidForceListener;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;

/**
 * 	项目：FuBaoHealth
 * 		@author liujie
 *	日期：2015-5-23上午10:40:03
 *	 我的银联卡
 */
public class PersonMyBank extends BaseActivity {

	private String mMode = "00";
	
	private Context mContext;

	private ListView listView;

	private MyBankCardAdapter adapter;
	
	private boolean bflage=false;
	private ImageButton btnAddCard;
	String register_id , txnAmt,flag;
	List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
	private RadioButton rad_c,rad_z;
	private void setupView() {
		
		if (getIntent().getStringExtra("chosebank")!=null) {
			bflage=false;
			setTitle(""+getIntent().getStringExtra("chosebank"));
			register_id=getIntent().getStringExtra("register_id");
			txnAmt=getIntent().getStringExtra("txnAmt")	;
			flag=getIntent().getStringExtra("flag")	;
		}
		else {
			bflage=true;
			setTitle("我的银行卡");
		}
		
		rad_c=(RadioButton)findViewById(R.id.r_c);
		rad_z=(RadioButton)findViewById(R.id.r_z);
		
		btnAddCard = (ImageButton) findViewById(R.id.title_right_add_card);
		btnAddCard.setVisibility(View.VISIBLE);
	}

	/**
	 * 
	 */
	private void addListener() {
		btnAddCard.setOnClickListener(listener);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_mybank);
		mContext = this;
		
		setupView();
		addListener();
		listView = (ListView) findViewById(R.id.lv_bank_card_list);
		getNetDate();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				 
				 if (bflage) {
					 //银行详情
					 Intent intent =new Intent(PersonMyBank.this,PersonMyBankInfo.class);
					 startActivity(intent);
				}
				 else {
					 /*//进入支付
					 if (rad_c.isChecked()) {
						 mMode="00";
						 getTn("http://wx.zgcainiao.com/app/api.php?type=app&name=consume",register_id ,mList.get(position).get("bank_id")+"",""+mList.get(position).get("card_id"), txnAmt);
							
					}
					 else {*/
						 mMode="00";
						 getTn(""+Constant.url_gettn,register_id ,""+mList.get(position).get("bank_id")+"",""+mList.get(position).get("card_id"), txnAmt);
							
					/*}*/
				}
				//startActivity(new Intent(PersonMyBank.this,JARActivity.class));
				
			}
		});
	}

	/**
	 * 
	 */
	private OnClickAvoidForceListener listener = new OnClickAvoidForceListener() {

		@Override
		public void onClickAvoidForce(View v) {
			// TODO Auto-generated method stub
			Intent intent = null;
			switch (v.getId()) {
			case R.id.title_right_add_card: // 添加银行卡
				intent = new Intent(mContext, AddCardActivity.class);
				startActivityForResult(intent, 1000);
		
				break;
			}
		}
	};

	/**
	 * 得到tn
	 */
	
	private void getTn(String url,String register_id ,String bankcard_id,String accNo,String txnAmt)
	{
		if (NetworkUtil.isOnline(this)) {
			AsyncHttpClient hClient =new AsyncHttpClient();
			RequestParams params =new RequestParams();
			params.put("accNo", accNo);
			params.put("register_id", register_id);
			params.put("bankcard_id", bankcard_id);
			params.put("txnAmt", Integer.parseInt(txnAmt)*100+"");
		
			showLog("获取tn提交数据"+params.toString());
			hClient.post(url,params, new AsyncHttpResponseHandler()
			{
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					super.onStart();
					 Showloading();
				}
				@Override
				public void onSuccess(String content) {
					// TODO Auto-generated method stub
					   showLog("获取tn:"+content);
					   Dissloading();
					   // {"err":0,"tn":"201508081024225020838" "201508081740000051292"}
					  
					   		try {
								JSONObject jObject =new JSONObject(content);
								if (jObject.getInt("err")==0) {
									UPPayAssistEx.startPayByJAR(PersonMyBank.this, PayActivity.class, null, null,
										jObject.getString("tn").trim(), mMode);
							
								}
								else
								{
									showToast(""+jObject.getString("msg"));
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
				}
			});
			
		}
	}
	
	/**
	 * 得到绑定的银行卡列表
	 */
	private  void getNetDate()
	{
		if (NetworkUtil.isOnline(this)) {
			
			AsyncHttpClient httpClient =new AsyncHttpClient();
			RequestParams params =new RequestParams();
			params.put("member_id", SharedPreferencesConfig.getStringConfig(mContext, "member_id"));
			System.out.println("当前会员ID"+SharedPreferencesConfig.getStringConfig(mContext, "member_id"));
			httpClient.setTimeout(5000);
			httpClient.post(Constant.member_yhk, params,new AsyncHttpResponseHandler()
			{
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
					Dissloading();
					if (!CheckJson(responseBody).equals("")) {
						//看似解析数据
						/*
						 * {"bankcard":[{"id":"7","member_id":"5","phone":"15526486071",
						 * "bank_id":"6","idnumber":"42090198711200524","cardno":"1234 5621 3123 1212 312",
						 * "type":"1","expiretime":"0","remark":"de","createtime":"1430388512",
						 * "status":"1","bank_name":"中国银行"},{"id":"8","member_id":"5","phone":"1234567891","bank_id":"7","idnumber":"123456789123456789","cardno":"4561123445647895","type":"1","expiretime":"0","remark":"",
						 *  "createtime":"1430388888","status":"1","bank_name":"中国工商银行"}],"err":0}
						 */
						
						
						System.out.println("我的银行卡列表"+new String(responseBody));
						try {
							JSONObject jObject =new JSONObject(new String(responseBody));
							
							if (jObject.getInt("err")==0) {
								 mList.clear();
								 JSONArray jArray =jObject.getJSONArray("bankcard");
								 for (int i = 0; i < jArray.length(); i++) {
									    JSONObject jsonObject=jArray.getJSONObject(i);
									    Map<String, Object> map = new HashMap<String, Object>();
											map.put("bank_name", jsonObject.getString("bank_name"));
											if (jsonObject.getString("type").equals("1")) {
												map.put("card_type", "储蓄卡");
											}
											else {
												map.put("card_type", "信用卡");
											}
										
											map.put("img", jsonObject.getString("logo"));
											map.put("card_id", jsonObject.getString("cardno"));
											map.put("bank_id", jsonObject.getString("id"));
											mList.add(map);
										
								}
								adapter = new MyBankCardAdapter(mContext);
								adapter.setList(mList);
								listView.setAdapter(adapter);
							}
							else {
								showToast("未绑定银行卡！");
							}
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
					}
					else
					{
						showToast("未绑定银行卡！");
					}
				}
				@Override
				public void onFailure(int statusCode,
						org.apache.http.Header[] headers, byte[] responseBody,
						Throwable error) {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*************************************************
         * 步骤3：处理银联手机支付控件返回的支付结果
         ************************************************/
		if (requestCode==1000) {
			getNetDate();
		}
        if (data == null) {
            return;
        }

        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            msg = "支付成功！";
            if (flag.equals("体检")) {
            	
				startActivity(new Intent(PersonMyBank.this,PhyOrderActivity.class));
			}
            else {
            	startActivity(new Intent(PersonMyBank.this,RegisOrderActivity.class));
			}
            //跳转
        } else if (str.equalsIgnoreCase("fail")) {
            msg = "支付失败！";
            
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = "用户取消了支付";
            
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("支付结果通知");
        builder.setMessage(msg);
        builder.setInverseBackgroundForced(true);
        // builder.setCustomTitle();
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

/*	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		 if (resultCode==1) {
			 if (data!=null) {
				 Map<String, Object> map = new HashMap<String, Object>();
					map.put("bank_name", data.getStringExtra("bank_name"));
					map.put("card_type", data.getStringExtra("card_type"));
					map.put("img", "5548292bded0f.jpg");
					map.put("card_id", data.getStringExtra("card_id"));
					mList.add(map);
			}
			adapter = new BankCardAdapter(mContext,SelectBankActivity.this);
			adapter.setList(mList);
			adapter.notifyDataSetChanged();
			listView.setAdapter(adapter);
		}
	}*/
}
