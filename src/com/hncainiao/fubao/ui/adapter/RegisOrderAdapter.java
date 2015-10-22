package com.hncainiao.fubao.ui.adapter;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hncainiao.fubao.R;

/**
 * @author zhaojing
 * @version 2010年8月7日 下午12:30:38
 * 
 */
public class RegisOrderAdapter extends Adapter<Map<String, Object>> {

	private Context mContext;

	private LayoutInflater inflater;

	public RegisOrderAdapter(Context mContext) {
		super(mContext);
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.inflater = LayoutInflater.from(mContext);
	}

	static class ViewHolder {
		TextView tvName;
		TextView tvTime;
		TextView tvHospital;
		TextView tvState;
		TextView patient_naem;
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
					.inflate(R.layout.item_list_regis_order, null);
			holder = new ViewHolder();
			holder.tvName = (TextView) convertView
					.findViewById(R.id.patient_time);
			holder.tvTime = (TextView) convertView
					.findViewById(R.id.tiem);
			holder.tvHospital = (TextView) convertView
					.findViewById(R.id.hospital_name);
			holder.tvState = (TextView) convertView
					.findViewById(R.id.tv_item_order_state);
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
		String states=map.get("status")+"";
		if (states.equals("0")) {
			holder.tvState.setText("已取消");
		}
		if(states.equals("1")){
			holder.tvState.setText("订单已生成");
		}
		if(states.equals("2")){
			holder.tvState.setText("病历已生成");
		}
		if(states.equals("20")){
			holder.tvState.setText("已预约未支付");
		}
		if(states.equals("10")){
			holder.tvState.setText("已预约已支付");
		}
		if(states.equals("40")){
			holder.tvState.setText("未付款未挂号");
		}
		if(states.equals("30")){
			holder.tvState.setText("已付款未挂号");
		}
		
		return convertView;
	}
}
