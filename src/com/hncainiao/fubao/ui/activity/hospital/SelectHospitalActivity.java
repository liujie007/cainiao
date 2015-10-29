package com.hncainiao.fubao.ui.activity.hospital;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.hncainiao.fubao.R;
import com.hncainiao.fubao.model.hospital;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.phyexam.PhyMenuSelectActivity;
import com.hncainiao.fubao.ui.activity.registration.SelectOfficesActivity;
import com.hncainiao.fubao.ui.adapter.SelectHospitalAdapte;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.hncainiao.fubao.utils.ToastManager;
import com.jmheart.view.listview.RefreshListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
/**
 * @author liujie
 * @version 2010年8月7日 下午12:11:14
 * 
 *          选择医院
 */
public class SelectHospitalActivity extends BaseActivity  {

	private static final String TAG = "SelectHospitalActivity";
	private Context mContext;
	private String flag = "";
	TextView hospital_nums,tvCity,tvHospNum,watch_more;
	private RefreshListView listView;
	List<hospital> hospitals= new ArrayList<hospital>();
	HashMap<String, Object> map;
	Button btnMone;
	SelectHospitalAdapte apr;
	ImageView imView;
	private int page=0,hostnum=0;
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_hospital);
		Intent intent = getIntent();
		if (intent != null) {
			flag = intent.getStringExtra("flag");
		}
		map=getMap(map, "location");
		mContext = SelectHospitalActivity.this;
		inintView();	
		//设置城市为长沙
		SharedPreferencesConfig.saveStringConfig(mContext, "city", "长沙市");
		getInitData(SharedPreferencesConfig.getStringConfig(mContext, "city"));
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		tvCity.setText(""+SharedPreferencesConfig.getStringConfig(mContext, "city"));
	
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		showLog("824"+resultCode);
		getInitData(SharedPreferencesConfig.getStringConfig(mContext, "city"));
		
	}
	/**
	 * 控件初始化
	 */
	private  void inintView()
	{
		setTitle("选择医院");
		page=0;
		imView=(ImageView)findViewById(R.id.im_ondate);
		((RelativeLayout) findViewById(R.id.rl_select_city)).setVisibility(View.VISIBLE);
		tvCity = (TextView) findViewById(R.id.tv_city);
		tvHospNum=(TextView) findViewById(R.id.hospital_nums);
		tvCity.setText(SharedPreferencesConfig.getStringConfig(mContext, "city"));
		listView = (RefreshListView) findViewById(R.id.lv_hospitals);
		listView.setOnRefreshListener(this);
		listView.setOnLoadListener(this);
		listView.setEmptyView(findViewById(R.id.im_ondate));
		imView.setVisibility(View.GONE);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent,
					View view, int position, long id) {
				if (position<=hospitals.size()) {
					
				
      if(!SharedPreferencesConfig.getStringConfig(mContext, "hospital_name").equals(hospitals.get(position-1).getName())){
		    	    SharedPreferencesConfig.saveStringConfig(mContext, "off_id", "");
					SharedPreferencesConfig.saveStringConfig(mContext, "off_name", "");
			}
      if(!SharedPreferencesConfig.getStringConfig(mContext, "hospital_name").
    		  equals(hospitals.get(position-1).getName())){
    	    SharedPreferencesConfig.saveStringConfig(mContext, "off_id", "");
			SharedPreferencesConfig.saveStringConfig(mContext, "off_name", "");
			}
				Intent intent = new Intent();
				if ("phyexam".equals(flag)) {
					SharedPreferencesConfig.saveStringConfig(mContext, "phy_hospital_name",hospitals.get(position-1).getName());
					SharedPreferencesConfig.saveStringConfig(mContext, "phy_hospital_id",hospitals.get(position-1).getId());
					SharedPreferencesConfig.saveStringConfig(mContext, "phy_hospital_address", hospitals.get(position-1).getAddress());
					
					intent.setClass(mContext,PhyMenuSelectActivity.class);
				} else if ("registration".equals(flag)) {
					// 把上一次浏览的 医院名 存入本地
					
					SharedPreferencesConfig.saveStringConfig(mContext, "hospital_name",hospitals.get(position-1).getName());
					SharedPreferencesConfig.saveStringConfig(mContext, "hospital_id",hospitals.get(position-1).getId());	
					SharedPreferencesConfig.saveStringConfig(mContext, "hospital_address", hospitals.get(position-1).getAddress());
					intent.setClass(mContext,SelectOfficesActivity.class);
				}
				
				/**
				 * 获取医院ID
				 * */
				intent.putExtra("hospital_id", hospitals.get(position-1).getId());
				SharedPreferencesConfig.saveStringConfig(mContext, "hospital_id", hospitals	.get(position-1).getId());
				intent.putExtra("hospital_name", hospitals.get(position-1).getName());
				startActivity(intent);
			}
		}
		});
	}
	

	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		//showToast("加载更多");
		page++;
        getData(""+SharedPreferencesConfig.getStringConfig(mContext, "city"));

	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		getInitData(SharedPreferencesConfig.getStringConfig(mContext, "city"));
	}
	@Override
	public void onReceiveLocation(BDLocation arg0) {
		// TODO Auto-generated method stub
		super.onReceiveLocation(arg0);
		if (arg0!=null) {
			tvCity.setText(""+arg0.getCity());
		}
	}
	/**
	 * 得到医院数据
	 */
	private void getData(String city) {
	
		if (NetworkUtil.isOnline(SelectHospitalActivity.this)) {
			AsyncHttpClient selectHttp = new AsyncHttpClient();
			selectHttp.setTimeout(5000);
			RequestParams params =new RequestParams();
			params.put("lng", map.get("lng")+"");
			params.put("lat",  map.get("lat")+"");
			params.put("region",  city);
		    params.put("page", page+"");
		    if ("phyexam".equals(flag)) {
		    	params.put("type", ""+1);
		    }
		    else {
		    	params.put("type", ""+2);
			}
			//FuBaoApplication.getInstance().set("location",null);//释放保存的对象
			showLog("得到医院数据提交参数："+params.toString());
			selectHttp.post(Constant.SELECTHOSPITAL,params, new AsyncHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
				       showLog(new String(responseBody));
						imView.setVisibility(View.GONE);
						if (new String(responseBody).trim().equals("null")||new String(responseBody).trim().equals(null)) {
							 showLog("zhixing");
							listView.onLoadComplete();
							listView.setResultSize(0);
							if (apr!=null) {
								apr.notifyDataSetChanged();
							}
						}else {
							
							// showToast(new String(responseBody));
						
					try {
						
						JSONObject object = new JSONObject(new String(responseBody));
						if (object.getInt("err")==0) {
						JSONArray array = object.getJSONArray("hospital");
						hostnum=hostnum+array.length();
						tvHospNum.setText("共找到"+object.getString("count")+"家医院");
						SharedPreferencesConfig.saveIntConfig(mContext, "Hos_nums", array.length());
						for (int i = 0; i < array.length(); i++) {
							String name = array.getJSONObject(i).getString("name");
							String id = array.getJSONObject(i).getString("id");
							String type = array.getJSONObject(i).getString("type");
							String img = array.getJSONObject(i).getString("img");
							int  km=array.getJSONObject(i).getInt("distance");
							hospitals.add(new hospital(id, name, img, type,locationkm("", km),array.getJSONObject(i).getString("address")));
							}
					  if (array.length()<10) {
						  listView.isEndDate();
					  }
						}
				else {
						page--;
						//无数据
						listView.isEndDate();
				    }
						
					} catch (Exception e) {
					}
					listView.onLoadComplete();
					listView.setResultSize(hospitals.size());
					apr.notifyDataSetChanged();
				}
						
				}

				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					//btnMone.setText("加载更多");
					imView.setVisibility(View.GONE);
					ToastManager.getInstance(mContext).showToast("网络连接失败");
					page--;
				}
			});
		}
		else {
			showToastNotNet();
			imView.setVisibility(View.GONE);
			/*imView.setImageResource(R.drawable.icon_list_nonet);*/
			page--;
		}
	}/**
	 * 得到医院数据
	 */
	private void getInitData(String city) {
	
		if (NetworkUtil.isOnline(SelectHospitalActivity.this)) {
			page=0;
			
			AsyncHttpClient selectHttp = new AsyncHttpClient();
			selectHttp.setTimeout(5000);
			RequestParams params =new RequestParams();
			params.put("lng", map.get("lng")+"");
			params.put("lat",  map.get("lat")+"");
			params.put("region", city);
		    params.put("page", page+"");
		    if ("phyexam".equals(flag)) {
		    
		    	params.put("type", ""+1);
				
		    }
		    else {
		    	
		    	params.put("type", ""+2);
		
			}
			//FuBaoApplication.getInstance().set("location",null);//释放保存的对象
			showLog("得到医院数据提交参数："+params.toString());
			selectHttp.post(Constant.SELECTHOSPITAL,params, new AsyncHttpResponseHandler() {
				@Override
				public void onStart() {
					// 加载信息
					super.onStart();
				    Showloading();
				}
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					    Dissloading();
					    tvHospNum.setText("共找到"+0+"家医院");
					    hospitals.clear();
						/*btnMone.setText("加载更多");
						imView.setVisibility(View.VISIBLE);*/
						if (!CheckJson(responseBody).equals("")) {
					try {
						JSONObject object = new JSONObject(new String(responseBody));
						JSONArray array = object.getJSONArray("hospital");
						tvHospNum.setText("共找到"+object.getString("count")+"家医院");
						SharedPreferencesConfig.saveIntConfig(mContext, "Hos_nums", array.length());
						for (int i = 0; i < array.length(); i++) {
							String name = array.getJSONObject(i).getString("name");
							String id = array.getJSONObject(i).getString("id");
							String type = array.getJSONObject(i).getString("type");
							String img = array.getJSONObject(i).getString("img");
							int  km=array.getJSONObject(i).getInt("distance");
							hospitals.add(new hospital(id, name, img, type,locationkm("", km),array.getJSONObject(i).getString("address")));
								
						}
					} catch (Exception e) {
					}
				}
				else {
					tvHospNum.setText("共找到"+0+"家医院");
					
					}
					    apr = new SelectHospitalAdapte(hospitals,mContext);
						listView.setAdapter(apr);
						listView.onLoadComplete();
						listView.onRefreshComplete();
						listView.setResultSize(hospitals.size());
						apr.notifyDataSetChanged();
				}

				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					Dissloading();
					tvHospNum.setText("共找到"+0+"家医院");
					imView.setVisibility(View.VISIBLE);
					imView.setImageResource(R.drawable.icon_list_nonet);
					//ToastManager.getInstance(mContext).showToast("网络连接失败");
				
				}
			});
		}
		else {
			//showToastNotNet();
			imView.setVisibility(View.VISIBLE);
			imView.setImageResource(R.drawable.icon_list_nonet);
		
		}
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);
		if (arg0.getId()==R.id.btn_mone_date) {
			page++;
			btnMone.setText("加载中...");   //设置按钮文字loading 
			getData(SharedPreferencesConfig.getStringConfig(mContext, "city"));
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
}


	

