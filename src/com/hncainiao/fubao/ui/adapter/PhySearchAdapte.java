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
 * @author lijieu
 * @version 2010年8月7日 下午12:30:38
 * 
 */
public class PhySearchAdapte extends Adapter<Map<String, Object>> {

	private Context mContext;

	private LayoutInflater inflater;

	public PhySearchAdapte(Context mContext) {
		super(mContext);
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.inflater = LayoutInflater.from(mContext);
		
	}

	static class ViewHolder {
		TextView tvName;
		TextView tvDetail;
		TextView tvPrice;
		TextView tvHostey;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = (HashMap<String, Object>) mList.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_list_phy_search, null);
			holder = new ViewHolder();
			holder.tvName = (TextView) convertView	.findViewById(R.id.tv_item_menu_name);

			holder.tvDetail = (TextView) convertView.findViewById(R.id.tv_item_menu_detail);

			holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_item_menu_price);

			holder.tvHostey= (TextView) convertView.findViewById(R.id.tv_item_hostey);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvName.setText(map.get("name") + "");
		holder.tvDetail.setText(map.get("detail") + "");
		holder.tvPrice.setText("￥"+map.get("price") + "");
		holder.tvHostey.setText(map.get("hostey") + "");
		return convertView;
	}
}
