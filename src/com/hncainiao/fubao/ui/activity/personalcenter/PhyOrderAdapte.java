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
public class PhyOrderAdapte extends Adapter<Map<String, Object>> {

	private Context mContext;

	private LayoutInflater inflater;

	public PhyOrderAdapte(Context mContext) {
		super(mContext);
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.inflater = LayoutInflater.from(mContext);
	}

	static class ViewHolder {
		TextView tvTime;
		TextView tvPeople;
		TextView tvYy;
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
			convertView = inflater.inflate(R.layout.item_list_phy_person_exam, null);
			holder = new ViewHolder();
			holder.tvTime = (TextView) convertView
					.findViewById(R.id.tv_time);

			holder.tvPeople = (TextView) convertView
					.findViewById(R.id.tv_people);

			holder.tvYy = (TextView) convertView
					.findViewById(R.id.tv_yy);

			holder.tvHost = (TextView) convertView
					.findViewById(R.id.tv_host);
			holder.tvYy = (TextView) convertView
					.findViewById(R.id.tv_yy);
			holder.tvTc = (TextView) convertView
					.findViewById(R.id.tv_tc);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvTime.setText(map.get("tvtime") + "");
		holder.tvPeople.setText("体检人:"+map.get("tvpeople") + "");
		
		switch (Integer.parseInt(map.get("tvyy")+"")) {
		case 0:
			holder.tvYy.setText("已取消");
			break;
		case 1:
			holder.tvYy.setText("已预约");
			break;
		case 2:
			holder.tvYy.setText("已生成报告");
			break;

		}
		
		
		
		holder.tvHost.setText(map.get("host") + "");
		holder.tvTc.setText(map.get("tc") + "");
		

		return convertView;
	}
}
