package com.hncainiao.fubao.ui.fragment;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.hncainiao.fubao.utils.ToastManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author zhaojing
 * @version 2010年8月8日 上午10:19:20
 * 
 *          医生介绍
 */
public class DoctorBriefFragment extends Fragment {
	
	private Context mContext;
	TextView textView,hospital,good,keshi;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mContext = getActivity();
		View view = inflater.inflate(R.layout.fragment_doctor_brief, null);
		textView=(TextView) view.findViewById(R.id.doctor_introduce);
		hospital=(TextView) view.findViewById(R.id.hospital);
		keshi = (TextView) view.findViewById(R.id.ke_shi);
		good=(TextView) view.findViewById(R.id.good_at);
		return view;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		getDoctorinfo();
		super.onResume();
	}
	
	
	
	private void getDoctorinfo() {

		if(NetworkUtil.isOnline(mContext)){
			AsyncHttpClient client=new AsyncHttpClient();
			client.setTimeout(5000);
			String url=Constant.DOCTORINFO;
			RequestParams params=new RequestParams();
			params.put("doctor_id", SharedPreferencesConfig.getStringConfig(mContext, "Doctor_id"));
			client.post(url, params, new AsyncHttpResponseHandler(){
				@Override
				public void onStart() {
					super.onStart();
				}
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					System.out.println("------------------------"+new String(responseBody));
					if(!BaseActivity.CheckJson(responseBody).equals("")){
						try {
							JSONObject object=new JSONObject(new String(responseBody));
							if(object.getInt("err")==0){
								JSONArray array=object.getJSONArray("doctor");
								for(int i=0;i<array.length();i++){
									textView.setText(array.getJSONObject(i).getString("remark")); 
									hospital.setText(array.getJSONObject(i).getString("hospital_name"));
									keshi.setText(array.getJSONObject(i).getString("department_name"));
								   good.setText(array.getJSONObject(i).getString("be_good_at"));
		
								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else{
						ToastManager.getInstance(mContext).showToast("数据异常");
					}
					
					super.onSuccess(statusCode, headers, responseBody);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					ToastManager.getInstance(mContext).showToast("获取数据失败");
				}
			});
		}else{
			ToastManager.getInstance(mContext).showToast("当前无网络连接");
		}
	}
}
