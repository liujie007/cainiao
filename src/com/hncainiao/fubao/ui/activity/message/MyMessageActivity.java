package com.hncainiao.fubao.ui.activity.message;

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
import android.util.TimeFormatException;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.personalcenter.RegisOrderMessage;
import com.hncainiao.fubao.ui.activity.phyexam.PhyInfoActivity;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.jmheart.view.listview.RefreshListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 	项目：FuBaoHealth
 * 		@author ningshunjie
 *	日期：2015-5-23上午11:12:47
 *
 * 我的消息
 */
public class MyMessageActivity extends BaseActivity {

	RefreshListView listMessage;
	MyMessageAdapter adapter;
	Context mContex;
	LinearLayout layout;
	private int visibleLastIndex = 0;   //最后的可视项索引  
	@SuppressWarnings("unused")
	private int visibleItemCount;       // 当前窗口可见项总数  
	private int page=0;

	boolean IsreadMessage=false;
	List<Map<String, Object>> mList=new ArrayList<Map<String,Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_message_activity);
		mContex=this;
		initView();
		getNetDate();
	}
	
	/**
	 * 控件初始化
	 */
	private void initView()
	{
		setTitle("我的消息");
		page=0;
		listMessage=(RefreshListView)findViewById(R.id.list_message);
		listMessage.setEmptyView(findViewById(R.id.lea_nodate));
		listMessage.setOnRefreshListener(this);
		listMessage.setOnLoadListener(this);
		layout=(LinearLayout)findViewById(R.id.lea_nodate);
		layout.setVisibility(View.GONE);
		listMessage.setOnItemClickListener(new OnItemClickListener(){
        Intent intent=null;
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2<=mList.size()) {
				arg2=arg2-1;
		    if(mList.get(arg2).get("type").equals("预约成功")){
		    	String s=mList.get(arg2).get("remark")+"";
		    	String a[]=s.split("=");
		    	intent=new Intent(mContex,RegisOrderMessage.class);
		    	intent.putExtra("register_d",new String(a[1]));
		    	intent.putExtra("message_id", mList.get(arg2).get("_id")+"");
		    	startActivity(intent);
		   }
		    if(mList.get(arg2).get("type").equals("体检预约成功")){
		    	String s=mList.get(arg2).get("remark")+"";
		    	String a[]=s.split("=");
		    	intent=new Intent(mContex,PhyInfoActivity.class);
		    	intent.putExtra("date", new String(a[1]));
		    	intent.putExtra("message_id", mList.get(arg2).get("_id")+"");
		    	//System.out.println("体检详情ID"+new String(a[1]));
		    	startActivity(intent);
		    }
		}
			}
			
		});
		
	}
	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		page++;
		getMoreNetDate();
	
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		getNetDate();
	}
	/**
	 * 得到网络数据
	 */
	public  void getNetDate()throws TimeFormatException{
		page=0;
		if (NetworkUtil.isOnline(mContex)) {
		AsyncHttpClient httpClient =new AsyncHttpClient();
		RequestParams params =new RequestParams();
		params.put("member_id", SharedPreferencesConfig.getStringConfig(mContex, "member_id"));
		params.put("page", page+"");
		httpClient.setTimeout(5000);
		httpClient.post(Constant.url_my_message,params, new AsyncHttpResponseHandler()
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
				layout.setVisibility(View.VISIBLE);
				Dissloading();
				if (!CheckJson(responseBody).equals("")) {
					
					//开始解析数据
					try {
						JSONObject jsonObject =new JSONObject(new String(responseBody));
					
							if (jsonObject.getInt("err")==0) {
								mList.clear();
								JSONArray jArray =jsonObject.getJSONArray("message");
								for (int i = 0; i < jArray.length(); i++) {
								     JSONObject jObject =jArray.getJSONObject(i);
									 HashMap<String, Object> map =new HashMap<String, Object>();
									 map.put("tv_tile", jObject.getString("title"));
									 map.put("tv_message", "订单编号："+jObject.getString("order_sn"));
									 map.put("tv_num", "");
									 map.put("type",jObject.getString("title") );
									 map.put("_id", jObject.getString("id"));
									 map.put("remark", jObject.getString("remark"));
									 map.put("status", jObject.getString("status"));
									 map.put("time", "下单时间："+TimetoData(jObject.getString("order_time")));
									 map.put("connect", jObject.getString("content"));
									 mList.add(map);
								}
								adapter =new MyMessageAdapter(mContex);
								adapter.setList(paixu(mList));
								listMessage.setAdapter(adapter);
								listMessage.onLoadComplete();
								listMessage.onRefreshComplete();
								listMessage.setResultSize(mList.size());
								adapter.notifyDataSetChanged();
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
				layout.setVisibility(View.VISIBLE);
			}
		});
		}
		else
		{
			showToastNotNet();
			layout.setVisibility(View.VISIBLE);
		
			page--;
		}
	}
	/**
	 * 得到网络数据
	 */
	public  void getMoreNetDate()throws TimeFormatException{
		if (NetworkUtil.isOnline(mContex)) {
		AsyncHttpClient httpClient =new AsyncHttpClient();
		RequestParams params =new RequestParams();
		params.put("member_id", SharedPreferencesConfig.getStringConfig(mContex, "member_id"));
		params.put("page", page+"");
		httpClient.setTimeout(5000);
		httpClient.post(Constant.url_my_message,params, new AsyncHttpResponseHandler()
		{
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				// TODO Auto-generated method stub
				layout.setVisibility(View.VISIBLE);
			
			
				if (!CheckJson(responseBody).equals("")) {
					
					//开始解析数据
					try {
						JSONObject jsonObject =new JSONObject(new String(responseBody));
					
							if (jsonObject.getInt("err")==0) {
								JSONArray jArray =jsonObject.getJSONArray("message");
								for (int i = 0; i < jArray.length(); i++) {
								     JSONObject jObject =jArray.getJSONObject(i);
									 HashMap<String, Object> map =new HashMap<String, Object>();
									 map.put("tv_tile", jObject.getString("title"));
									 map.put("tv_message", "订单编号："+jObject.getString("order_sn"));
									 map.put("tv_num", "");
									 map.put("type",jObject.getString("title") );
									 map.put("_id", jObject.getString("id"));
									 map.put("remark", jObject.getString("remark"));
									 map.put("status", jObject.getString("status"));
									 map.put("time", "下单时间："+TimetoData(jObject.getString("order_time")));
									 map.put("connect", jObject.getString("content"));
									 mList.add(map);
								}
							
							}
							else {
							//	showToast("没有数据！");
								listMessage.isEndDate();
								page--;
							}
							listMessage.onLoadComplete();
							listMessage.setResultSize(mList.size());
							adapter.setList(paixu(mList));
							adapter.notifyDataSetChanged();
							
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
				page--;
				showToastNetTime();
				layout.setVisibility(View.VISIBLE);
			}
		});
		}
		else
		{
			showToastNotNet();
			layout.setVisibility(View.VISIBLE);
			page--;
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
				    return ((String) object2.get("status")).
				    		compareTo((String) object1.get("status"));
				      }    
				     });    
				}
				return mList;
			}
	
}
