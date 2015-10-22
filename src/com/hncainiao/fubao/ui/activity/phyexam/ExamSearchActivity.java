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
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.adapter.PhySearchAdapte;
import com.hncainiao.fubao.ui.views.PullToRefreshView;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 	项目：FuBaoHealth
 * 		@author liujie
 *	日期：2015-5-16下午7:13:02
 *
 *	体检搜索
 */
public class ExamSearchActivity extends BaseActivity  {

	TextView tvCity;
	ListView listView;
	private PullToRefreshView pullToRefreshView;
	Context mContext;
	private int visibleLastIndex = 0;   //最后的可视项索引  
	private int visibleItemCount;       // 当前窗口可见项总数  
	private Button btenLoadMone;
	PhySearchAdapte hAdapte;
	EditText edKeyWord;
	private String [] intentdate;;
	ImageView imLog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		setContentView(R.layout.activity_exam_search);
		mContext=ExamSearchActivity.this;
		inintView();
		location();// 定位
	}
	private void inintView()
	{
		setTitle("预约体检");
		edKeyWord=(EditText)findViewById(R.id.ed_keyword);
		((RelativeLayout) findViewById(R.id.rl_select_city)).setVisibility(View.VISIBLE);
		tvCity = (TextView) findViewById(R.id.tv_city);
		((Button)findViewById(R.id.btn_search)).setOnClickListener(this);
		edKeyWord.setOnClickListener(this);
		listView=(ListView)findViewById(R.id.listview);
		imLog=(ImageView)findViewById(R.id.im_nodate);
	/*	btenLoadMone=(Button)listsetFoot(listView).findViewById(R.id.textView1);
		btenLoadMone.setOnClickListener(this);
		
	*/	
		listView.setEmptyView(findViewById(R.id.im_nodate));
		listView.setOnScrollListener(this);     //添加滑动监听  
		hAdapte=new PhySearchAdapte(mContext);
		imLog.setVisibility(View.GONE);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext,PhyMenuDetailActivity.class);
				intent.putExtra("intendate", intentdate[position]);
			    intent.putExtra("flag", 2);
				startActivity(intent);
			}
		});
	/*	hAdapte.setList(setDate());
		listView.setAdapter(hAdapte);*/
	
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	
		switch (arg0.getId()) {
		case R.id.btn_search:
			//搜索
			listView.setBackgroundDrawable(null);
		    edKeyWord.clearFocus();
			getDate();
			break;
		case R.id.textView1:
			//加载更多
		   /*     btenLoadMone.setText("加载中...");   //设置按钮文字loading  
		        hAdapte.setList(setDate());
	            listView.setAdapter(hAdapte); 
	            listView.setSelection(visibleLastIndex - visibleItemCount + 1); //设置选中项  
		  */        
			break;
			case R.id.ed_keyword:
				edKeyWord.setFocusable(true);
				edKeyWord.setFocusableInTouchMode(true);
				 edKeyWord.requestFocus();
				break;
		}
	}
	List<Map<String , Object>> list =new ArrayList<Map<String,Object>>();
	private List<Map<String, Object>> setDate()
	{
		
		for (int i = 0; i < 2; i++) {
			Map< String, Object> map =new HashMap<String, Object>();
			 map.put("name", "老年人套餐");
			 map.put("hostey", "长沙市第一医院");
			 map.put("price", "100");
			 map.put("detail", "肝病刚i把那个 订单");
			 list.add(map);
		}
		return list;
	}
	
	 /** 
     * 滑动时被调用 
     */  
	@Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {  
        this.visibleItemCount = visibleItemCount;  
        visibleLastIndex = firstVisibleItem + visibleItemCount - 1;  
    }  
	  /** 
     * 滑动状态改变时被调用 
     */  
	@Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {  
        int itemsLastIndex = hAdapte.getCount() - 1;    //数据集最后一项的索引  
        int lastIndex = itemsLastIndex + 1;             //加上底部的loadMoreView项  
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && visibleLastIndex == lastIndex) {  
            //如果是自动加载,可以在这里放置异步加载数据的代码
       /* 	    btenLoadMone.setText("加载中...");   //设置按钮文字loading  
		        hAdapte.setList(setDate());
	            listView.setAdapter(hAdapte); 
	            hAdapte.notifyDataSetChanged();
	            listView.setSelection(visibleLastIndex - visibleItemCount + 1); //设置选中项  
     */   }  
    }  
    
	/**
	 * 得到数据
	 */
	public void getDate()
	{
		if (NetworkUtil.isOnline(this)) {
			 AsyncHttpClient httpClient =new AsyncHttpClient();
			 RequestParams params =new RequestParams();
			 params.put("keyword", edKeyWord.getText().toString().trim()+"");
			 httpClient.setTimeout(5000);
		     httpClient.post(Constant.URL_PHYSEARCH_STRING, params, new AsyncHttpResponseHandler()
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
		    		imLog.setVisibility(View.VISIBLE);
		    		Dissloading();
		    		if (!CheckJson(responseBody).equals("")) {
						
		    			//开始解析数据
		    			/*
		    			 * {"package":[{"id":"3","hospital_id":"6",
		    			 * "cid":"3","name":"十一女性体检","remark":"十一女性体检",
		    			 * "info":"十一女性体检十一女性体检十一女性体检十一女性体检十一女性体检十一女性体检十一女性体检",
		    			 * "current_price":"700.10","market_price":"2800.10","star":"5","msg":"为规范诊疗行为，方便患者就诊，根据卫生部《关于在公立医院施行预约诊疗服务工作意见》和省卫生厅文件精神，我院成立预约咨询中心，现将有关预约挂号施行办法公告如下：","createtime":"1430733128","updatetime":"1431680692",
		    			 *  "status":"1","sort":"255","hospital_name":"长沙市中心医院","cname":"女性体检"}],"err":0}
		    			 */
		    			
		    			try {
							JSONObject jsonObject =new JSONObject(new String(responseBody));
							 if (jsonObject.getInt("err")==0) {
								
						
							JSONArray jArray =jsonObject.getJSONArray("package");
							 list.clear();
							 intentdate=new String[jArray.length()];
							 for (int i = 0; i < jArray.length(); i++) {
								 JSONObject jObject =jArray.getJSONObject(i);
								 intentdate[i]=jObject.toString();
								 Map< String, Object> map =new HashMap<String, Object>();
					   			 map.put("name", jObject.getString("name"));
					   			 map.put("hostey", jObject.getString("hospital_name"));
					   			 map.put("price", jObject.getString("current_price"));
					   			 map.put("detail", jObject.getString("remark"));
					   			 list.add(map);
							}
							   
						}
							 else {
								showToast("抱歉没有搜索到结果！");
							}
							    hAdapte.setList(list);
					            listView.setAdapter(hAdapte); 
							 
		    			} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    			
					}
		    		else {
						showToast("数据异常");
					}
		    	}
		    	@Override
		    	public void onFailure(int statusCode, Header[] headers,
		    			byte[] responseBody, Throwable error) {
		    		// TODO Auto-generated method stub
		    		Dissloading();
		    		showToastNetTime();
		    		imLog.setVisibility(View.VISIBLE);
		    		imLog.setImageResource(R.drawable.icon_list_nonet);
		    	}
		     });
		}
		else {
			showToastNotNet();
			imLog.setVisibility(View.VISIBLE);
			imLog.setImageResource(R.drawable.icon_list_nonet);
		}
	}
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
		}

	}
}
