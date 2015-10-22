package com.hncainiao.fubao.ui.activity.registration;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.model.Group;
import com.hncainiao.fubao.model.Kind;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.doctor.DoctorListActivity;
import com.hncainiao.fubao.ui.activity.hospital.HospitalIndexActivity;
import com.hncainiao.fubao.ui.adapter.ExpandableListAdapter;
import com.hncainiao.fubao.ui.views.PinnedHeaderExpandableListView.OnHeaderUpdateListener;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author zhaojing
 * @version 2010年8月7日 下午2:32:44 选择科室
 */
public class SelectOfficesActivity extends BaseActivity implements
		ExpandableListView.OnChildClickListener,
		ExpandableListView.OnGroupClickListener, OnHeaderUpdateListener, OnGroupExpandListener{
	private static final String TAG = "SelectOfficesActivity";
	private Context mContext;
	ExpandableListView expandableListView;
	private ExpandableListAdapter adapter;
	ArrayList<Group> groupList2;
	private ArrayList<List<Kind>> childList2 = null;
	private String hospital_name = "";
	private String hospital_id = "";
	private String Offices_id = ""; // 科室ID传入医生列表
	private RelativeLayout rlHospitalIndex; // 医院主页
	GridView gridView;///跳转
    List<Boolean> listState=new ArrayList<Boolean>();
    List<String> listId=new ArrayList<String>();
	private void setupView() {
		rlHospitalIndex = (RelativeLayout) findViewById(R.id.rl_hospital_index);
		rlHospitalIndex.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext,
						HospitalIndexActivity.class);
				// 传入科室ID
				if(intent!=null){
					intent.putExtra("hospital_id", hospital_id);
					startActivity(intent);	
				}
				
			}
		});
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_offices);
		mContext = this;
		setupView();

		Intent intent = getIntent();
		if (intent != null) {
			hospital_name = intent.getStringExtra("hospital_name");
			// 获取从医院列表那里的医院ID
			hospital_id = intent.getStringExtra("hospital_id");
			setTitle(hospital_name);
		}
		expandableListView = (ExpandableListView) findViewById(R.id.expand_list_offices);
		try {
			setData();
		} catch (SocketTimeoutException e) {
			// TODO Auto-generated catch block
			showToastNetTime();
			e.printStackTrace();
		}
		expandableListView.setOnGroupExpandListener(this);
		//SharedPreferencesConfig.saveStringConfig(mContext, "off_id", "");
		//SharedPreferencesConfig.saveStringConfig(mContext, "off_name", "");

		System.out.println("gaile中"+SharedPreferencesConfig.getStringConfig(mContext, "off_id"));
		expandableListView.setOnGroupClickListener(new OnGroupClickListener() {
			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// TODO Auto-generated method stub
				 adapter.setSelet(groupPosition);
				 
				if (listState.get(groupPosition)) {
					return false;
				}
				else {
					//跳转
					adapter.notifyDataSetChanged();
					 Intent intent = new Intent(mContext, DoctorListActivity.class);
					 intent.putExtra("Offices_id", listId.get(groupPosition)+"");
					 SharedPreferencesConfig.saveStringConfig(mContext, "off_id", listId.get(groupPosition)+"");
					 intent.putExtra("offices",groupList2.get(groupPosition).getName()+ "");
					 SharedPreferencesConfig.saveStringConfig(mContext, "off_name", ""+groupList2.get(groupPosition).getName());
					 startActivity(intent);
				}
				 
				return true;
			}
		});
		expandableListView.setOnChildClickListener(new OnChildClickListener(){
			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1,
					int arg2, int arg3, long arg4) {
				 Intent intent = new Intent(mContext, DoctorListActivity.class);
				 intent.putExtra("Offices_id", childList2.get(arg2).get(arg3).getId()+"");
				 SharedPreferencesConfig.saveStringConfig(mContext, "off_id", childList2.get(arg2).get(arg3).getId()+"");
				 intent.putExtra("offices",childList2.get(arg2).get(arg3).getName()+ "");
				 SharedPreferencesConfig.saveStringConfig(mContext, "off_name", childList2.get(arg2).get(arg3).getName()+"");
				 startActivity(intent);
				return false;
			}
		});
		
	
	}

	@Override
	public View getPinnedHeader() {
		View headerView = (ViewGroup) getLayoutInflater().inflate(
				R.layout.group, null);
		headerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		return headerView;
	}
	
	@Override
	public void updatePinnedHeader(View headerView, int firstVisibleGroupPos) {
		// TODO Auto-generated method stub
		Group firstVisibleGroup = (Group) adapter
				.getGroup(firstVisibleGroupPos);
		TextView textView = (TextView) headerView.findViewById(R.id.group);
		textView.setText(firstVisibleGroup.getName());
	}
	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		// TODO Auto-generated method stub
		
			return false;
	}

	/**
	 * 跳转医生列表 传科室ID
	 * ***/
	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		// showToast(childList.get(groupPosition).get(childPosition).getName()+
		// "");
		Intent intent = new Intent(mContext, DoctorListActivity.class);
		// intent.putExtra("Offices_id",
		// childList.get(groupPosition).get(childPosition).getId()+"");
		// putExtra("offices",childList.get(groupPosition).get(childPosition).getName()
		// + "");
		startActivity(intent);
		return false;
	}

	private void setData() throws SocketTimeoutException{
		if (NetworkUtil.isOnline(SelectOfficesActivity.this)) {
			groupList2 = new ArrayList<Group>();
			childList2 = new ArrayList<List<Kind>>();
			AsyncHttpClient client = new AsyncHttpClient();
			String url = Constant.GUAHAO_KESHI;
			RequestParams params = new RequestParams();	// 传入医院ID
			Intent intent = getIntent();
			if(intent!=null){
				hospital_id = intent.getStringExtra("hospital_id");
			}
			System.out.println("科室选择医院ID"+hospital_id);
			params.put("hospital_id", hospital_id);
			client.post(url, params, new AsyncHttpResponseHandler() {
				@Override
				public void onStart() {
					// 加载
					Showloading();
					super.onStart();
				}
				@Override
				public void onSuccess(int statusCode, Header[] headers,byte[] responseBody) {
					Dissloading();
					System.out.println("科室列表"+new String(responseBody));
					if(!CheckJson(responseBody).equals("")){
						try {
							JSONObject object = new JSONObject(new String(responseBody));
							if (object.getInt("err") == 0) {
								JSONArray array = object.getJSONArray("department");
								for (int i = 0; i < array.length(); i++) {
									JSONObject obj = (JSONObject) array.opt(i);
									String name = obj.getString("name");
									 listId.add(obj.getString("id"));
									// 添加一级科室
									Group group = new Group(name);
									groupList2.add(group);
									JSONArray array2=obj.getJSONArray("child");
								
									 if (array2.length()>0) {
										 listState.add(true);
									 }else {
										 listState.add(false);
									}	 
										 ArrayList<Kind>childtemp=null;
			                         	   childtemp=new ArrayList<Kind>();
			                               for(int j=0;j<array2.length();j++){
			                            	   Kind kind = new Kind(array2.getJSONObject(j).getString("id"),  
			                        		   array2.getJSONObject(j).getString("name"));
			                            	   childtemp.add(kind);
			                               }
			                                childList2.add(childtemp);
									
			       						   
									
									adapter = new ExpandableListAdapter(mContext,groupList2, childList2);
									expandableListView.setAdapter(adapter);
								}
							} 

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					else {
						showToast("请重试！重新选择医院");

					}
					super.onSuccess(statusCode, headers, responseBody);
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					showToast("数据获取失败，请重试");
					Dissloading();
					// TODO Auto-generated method stub
				}
			});
		}else{
			showToast("当前无网络连接");
		}

		
	}
	public void onGroupExpand(int groupPosition) {
		// TODO Auto-generated method stub
	
			if (adapter!=null) {
				int len = adapter.getGroupCount();
			    for (int i = 0; i < len; i++) {
			        if (i != groupPosition) {
			        	expandableListView.collapseGroup(i);
			        }
			    }
			}
		
	}
}
