package com.hncainiao.fubao.ui.adapter;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hncainiao.fubao.R;


public class MyPatientLogoAdapter extends Adapter<Map<String, Object>>{
	
	Context mContext;
	private LayoutInflater inflater;

	
	

	public MyPatientLogoAdapter(Context mContext) {
		super(mContext);
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.inflater = LayoutInflater.from(mContext);
	}
	 class ViewHolder {
		TextView tvName;
		TextView tvTime;
		TextView tvHospital;
		TextView Office_name;
	}
	 @Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			HashMap<String, Object> map = (HashMap<String, Object>) mList
					.get(position);
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater
						.inflate(R.layout.mypatitenlogo_item, null);
				holder = new ViewHolder();
				holder.tvName = (TextView) convertView
						.findViewById(R.id.name);
				holder.tvTime = (TextView) convertView
						.findViewById(R.id.time);
				holder.tvHospital = (TextView) convertView
						.findViewById(R.id.hospital_name);
				holder.Office_name = (TextView) convertView
						.findViewById(R.id.office_name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tvName.setText(map.get("name") + "");
			holder.tvTime.setText(map.get("time") + "");
			holder.tvHospital.setText(map.get("hospital") + "");
			holder.Office_name.setText(map.get("office_name") + "");

			
			return convertView;
		}
	
	
	
	

}
