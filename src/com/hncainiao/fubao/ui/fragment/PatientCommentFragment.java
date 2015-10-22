package com.hncainiao.fubao.ui.fragment;

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
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.adapter.PingjiaAdapter;

/**
 * @author zhaojing
 * @version 2010年8月8日 上午10:19:20
 * 
 *          患者评价
 */
public class PatientCommentFragment extends Fragment {
	List<Map<String, Object>>mList;
	Context mContext;
	ListView listView;
	TextView nums;
	PingjiaAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_patient_comment, null);
		mContext=getActivity();
		listView=(ListView) view.findViewById(R.id.list);
		nums=(TextView) view.findViewById(R.id.nuns);
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
		nums.setText("("+mList.size()+")");
		adapter=new PingjiaAdapter(mContext);
		adapter.setList(mList);
		listView.setAdapter(adapter);	
	}
}
