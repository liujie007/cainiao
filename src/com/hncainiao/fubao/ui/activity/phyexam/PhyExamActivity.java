package com.hncainiao.fubao.ui.activity.phyexam;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.hospital.SelectHospitalActivity;
import com.hncainiao.fubao.ui.adapter.HistoryPhyAdapter;
import com.hncainiao.fubao.ui.listener.OnClickAvoidForceListener;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.hncainiao.fubao.utils.StringUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author liujie
 * @version 2015年4月13日 上午10:24:04
 * 
 *          预约体检
 */
public class PhyExamActivity extends BaseActivity {

	private static final String TAG = "PhyExamActivity";
	private Context mContext;
	private ListView listView;
	private HistoryPhyAdapter adapter;
	private RelativeLayout selectHospital;
	private TextView tvCity;
	private ImageView imNoDate;
   
	List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
	private int visibleLastIndex = 0;   //最后的可视项索引  
	private int visibleItemCount;       // 当前窗口可见项总数  
	private LocationClient locationClient;
	private void setupView() {
		setTitle("预约体检");
		selectHospital = (RelativeLayout) findViewById(R.id.rl_select_hospital);
		((RelativeLayout) findViewById(R.id.rl_select_city)).setVisibility(View.VISIBLE);
		tvCity = (TextView) findViewById(R.id.tv_city);
		imNoDate=(ImageView)findViewById(R.id.im_ondate);
		imNoDate.setVisibility(View.GONE);
	}

	private void addListener() {
		selectHospital.setOnClickListener(listener);
		((LinearLayout)findViewById(R.id.ll_search)).setOnClickListener(listener);
		((Button)findViewById(R.id.btn_start_order)).setOnClickListener(listener);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_physical_examination);
		mContext = this;
		setupView();
    	locationClient=	location();// 定位
		addListener();
		getNetDate();
		listView = (ListView) findViewById(R.id.lv_history_physical_examination);
        listView.setEmptyView(findViewById(R.id.im_ondate));
      
