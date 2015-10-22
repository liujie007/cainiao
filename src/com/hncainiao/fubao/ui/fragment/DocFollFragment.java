package com.hncainiao.fubao.ui.fragment;

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
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.doctor.DoctorDetailActivity;
import com.hncainiao.fubao.ui.adapter.DoctorAdapter;
import com.hncainiao.fubao.ui.views.NetLoadDialog;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.hncainiao.fubao.utils.ToastManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
 
/**
 * @author zhaojing
 * @version 2015年4月18日 上午10:13:57
 * 
 */
public class DocFollFragment extends Fragment {

	private View view;
	NetLoadDialog hDialog;


	private Context mContext;

	private ListView listView;

	private DoctorAdapter adapter;
	
	List<Map<String, Object>>mList=null;
	ImageView emptyView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_follow_doctor, null);
		mContext = getActivity();
	

		listView = (ListView) view.findViewById(R.id.lv_follow_doctors);
		 emptyView=(ImageView)view.findViewById(R.id.imageview);
	
		
			
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, DoctorDetailActivity.class);
				intent.putExtra("Doctor_id", mList.get(position).get("_id")+"");
				if(intent!=null&&!mList.get(position).get("_id").equals("")){
					SharedPreferencesConfig.saveStringConfig(mContext, "Doctor_id",mList.get(position).get("_id")+"");
					startActivity(intent);	
					getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				}
				
			}
		});
		
		
		return view;
	}
	public void Showloading()
	{
		hDialog =new NetLoadDialog(mContext);
		hDialog.SetMessage("操作中...");
		hDialog.showDialog();
		
	}
	/**
	 * 取消动画
	 */
	public void Dissloading()
	{
		hDialog .dismissDialog();
	}
	

	private void setData() throws SocketTimeoutException {
		 mList = new ArrayList<Map<String, Object>>();
		 if(NetworkUtil.isOnline(mContext)){
			 try {
					AsyncHttpClient client = new AsyncHttpClient();
					RequestParams params = new RequestParams();
					String url = Constant.CONNER_DOCTOR;
					client.setTimeout(5000);
					params.put("member_id", SharedPreferencesConfig.getStringConfig(mContext, "member_id"));
					params.put("type", "2");
					client.post(url, params, new AsyncHttpResponseHandler() {
						@Override
						public void onStart() {
							/**
							 * 开始
							 * 
							 * */
							
							Showloading();
							super.onStart();
						}

						@Override
						public void onSuccess(int statusCode, Header[] headers,
								byte[] responseBody) {
							Dissloading();
							mList.clear();

							System.out.println("医生关注列表" + new String(responseBody));
							if(!BaseActivity.CheckJson(responseBody).equals("")){
								try {
									JSONObject object = new JSONObject(new String(
											responseBody));
									Map<String, Object> map = null;
									if (object.getInt("err") == 0) {
										JSONArray array = object.getJSONArray("subscribe");
										for (int i = 0; i < array.length(); i++) {
											String name = array.getJSONObject(i).getString("doctor_name");
										   String hospital_name = array.getJSONObject(	i).getString("hospital_name");
										//	String department_name = array.getJSONObject(i).getString("department_name");
											String title = array.getJSONObject(i).getString("doctor_title");
											//是否有号
											String status=array.getJSONObject(i).getString("avaiable");
									// SharedPreferencesConfig.saveStringConfig(mContext, "doctor_status", status);
											map = new HashMap<String, Object>();
											map.put("name", name);
											map.put("level", title);
											map.put("locate", hospital_name);
											map.put("img", array.getJSONObject(i).getString("doctor_avatar"));
											map.put("_id", array.getJSONObject(i).getString("doctor_id"));
											map.put("doctor_status", status);
											mList.add(map);
											
										}
									}
									adapter = new DoctorAdapter(mContext);
									adapter.setList(paixu(mList));
									listView.setAdapter(adapter);
								} catch (JSONException e) {

								}catch (Exception e) {
									// TODO: handle exception
									
								}
								
							}
							
						}

						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] responseBody, Throwable error) {
						     Dissloading();
						 
           
							ToastManager.getInstance(mContext).showToast("获取医生失败");
						}
					});

				} catch (Exception e) {

				}

			} else  {
				ToastManager.getInstance(mContext).showToast("当前无网络连接");
			}

	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		try {
			setData();
		} catch (SocketTimeoutException e) {
			// TODO Auto-generated catch block
			ToastManager.getInstance(mContext).showToast("数据获取超时，请重试");
			e.printStackTrace();
		}

		super.onResume();
	}
	//Listview排序
	public List<Map<String, Object>> paixu(List<Map<String, Object>> mList){
		
		if(!mList.isEmpty()){
			  Collections.sort(mList, new Comparator<Map<String, Object>>() {
			      @Override
			      public int compare(Map<String, Object> object2,

			      Map<String, Object> object1) {
			  //根据文本排序
			    return ((String) object2.get("doctor_status")).
			    		compareTo((String) object1.get("doctor_status"));
			      }    
			     });    
			}
			return mList;
		}
	

}
