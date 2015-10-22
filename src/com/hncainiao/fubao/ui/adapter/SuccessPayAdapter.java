package com.hncainiao.fubao.ui.adapter;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hncainiao.fubao.R;

public class SuccessPayAdapter extends Adapter<Map<String, Object>> {
	
	
	private Context mContext;

	private LayoutInflater inflater;

	public SuccessPayAdapter(Context mContext) {
		super(mContext);
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.inflater = LayoutInflater.from(mContext);
	}
	static class ViewHolder {
		TextView tvName;
		TextView time;
		TextView hospital;
		TextView idcard;
		TextView department;
		TextView zhencha;
		TextView juti;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = (HashMap<String, Object>) mList
				.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.success_pay_adapter,
					null);
			holder = new ViewHolder();
	
			holder.tvName = (TextView) convertView
					.findViewById(R.id.name);
			holder.time = (TextView) convertView
					.findViewById(R.id.time);
			holder.hospital=(TextView) convertView
					.findViewById(R.id.hospital);
			holder.idcard=(TextView) convertView
					.findViewById(R.id.idcard);
			
			holder.department=(TextView) convertView
					.findViewById(R.id.department);
			holder.zhencha=(TextView) convertView
					.findViewById(R.id.shoufeixiang);
			holder.juti=(TextView) convertView
					.findViewById(R.id.juti);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvName.setText(map.get("name") + "");
		holder.time.setText(map.get("time") + "");
		holder.hospital.setText(map.get("hospital") + "");
		holder.idcard.setText(map.get("idcard") + "");
		holder.department.setText(map.get("department") + "");
		holder.zhencha.setText(map.get("zhencha") + "");
		holder.juti.setText(map.get("juti") + "");
		return convertView;
	}

}
