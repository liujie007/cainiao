package com.hncainiao.fubao.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.cloud.BloodGlucoseActivity;
import com.hncainiao.fubao.ui.activity.cloud.BloodNurseActivity;
import com.hncainiao.fubao.ui.activity.cloud.CloudAdapter;
import com.hncainiao.fubao.ui.activity.cloud.HappyMummyActivity;
import com.hncainiao.fubao.ui.activity.cloud.HealthAssistantActivity;
import com.hncainiao.fubao.ui.activity.cloud.HealthWatchActivity;
import com.hncainiao.fubao.ui.activity.cloud.MyBraceletActivity;
import com.hncainiao.fubao.ui.activity.couldhealth.BloodHushi;
import com.hncainiao.fubao.ui.activity.couldhealth.BloodManager;
import com.hncainiao.fubao.ui.activity.couldhealth.HealthZhuShouActivity;
import com.hncainiao.fubao.ui.activity.couldhealth.JWOTCH;
import com.hncainiao.fubao.ui.activity.couldhealth.MyBracelet;
import com.hncainiao.fubao.ui.activity.couldhealth.XingFuMother;


/**
 * @author zhaojing
 * @version 2015年4月15日 下午1:34:28
 * 
 *          云健康管理
 */
public class CloudHealthFragment extends BaseFragment {

	private View view;
	ListView  cloudList;
	Context mContext;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_cloud_health, null);
		mContext=getActivity();
		inintView();
		cloudList.setOnItemClickListener(new OnItemClickListener(){

			Intent  intent=null;
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				switch (arg2) {
				case 0:
					//健康腕表
					intent=new Intent(mContext,JWOTCH.class);
					startActivity(intent);
					
					
					break;
				case 1:
					//我的手环
					intent=new Intent(mContext,MyBracelet.class);
					startActivity(intent);
					
					break;
				case 2:
					//血压护士
					intent=new Intent(mContext,BloodHushi.class);
					startActivity(intent);
					
					break;
				case 3:
					//血糖管家
					intent=new Intent(mContext,BloodManager.class);
					startActivity(intent);
					
					break;
				case 4:
					//幸福妈咪
					intent=new Intent(mContext,XingFuMother.class);
					startActivity(intent);
					
					break;
				case 5:
					//健康助手
					intent=new Intent(mContext,HealthZhuShouActivity.class);
					startActivity(intent);
					break;
				default:
					break;
				}
				
			}
			
		});
		
		return view;
	}
	/**
	 * 数据来源
	 */
	List<Map<String, Object>> list =new ArrayList<Map<String,Object>>();
	
	private List<Map<String, Object>> setDate()
	{
		list.clear();
		HashMap<String, Object> map =new HashMap<String, Object>();
		map.put("title", "JWOTCH 健康腕表");
		map.put("content", "一键云端管理平台守您和家人的健康");
		map.put("img", this.getResources().getDrawable(R.drawable.cloud_sb));
		list.add(map);
		
		HashMap<String, Object> map1 =new HashMap<String, Object>();
		map1.put("title", "我的手环");
		map1.put("content", "穿戴式智能设备通过数据指导健康生活");
		map1.put("img", this.getResources().getDrawable(R.drawable.cloud_zs));
		list.add(map1);
		
		HashMap<String, Object> map2 =new HashMap<String, Object>();
		map2.put("title", "血压护士");
		map2.put("content", "移动智能血压计自动测量实现对血压的监控");
		map2.put("img", this.getResources().getDrawable(R.drawable.cloud_xy));
		list.add(map2);
		
		HashMap<String, Object> map3 =new HashMap<String, Object>();
		map3.put("title", "血糖管家");
		map3.put("content", "帮助糖尿病患者实现全方位的自我管理");
		map3.put("img", this.getResources().getDrawable(R.drawable.cloud_xt));
		list.add(map3);
		
		HashMap<String, Object> map4 =new HashMap<String, Object>();
		map4.put("title", "幸福妈咪");
		map4.put("content", "随时了解身体的变化，智能监控宝宝的成长。");
		map4.put("img", this.getResources().getDrawable(R.drawable.cloud_mm));
		list.add(map4);
		
		HashMap<String, Object> map5 =new HashMap<String, Object>();
		map5.put("title", "健康助手");
		map5.put("content", "通过健康测试了解身体状况并提供调理方案");
		map5.put("img", this.getResources().getDrawable(R.drawable.cloud_jr));
		list.add(map5);
		return list;
		
	}
	/**
	 * 控件初始化
	 */
	private void inintView()
	{
		((TextView) view.findViewById(R.id.title_txt)).setText("云健康管理");
		 cloudList=(ListView)view.findViewById(R.id.listCould);
	    ((Button) view.findViewById(R.id.comeback)).setVisibility(View.INVISIBLE);
	   CloudAdapter cloudAdapter=  new CloudAdapter(getActivity());
	   cloudAdapter.addList(setDate());
	    cloudList.setAdapter(cloudAdapter);

	    cloudList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 处理业务
				switch (arg2) {
				case 0:
					startActivity(new Intent(getActivity(),HealthWatchActivity.class));
					
					break;
				case 1:
					startActivity(new Intent(getActivity(),MyBraceletActivity.class));
					
					break;
				case 2:
					startActivity(new Intent(getActivity(),BloodNurseActivity.class));
					
					break;
				case 3:
					startActivity(new Intent(getActivity(),BloodGlucoseActivity.class));
					
					break;
				case 4:
					startActivity(new Intent(getActivity(),HappyMummyActivity.class));
					
					break;
				case 5:
					startActivity(new Intent(getActivity(),HealthAssistantActivity.class));
					
					break;

				default:
					break;
				}
				
			}
		});

	}
	
	@Override
	protected void creatFragmentUI() {
		// TODO Auto-generated method stub
		
	}
}
