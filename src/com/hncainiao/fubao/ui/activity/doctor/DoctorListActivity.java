package com.hncainiao.fubao.ui.activity.doctor;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.adapter.DoctorAdapter;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.hncainiao.fubao.utils.ToastManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author zhaojing
 * @version 2010年8月7日 下午6:10:14
 * 
 *          医生列表
 */
public class DoctorListActivity extends BaseActivity {
	
	private static final String TAG = "DoctorListActivity";
	private Context mContext;
	private ListView listView;
	private DoctorAdapter adapter;
	private TextView doctor_nums;
	List<Map<String, Object>> mList ;
	Button btnMone;
	ImageView imView;
	private int docnum=0,haonum=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_doctor);
		mContext = this;
		Intent intent = getIntent();
		if (intent != null) {
			setTitle(intent.getStringExtra("offices") + "");
		}
		InitView();
		try {
			setData();
		} catch (SocketTimeoutException e) {
			// TODO Auto-generated catch block
			showToastNetTime();
			e.printStackTrace();
		}
		
	}

	private void InitView() {
		listView = (ListView) findViewById(R.id.lv_doctors);
		doctor_nums=(TextView) findViewById(R.id.doctor_nums);
		imView=(ImageView)findViewById(R.id.im_ondate);
		listView.setEmptyView(findViewById(R.id.im_ondate));
		imView.setVisibility(View.GONE);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(
					AdapterView<?> parent,
					View view, int position, long id) {
				Intent intent = new Intent(mContext,DoctorDetailActivity.class);
				// 传入医生编号，进入医生主页菜单
				intent.putExtra("Doctor_id",mList.get(position).get("_id")+"");
				SharedPreferencesConfig.saveStringConfig(mContext, "Doctor_id",mList.get(position).get("_id")+"");
			//	SharedPreferencesConfig.saveStringConfig(mContext, "Doctor_img",mList.get(position).get("img")+"");
				startActivity(intent);
			}
		});
	}

	private void setData()  throws SocketTimeoutException{
		mList = new ArrayList<Map<String, Object>>();
		if (NetworkUtil.isOnline(DoctorListActivity.this)) {
			try {
				String Offices_id="";
				AsyncHttpClient client = new AsyncHttpClient();
				RequestParams params = new RequestParams();
				String url = Constant.DOCTOR;
				client.setTimeout(1000000);
				Intent intent = getIntent();
				if(intent!=null){
					Offices_id = intent.getStringExtra("Offices_id");
				}else{
					Offices_id="394";
				}
				params.put("department_id", Offices_id.trim());
			    showLog("请求参数："+params.toString()+"url:"+url);
				client.post(url, params, new AsyncHttpResponseHandler() {
					@Override
					public void onStart() {
						/**
						 * 开始
						 * */
						Showloading();
						super.onStart();
					}
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						Dissloading();
						showLog("医生列表->"+new String(responseBody));
						if(!CheckJson(responseBody).equals("")){
							try {
								int haveyouhao = 0;
								JSONObject object = new JSONObject(new String(responseBody));
								Map<String, Object> map = null;
								if (object.getInt("err") == 0) {
								
									JSONArray array = object.getJSONArray("doctor");
									for (int i = 0; i<array.length(); i++) {
										String name = array.getJSONObject(i).getString("name");
										String hospital_name = array.getJSONObject(	i).getString("hospital_name");
										String department_name = array.getJSONObject(i).getString("department_name");
										String title = array.getJSONObject(i).getString("title");
										//是否有号
										String status=array.getJSONObject(i).getString("available");
										if(status.equals("1")){
											haveyouhao++;
										}
										//SharedPreferencesConfig.saveStringConfig(mContext, "doctor_status", status);
										map = new HashMap<String, Object>();
										map.put("name", name);
										map.put("level", title);
										map.put("locate", hospital_name);
										map.put("img", array.getJSONObject(i).getString("avatar"));
										map.put("_id", array.getJSONObject(i).getString("id"));
										map.put("doctor_status", status);
										//map.put("available", arg1)
										mList.add(map);
										docnum=array.length();
										doctor_nums.setText("共找到"+docnum+"位医生," +""+haveyouhao+"位有号");	
										
									}
									adapter = new DoctorAdapter(mContext);
									adapter.setList(paixu(mList));
									listView.setAdapter(adapter);
								}
								
							} catch (JSONException e) {

							}
							
						}else{
							showToast("没有数据");
						}
						
						
					}
					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						// TODO Auto-generated method stub
						Dissloading();
						showToast("网络不给力，请换网重试");
					}

					
				});

			} catch (Exception e) {

			}

		} else {
			ToastManager.getInstance(mContext).showToast("当前无网络连接");
		}

	}
	
	//Listview排序
	public List<Map<String, Object>> paixu(List<Map<String, Object>> mList){
		
		if(!mList.isEmpty()){
			  Collections.sort(mList, new Comparator<Map<String, Object>>() {
			      @Override
			      public int compare(Map<String, Object> object2,

			      Map<String, Object> object1) {
			  //根据文本排序
			    return ((String) object1.get("doctor_status")).
			    		compareTo((String) object2.get("doctor_status"));
			      }    
			     });    
			}
			return mList;
		}

}
