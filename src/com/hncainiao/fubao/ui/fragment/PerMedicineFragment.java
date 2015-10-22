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
import com.hncainiao.fubao.ui.activity.permedi.PerAdapter;
import com.hncainiao.fubao.ui.activity.permedicine.GanXiBaoActivity;
import com.hncainiao.fubao.ui.activity.permedicine.JiYinTestActivity;
import com.hncainiao.fubao.ui.activity.permedicine.OutCountryActivity;
import com.hncainiao.fubao.ui.activity.permedicine.PersonDoctorActivity;
import com.hncainiao.fubao.ui.activity.permedicine.SpecialMedicineActivity;
import com.hncainiao.fubao.ui.activity.permedicine.VipActivity;
 
/**
 * @author zhaojing
 * @version 2015年4月15日 下午1:34:28
 * 
 *          个性化医疗
 */
public class PerMedicineFragment extends BaseFragment {

	private View view;
	ListView  PerMedicineList;
	Context mContext;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_personalized_medicine, null);
		mContext=getActivity();
		inintView();
		return view;
	}
	/**
	 * 控件初始化
	 */
	private void inintView()
	{
		 ((TextView) view.findViewById(R.id.title_txt)).setText("个性化医疗");
		 PerMedicineList=(ListView)view.findViewById(R.id.list);
		 ((Button) view.findViewById(R.id.comeback)).setVisibility(View.INVISIBLE);
		 PerAdapter adpter=  new PerAdapter(getActivity());
		 adpter.addList(setDate());
		 PerMedicineList.setAdapter(adpter);
		 PerMedicineList.setOnItemClickListener(new OnItemClickListener(){
          Intent intent=null;
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch (arg2) {
				case 0://高端私人医生
					intent=new Intent(mContext,PersonDoctorActivity.class);
					startActivity(intent);
					
					break;
				case 1://基因检测
					intent=new Intent(mContext,JiYinTestActivity.class);
					startActivity(intent);
				    break;
				case 2://干细胞
					intent=new Intent(mContext,GanXiBaoActivity.class);
					startActivity(intent);
					
					
					break;
               case 3://癌症治疗
					
					break;
               case 4://国外就医
            	   intent=new Intent(mContext,OutCountryActivity.class);
            	   startActivity(intent);
					
					break;
               case 5://特殊药品定制
            	   intent=new Intent(mContext,SpecialMedicineActivity.class);
            	   startActivity(intent);
					
					break;
			  case 6://vip热线服务
				  intent=new Intent(mContext,VipActivity.class);
				  startActivity(intent);
						
						break;
					

				default:
					break;
				}
				
			}
			 
		 }) ;
		 
		 
	}
	/**
	 * 数据来源
	 */
	List<Map<String, Object>> list =new ArrayList<Map<String,Object>>();
	
	private List<Map<String, Object>> setDate()
	{
		list.clear();
		HashMap<String, Object> map =new HashMap<String, Object>();
		map.put("title", "高端私人医生");
		map.put("content", "根据你的身体状况制定完整的健康调理方案");
		map.put("img", this.getResources().getDrawable(R.drawable.per_ys));
		list.add(map);
		
		HashMap<String, Object> map1 =new HashMap<String, Object>();
		map1.put("title", "基因检测");
		map1.put("content", "为你提供贯穿整个生命周期的健康服务");
		map1.put("img", this.getResources().getDrawable(R.drawable.per_jy));
		list.add(map1);
		
		HashMap<String, Object> map2 =new HashMap<String, Object>();
		map2.put("title", "干细胞");
		map2.put("content", "为您进行干细胞留存，培养与移植服务");
		map2.put("img", this.getResources().getDrawable(R.drawable.per_gxb));
		list.add(map2);
		 
		HashMap<String, Object> map3 =new HashMap<String, Object>();
		map3.put("title", "癌症治疗");
		map3.put("content", "为癌症患者进行特殊治疗和个性化套餐服务");
		map3.put("img", this.getResources().getDrawable(R.drawable.per_yz));
		list.add(map3);
		
		HashMap<String, Object> map4 =new HashMap<String, Object>();
		map4.put("title", "国外就医");
		map4.put("content", "为患者提供国外高档就医一条龙服务");
		map4.put("img", this.getResources().getDrawable(R.drawable.per_gw));
		list.add(map4);
		
		HashMap<String, Object> map5 =new HashMap<String, Object>();
		map5.put("title", "特殊药品定制");
		map5.put("content", "根据你的需求提供特殊样品和新特药配送服务");
		map5.put("img", this.getResources().getDrawable(R.drawable.per_yy));
		list.add(map5);
		
		HashMap<String, Object> map6 =new HashMap<String, Object>();
		map6.put("title", "Vip热线服务");
		map6.put("content", "24小时专属健康管家响应，全面负责你的健康问题");
		map6.put("img", this.getResources().getDrawable(R.drawable.per_vip));
		list.add(map6);
		return list;
		
	}
	@Override
	protected void creatFragmentUI() {
		// TODO Auto-generated method stub
	}
}
