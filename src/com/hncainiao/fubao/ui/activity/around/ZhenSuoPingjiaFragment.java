package com.hncainiao.fubao.ui.activity.around;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.adapter.PingjiaAdapter;

public class ZhenSuoPingjiaFragment extends Fragment{
	
	/**
	 * 诊所平价
	 * */
	
	
private View view;
Context mContext;
PingjiaAdapter adapter;
ListView listView;
List<Map<String, Object>>mList;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.zhensuopingja, null);
		mContext=getActivity();
		listView=(ListView) view.findViewById(R.id.list);
		setData();
		return view;
	}
	private void setData() {
		mList=new ArrayList<Map<String,Object>>();
		Map<String , Object>map=null;
		for(int i=0;i<=1;i++){
			
			map=new HashMap<String, Object>();
			map.put("name", "敬请期待");
			map.put("time", "2015-06-34");
			map.put("neirong", "尚未开通，敬请期待");	
			mList.add(map);
		}
		adapter=new PingjiaAdapter(mContext);
		adapter.setList(mList);
		listView.setAdapter(adapter);	
	}
}
