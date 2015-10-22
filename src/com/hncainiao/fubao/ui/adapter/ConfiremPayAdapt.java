package com.hncainiao.fubao.ui.adapter;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hncainiao.fubao.R;

public class ConfiremPayAdapt extends Adapter<Map<String, Object>>{

	private Context mContext;

	private LayoutInflater inflater;
	
	public ConfiremPayAdapt(Context Context) {
		super(Context);
		this.mContext = Context;
		this.inflater = LayoutInflater.from(mContext);
	}
	class ViewHolder{
		TextView data;
		TextView name;
		TextView department;
		TextView idcard;
		TextView hospiatl;
		TextView zhenchaxiang;
		TextView jiti;
	} 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = (HashMap<String, Object>) mList
				.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.confirempaylay, null);
			holder = new ViewHolder();
			holder.data = (TextView) convertView 
					.findViewById(R.id.time);
			holder.name = (TextView) convertView
					.findViewById(R.id.name);
			holder.department = (TextView) convertView
					.findViewById(R.id.department);
			
			holder.idcard = (TextView) convertView
					.findViewById(R.id.idcard);
			
			holder.hospiatl = (TextView) convertView
					.findViewById(R.id.hospital);
			holder.zhenchaxiang = (TextView) convertView
					.findViewById(R.id.shoufeixiang);
			
			holder.jiti = (TextView) convertView
					.findViewById(R.id.juti);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(map.get("name") + "");
		holder.data.setText(map.get("data") + "");
		
		holder.department.setText(map.get("department") + "");
		holder.idcard.setText(map.get("idcard") + "");
		holder.hospiatl.setText(map.get("hospiatl") + "");
		holder.zhenchaxiang.setText(map.get("zhenchaxiang") + "");
		holder.jiti.setText(map.get("jiti") + "");
		return convertView;
	}

}
