package com.hncainiao.fubao.ui.adapter;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hncainiao.fubao.R;

public class PingjiaAdapter extends Adapter<Map<String, Object>>{
	private Context mContext;

	private LayoutInflater inflater;

	public PingjiaAdapter(Context mContext) {
		super(mContext);
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.inflater = LayoutInflater.from(mContext);
	}
	class ViewHolder{
		TextView name;
		TextView time;
		TextView connect;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = (HashMap<String, Object>) mList
				.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.pingjia_item, null);
			holder = new ViewHolder();
			
			holder.name = (TextView) convertView
					.findViewById(R.id.name);
			holder.time = (TextView) convertView
					.findViewById(R.id.time);
			holder.connect = (TextView) convertView
					.findViewById(R.id.neirong);
		
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(map.get("name") + "");
		holder.time.setText(map.get("time") + "");
		holder.connect.setText(map.get("neirong") + "");
		
		return convertView;
	}
}
