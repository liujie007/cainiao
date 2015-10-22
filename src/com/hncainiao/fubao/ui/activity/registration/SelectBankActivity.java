package com.hncainiao.fubao.ui.activity.registration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.adapter.BankCardAdapter;
import com.hncainiao.fubao.ui.listener.OnClickAvoidForceListener;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author zhaojing
 * @version 2015年4月10日 下午5:22:06
 * 
 *          选择银行
 */
public class SelectBankActivity extends BaseActivity {

	// private static final String TAG = "SelectBankActivity";

	private Context mContext;

	private ListView listView;

	private BankCardAdapter adapter;

	private ImageButton btnAddCard;
	List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
	private void setupView() {
		setTitle("选择银行卡");
		btnAddCard = (ImageButton) findViewById(R.id.title_right_add_card);
		btnAddCard.setVisibility(View.INVISIBLE);
	}

	private void addListener() {
		btnAddCard.setOnClickListener(listener);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_bank);
		mContext = this;
		setupView();
		addListener();
		listView = (ListView) findViewById(R.id.lv_bank_card_list);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0,View arg1, int arg2,long arg3) {

//				final ViewHolder1 holder = (ViewHolder1) arg1.getTag();
//				holder.choose.toggle();
				
			
			}

		});
	
		
	}
	@Override
	protected void onResume() {
		getNetDate();
		super.onResume();
	}
	

	private OnClickAvoidForceListener listener = new OnClickAvoidForceListener() {

		@Override
		public void onClickAvoidForce(View v) {
			// TODO Auto-generated method stub
			Intent intent = null;
			switch (v.getId()) {
			case R.id.title_right_add_card: // 添加银行卡
				intent = new Intent(mContext, AddCardActivity.class);
				startActivity(intent);
				finish();
				break;
			}
		}
	};

	/**
	 * 得到绑定的银行卡列表
	 */
	private  void getNetDate(){
		if (NetworkUtil.isOnline(this)) {
			
			AsyncHttpClient httpClient =new AsyncHttpClient();
			RequestParams params =new RequestParams();
			params.put("member_id", "8");
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
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					// TODO Auto-generated method stubto
					Dissloading();
					if (!CheckJson(responseBody).equals("")) {
						//看似解析数据
						try {
							JSONObject jObject =new JSONObject(new String(responseBody));
							
							if (jObject.getInt("err")==0) {
								 JSONArray jArray =jObject.getJSONArray("bankcard");
								 for (int i = 0; i < jArray.length(); i++) {
									 JSONObject jsonObject=jArray.getJSONObject(i);
									 Map<String, Object> map = new HashMap<String, Object>();
											map.put("bank_name", jsonObject.getString("bank_name"));
											map.put("card_type", "银行卡");
											map.put("img", jsonObject.getString("logo"));
											map.put("card_id", jsonObject.getString("cardno"));
											mList.add(map);
								}
								adapter = new BankCardAdapter(mContext,SelectBankActivity.this);
								adapter.setList(mList);
								listView.setAdapter(adapter);
							}
							else {
								showToast("没有数据！");
							}
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
					}
					else
					{
						showToast("没有数据！");
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
