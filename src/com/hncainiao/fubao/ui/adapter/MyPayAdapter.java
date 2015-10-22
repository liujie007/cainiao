package com.hncainiao.fubao.ui.adapter;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hncainiao.fubao.R;

public class MyPayAdapter extends Adapter<Map<String, Object>>{
	private Context mContext;

	private LayoutInflater inflater;

	public MyPayAdapter(Context Context) {
		super(Context);
		this.mContext = Context;
		this.inflater = LayoutInflater.from(mContext);
	}
	class ViewHolder{
		TextView data;
		TextView name;
		TextView connect;
		TextView money;
		TextView hospiatl;
		ImageView img;
	} 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = (HashMap<String, Object>) mList
				.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.mypayitem, null);
			holder = new ViewHolder();
			holder.img = (ImageView) convertView
					.findViewById(R.id.img);
			holder.data = (TextView) convertView
					.findViewById(R.id.time);
			holder.name = (TextView) convertView
					.findViewById(R.id.name);
			holder.connect = (TextView) convertView
					.findViewById(R.id.connect);
			holder.money = (TextView) convertView
					.findViewById(R.id.money);
			holder.hospiatl = (TextView) convertView
					.findViewById(R.id.hospital);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(map.get("name") + "");
		holder.data.setText(map.get("data") + "");
		holder.connect.setText(map.get("connect") + "");
		holder.money.setText(map.get("money") + "");
		holder.hospiatl.setText(map.get("hospiatl") + "");

		return convertView;
	}
	

}
