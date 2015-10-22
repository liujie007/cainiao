package com.hncainiao.fubao.ui.fragment;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.hospital.HospitalIndexActivity;
import com.hncainiao.fubao.ui.adapter.FollowHospitalAdapter;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.hncainiao.fubao.utils.ToastManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
 
/**
 * @author zhaojing
 * @version 2015年4月18日 上午10:14:18
 * 
 */
public class HosFollFragment extends Fragment {

	private View view;

	private Context mContext;

	private ListView listView;

	private FollowHospitalAdapter adapter;
	List<Map<String, Object>> mList=null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_follow_hospital, null);
		mContext = getActivity();
		listView = (ListView) view.findViewById(R.id.lv_follow_hospital);
		//setData();
		
	   listView.setEmptyView(view.findViewById(R.id.imageview));

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext,HospitalIndexActivity.class);
				if(intent!=null){
					intent.putExtra("hospital_id", mList.get(position).get("_id")+"");
				}
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			}
		});
		return view;
	}

	private  void setData()throws SocketTimeoutException {
	     mList = new ArrayList<Map<String, Object>>();
		if(NetworkUtil.isOnline(mContext)){
			AsyncHttpClient client=new AsyncHttpClient();
			String url=Constant.CONNER_DOCTOR;

			client.setTimeout(5000);
			RequestParams params=new RequestParams();
			params.put("member_id",SharedPreferencesConfig.getStringConfig(mContext, "member_id"));
			params.put("type", "1");
			client.post(url, params, new AsyncHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					System.out.println("关注医院列表22222222222"+new String(responseBody));
					if(!BaseActivity.CheckJson(responseBody).equals("")){
						try{
							mList.clear();

							JSONObject object=new JSONObject(new String(responseBody));
							
							HashMap<String, Object> map=null;
							if(object.getInt("err")==0){
								JSONArray array=object.getJSONArray("subscribe");
								for(int i=0;i<array.length();i++){
									map=new HashMap<String, Object>();
									map.put("name", array.getJSONObject(i).getString("hospital_name"));
									map.put("level", array.getJSONObject(i).getString("hospital_type"));
									map.put("img", array.getJSONObject(i).getString("hospital_img"));
									map.put("_id", array.getJSONObject(i).getString("hospital_id"));
									mList.add(map);
								}
							}
							
						}catch(Exception e){		

						}
						adapter = new FollowHospitalAdapter(mContext);
						adapter.setList(mList);
						listView.setAdapter(adapter);
				
			}else{
					ToastManager.getInstance(mContext).showToast("数据异常");
					
			}
					
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, responseBody);
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					ToastManager.getInstance(mContext).showToast("获取医院失败");
					mList.clear();
					adapter = new FollowHospitalAdapter(mContext);
					adapter.setList(mList);
					listView.setAdapter(adapter);
					

				}
				
				
			});
			
			
			
		}
	
		
	}
	@Override
	public void onResume() {
		try {
			setData();
		} catch (SocketTimeoutException e) {
			// TODO Auto-generated catch block
			ToastManager.getInstance(mContext).showToast("网络连接超时");
			e.printStackTrace();
		}
		
		super.onResume();
	}
	
}
