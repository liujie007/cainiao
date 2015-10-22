package com.hncainiao.fubao.ui.activity.personalcenter;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.adapter.Adapter;

/**
 * @author liujie
 * @version 2010年8月7日 下午12:30:38
 * 
 */
public class PhyTalAdapte extends Adapter<Map<String, Object>> {

	private Context mContext;

	private LayoutInflater inflater;

	public PhyTalAdapte(Context mContext) {
		super(mContext);
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.inflater = LayoutInflater.from(mContext);
	}

	static class ViewHolder {
		TextView tvTime;
		TextView tvPeople;
		TextView tvHost;
		TextView tvTc;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = (HashMap<String, Object>) mList
				.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_list_phy_person_tai, null);
			holder = new ViewHolder();
			holder.tvTime = (TextView) convertView
					.findViewById(R.id.tv_time);

			holder.tvPeople = (TextView) convertView
					.findViewById(R.id.tv_people);

			

			holder.tvHost = (TextView) convertView
					.findViewById(R.id.tv_host);
			
			holder.tvTc = (TextView) convertView
					.findViewById(R.id.tv_tc);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvTime.setText(map.get("tvtime") + "");
		holder.tvPeople.setText(""+map.get("tvpeople") + "");
		
		holder.tvHost.setText(map.get("host") + "");
		holder.tvTc.setText(map.get("tc") + "");
		

		return convertView;
	}
}
