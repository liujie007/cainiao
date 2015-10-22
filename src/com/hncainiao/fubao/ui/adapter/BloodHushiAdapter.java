package com.hncainiao.fubao.ui.adapter;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hncainiao.fubao.R;

public class BloodHushiAdapter extends Adapter<Map<String, Object>>{
	
	private Context mContext;

	private LayoutInflater inflater;

	public BloodHushiAdapter(Context Context) {
		super(Context);
		this.mContext = Context;
		this.inflater = LayoutInflater.from(mContext);
	}
	class ViewHolder{
		TextView data;
		TextView shousuo;
		TextView shuzhan;
		TextView  status;
		
		
		
	} 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = (HashMap<String, Object>) mList
				.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.bloodushi_item, null);
			holder = new ViewHolder();
			holder.data = (TextView) convertView
					.findViewById(R.id.data);
			holder.shousuo = (TextView) convertView
					.findViewById(R.id.shousuo);
			holder.shuzhan = (TextView) convertView
					.findViewById(R.id.shuzhang);
			holder.status = (TextView) convertView
					.findViewById(R.id.normol);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.data.setText(map.get("data") + "");
		holder.shousuo.setText(map.get("shousuo") + "");
		holder.shuzhan.setText(map.get("shuzhan") + "");
		holder.status.setText(map.get("status") + "");
		return convertView;
	}

	
	
	

}
