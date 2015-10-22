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

/**
 * @author zhaojing
 * @version 2010年8月7日 下午12:30:38
 * 
 */
public class HospitalAdapter extends Adapter<Map<String, Object>> {

	private Context mContext;

	private LayoutInflater inflater;
	

	public HospitalAdapter(Context mContext) {
		super(mContext);
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.inflater = LayoutInflater.from(mContext);
	}

	static class ViewHolder {
		ImageView ivPic;
		TextView tvName;
		TextView tvLevel;
		TextView tvDistance;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = (HashMap<String, Object>) mList.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_list_hospital, null);
			holder = new ViewHolder();
			holder.ivPic = (ImageView) convertView
					.findViewById(R.id.img_item_hospital);
			holder.tvName = (TextView) convertView
					.findViewById(R.id.tv_item_hospital_name);
			holder.tvLevel = (TextView) convertView
					.findViewById(R.id.tv_item_hospital_level);
			holder.tvDistance = (TextView) convertView
					.findViewById(R.id.tv_item_hospital_diatance);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvName.setText(map.get("name") + "");
		holder.tvLevel.setText(map.get("type") + "");
		holder.tvDistance.setText(map.get("distance") + "");
		return convertView;
	}
}
