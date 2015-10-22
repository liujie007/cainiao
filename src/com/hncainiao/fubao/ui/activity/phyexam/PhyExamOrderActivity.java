package com.hncainiao.fubao.ui.activity.phyexam;

import java.util.HashMap;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.application.FuBaoApplication;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.personalcenter.PersonMyBank;
import com.hncainiao.fubao.ui.listener.OnClickAvoidForceListener;
import com.hncainiao.fubao.ui.views.CustomAlertDialog;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author zhaojing
 * @version 2015年4月10日 下午2:35:14
 * 
 *          体检订单确认
 */
public class PhyExamOrderActivity extends BaseActivity {

	// private static final String TAG = "ConfirmOrderActivity";

	private Context mContext;

	/**
	 * 银行 医院
	 */
	private RelativeLayout rlBank, rlHospital;
	private ImageView ivBank, ivHospital;

	/**
	 * 确认订单
	 */
	private Button btnConfirm;
	/*
	 * 自定义的对话框
	 */
	private CustomAlertDialog dialog;
	int flag=1;
	private String tc_id,people_id;

	private void setupView() {
		setTitle("确认订单");
		rlBank = (RelativeLayout) findViewById(R.id.rl_select_bank);
		rlHospital = (RelativeLayout) findViewById(R.id.rl_select_hospital);
		ivBank = (ImageView) findViewById(R.id.iv_select_bank);
		ivHospital = (ImageView) findViewById(R.id.iv_select_hospital);
		btnConfirm = (Button) findViewById(R.id.btn_confirm_phyexam_order);
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map =(HashMap<String, Object>) FuBaoApplication.getInstance().get("tj");
		if (map!=null) {
			//
			((TextView)findViewById(R.id.tv_name)).setText(""+map.get("name"));
			((TextView)findViewById(R.id.tv_idka)).setText(""+map.get("idnumber"));
			((TextView)findViewById(R.id.tv_phone)).setText(""+map.get("phone"));
			((TextView)findViewById(R.id.tv_host)).setText(""+map.get("tvHostName"));
			((TextView)findViewById(R.id.tv_taocan)).setText(""+map.get("tvTanCan"));
			((TextView)findViewById(R.id.tv_time)).setText(""+map.get("tvTime"));
			((TextView)findViewById(R.id.tv_money)).setText(""+map.get("tvPrice"));
			tc_id=map.get("id")+"";
			people_id=map.get("patient_id")+"";
		}
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
		setContentView(R.layout.activity_confirm_phyexam_order);
		mContext = this;
		setupView();
		addListener();
	}

	private OnClickAvoidForceListener listener = new OnClickAvoidForceListener() {

		@Override
		public void onClickAvoidForce(View v) {
			// TODO Auto-generated method stub
			Intent intent = null;
			switch (v.getId()) {
			case R.id.rl_select_bank:
				ivBank.setImageResource(R.drawable.img_select);
				ivHospital.setImageResource(R.drawable.img_unselect);
				rlBank.setBackgroundResource(R.drawable.corners_selected_bg);
				rlHospital.setBackgroundResource(R.drawable.corners_unselected_bg);
				flag=1;
				break;
			case R.id.rl_select_hospital:
				flag=2;
				ivHospital.setImageResource(R.drawable.img_select);
				ivBank.setImageResource(R.drawable.img_unselect);
				rlHospital
						.setBackgroundResource(R.drawable.corners_selected_bg);
				rlBank.setBackgroundResource(R.drawable.corners_unselected_bg);
				break;
			case R.id.btn_confirm_phyexam_order: // 确认订单
				dialog = new CustomAlertDialog(mContext, listener);
				dialog.setTitle("提 示");
				dialog.setMessage("确认提交订单吗?");
				break;
			case R.id.btn_custom_alertdialog_confirm:
				if (dialog != null) {
					dialog.dismiss();
				}
				if (flag==2) {
					//
					pHostOrder(2);
				}
				else if (flag==1) {
					/*intent = new Intent(mContext, SelectBankActivity.class);
					startActivity(intent);*/
					pHostOrder(1);
					

				} 
				
				break;
			}
		}
	};
	/**
	 * 到院支付
	 */
	private void pHostOrder(int flage)
	{
	
		if (NetworkUtil.isOnline(this)) {
			
			AsyncHttpClient httpClient =new AsyncHttpClient();
			RequestParams params =new RequestParams();
			params.put("member_id", SharedPreferencesConfig.getStringConfig(PhyExamOrderActivity.this, "member_id"));
			params.put("people_id", people_id);
			params.put("package_id", tc_id);
			params.put("paytype", "1");
			params.put("title", "");
			httpClient.post(Constant.porder_host,params, new AsyncHttpResponseHandler()
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
					
						try {
							JSONObject jsonObject =new JSONObject(new String(responseBody));
							if (jsonObject.getInt("err")==0) {
								
								if (flag==1) {
								   Intent	intent = new Intent(mContext, PersonMyBank.class);
									intent.putExtra("flag", "体检");
									intent.putExtra("chosebank", "请选择银行卡进行支付");
									intent.putExtra("orderld", "33345678945675");
									intent.putExtra("txnAmt", "100");
									startActivity(intent);
								}
								else {
									showToast("体检预约成功！");//{"err":0,"exam_id":53}
									Intent intent =new Intent(mContext,PhyInfoActivity.class);
									intent.putExtra("date",jsonObject.getString("exam_id"));
									intent.putExtra("message_id","");
									startActivity(intent);
								}
								
							}
							else {
								showToast(jsonObject.getString("msg"));
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
					  showToastNetTime();
				}
			});
		}
		else {
			showToastNotNet();
		}
	}
}