        imNoDate.setVisibility(View.GONE);
    
	}
	
	private OnClickAvoidForceListener listener = new OnClickAvoidForceListener() {

		@SuppressWarnings("null")
		@Override
		public void onClickAvoidForce(View v) {
			// TODO Auto-generated method stub
			 
			switch (v.getId()) {
			case R.id.rl_select_hospital: // 选择医院
				SharedPreferencesConfig.saveStringConfig(mContext, "city", tvCity.getText().toString());
				Intent intent = new Intent(mContext, SelectHospitalActivity.class);
				intent.putExtra("flag", "phyexam");
				startActivity(intent);
				break;
			case R.id.btn_start_order:
				//跳转到套餐选择
				if (!((TextView) findViewById(R.id.tv_hospital)).getText().toString().equals("请选择医院")) {
					Intent	intent2 = new Intent(mContext, PhyMenuSelectActivity.class);
					intent2.putExtra("hospital_id", SharedPreferencesConfig.getStringConfig(mContext, "hospital_id")+"");
					startActivity(intent2);
				}
				else {
					showToast("请先选择医院！");
				}
			
				break;
			case R.id.ll_search:
				//搜索
				 startActivity(new Intent(mContext,ExamSearchActivity.class));
				break;
			
		
			}
			
			
		}
	};

	/*
	 * (non-Javadoc) 定位
	 * 
	 * @see
	 * com.cainiao.fubao.ui.activity.BaseActivity#onReceiveLocation(com.baidu
	 * .location.BDLocation)
	 */
	@Override
	public void onReceiveLocation(BDLocation arg0) {
		// TODO Auto-generated method stub
		super.onReceiveLocation(arg0);
		if (arg0 != null) {
			tvCity.setText("" + arg0.getCity());
			// 等到经度和纬度
			HashMap<String, Object> has = new HashMap<String, Object>();
			has.put("lng", arg0.getLongitude());
			has.put("lat", arg0.getLatitude());
			has.put("city", arg0.getCity());
			setMap(has, "location");
			// FuBaoApplication.getInstance().set("location", has);
			locationClient.stop();
		}
		

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		if (!SharedPreferencesConfig.getStringConfig(this, "phy_hospital_name").equals("")) {
			((TextView) findViewById(R.id.tv_hospital)).setText(""+ SharedPreferencesConfig.getStringConfig(this,"phy_hospital_name"));
		} else {
			((TextView) findViewById(R.id.tv_hospital)).setText("请选择医院");
		}
		if (!SharedPreferencesConfig.getStringConfig(this, "city").equals("")) {
			 if(SharedPreferencesConfig.getStringConfig(mContext, "city").length()>4){
	        	String str= SharedPreferencesConfig.getStringConfig(mContext, "city").substring(0,4)+"...";
	        	 tvCity.setText(str);
	         }else{
	        	 tvCity.setText(SharedPreferencesConfig.getStringConfig(mContext, "city")); 
	         }
			
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		if (locationClient!=null) {
			locationClient.stop();
		}
	}
	/**
	 * 得到网络数据
	 */
	private void getNetDate() {
		if (NetworkUtil.isOnline(this)) {
			AsyncHttpClient httpClient = new AsyncHttpClient();
			RequestParams params = new RequestParams();
			//params.put("memeber_id", SharedPreferencesConfig.getStringConfig(	mContext, "member_id"));
			params.put("member_id", SharedPreferencesConfig.getStringConfig(mContext, "member_id"));
			params.put("page", "");
			showLog("历史体检数据提交；" + params.toString());
			httpClient.setTimeout(5000);
			httpClient.post(Constant.url_my_baogao, params,
					new AsyncHttpResponseHandler() {
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
							imNoDate.setVisibility(View.VISIBLE);
							Dissloading();
							if (!CheckJson(responseBody).equals("")) {
								// 进行数据解析
								/*
								 * {"patient":[{"id":"4","member_id":"5","name":"周洁"
								 * ,"phone":"15526486079","idnumber":
								 * "42021198711200513"
								 * ,"gender":"1","age":"45","cardno"
								 * :"B001","createtime"
								 * :"1430723923","status":"1"
								 * ,"remark":"","updatetime"
								 * :"1430814300"},{"id":
								 * "5","member_id":"5","name"
								 * :"贾三","phone":"15526486072"
								 * ,"idnumber":"420021198711200514"
								 * ,"gender":"1",
								 * "age":"27","cardno":"A003","createtime"
								 * :"1430723941"
								 * ,"status":"1","remark":"","updatetime"
								 * :"1430814173"}],"err":0}
								 */
								 
								try {
									JSONObject	jsonObject = new JSONObject(	new String(responseBody));
									
									if (jsonObject.getInt("err") == 0) {

										JSONArray jsonArray = jsonObject.getJSONArray("report");

										Map<String, Object> map = null;
										for (int i = 0; i < jsonArray.length(); i++) {
											JSONObject jObject =jsonArray.getJSONObject(i);
											map = new HashMap<String, Object>();
								
											//map.put("id",jObject.getString("id") );
											map.put("menu", jObject.getString("package_name"));
											map.put("hospital","("+jObject.getString("hospital_name")+")");
											map.put("time", "上次体检时间:"+StringUtil.getaStrTime(jObject.getString("visittime")+""));//
											map.put("package_id",jObject.getString("package_id") );
											mList.add(map);
										}
										adapter = new HistoryPhyAdapter(mContext);
										adapter.setList(mList);
										listView.setAdapter(adapter);
									} else {
										
										imNoDate.setImageResource(R.drawable.icon_list_nodate);
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							} else {
								showToast("数据异常！");
							}
						}

						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] responseBody, Throwable error) {
							// TODO Auto-generated method stub
							Dissloading();
							showToastNetTime();
							imNoDate.setVisibility(View.VISIBLE);
							imNoDate.setImageResource(R.drawable.icon_list_nonet);
						}
					});
		} else {
			showToastNotNet();
			imNoDate.setVisibility(View.VISIBLE);
			imNoDate.setImageResource(R.drawable.icon_list_nonet);
		}
	}
}
