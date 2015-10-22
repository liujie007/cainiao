package com.hncainiao.fubao.ui.adapter;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.hncainiao.fubao.R;

public class ICCHAdpater extends Adapter<Map<String, Object>>{
	
	private Context mContext;

	private LayoutInflater inflater;

	public ICCHAdpater(Context Context) {
		super(Context);
		this.mContext = Context;
		this.inflater = LayoutInflater.from(mContext);
	}
	class ViewHolder{
		CheckBox box;
		TextView adress;
		TextView name;
		TextView phone;
		ImageView updata;
		
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = (HashMap<String, Object>) mList
				.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.ichi_item, null);
			holder = new ViewHolder();
			holder.updata = (ImageView) convertView
					.findViewById(R.id.pic);
			holder.adress = (TextView) convertView
					.findViewById(R.id.adress);
			holder.name = (TextView) convertView
					.findViewById(R.id.name);
			holder.phone = (TextView) convertView
					.findViewById(R.id.phone);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(map.get("name") + "");
		holder.adress.setText(map.get("adress") + "");
		holder.phone.setText(map.get("phone") + "");
		return convertView;
	}

}
