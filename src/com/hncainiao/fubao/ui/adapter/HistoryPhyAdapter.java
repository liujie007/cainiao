package com.hncainiao.fubao.ui.adapter;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.phyexam.PhyMenuDetailActivity;

/**
 * @author zhaojing
 * @version 2010年8月7日 下午12:30:38
 * 
 */
public class HistoryPhyAdapter extends Adapter<Map<String, Object>> {

	private Context mContext;

	private LayoutInflater inflater;

	public HistoryPhyAdapter(Context mContext) {
		super(mContext);
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.inflater = LayoutInflater.from(mContext);
	}

	 static class ViewHolder {
		TextView tvMenu;
		TextView tvHospital;
		TextView tvTime;
		Button btnDetail;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = (HashMap<String, Object>) mList.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_list_history_order,null);
			holder = new ViewHolder();
			holder.tvMenu = (TextView) convertView.findViewById(R.id.tv_item_phy_menu);
			holder.tvHospital = (TextView) convertView.findViewById(R.id.tv_item_phy_hospital);
			holder.tvTime = (TextView) convertView.findViewById(R.id.tv_item_phy_time);
			holder.btnDetail = (Button) convertView.findViewById(R.id.btn_item_phy_detail);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
          
	/*	if (position==0) {
			holder.tvMenu.setText("历史体检");
			holder.tvMenu.setGravity(Gravity.CENTER_VERTICAL);
			holder.tvHospital.setTextColor(Color.rgb(24, 24, 24));
			holder.tvHospital.setVisibility(View.GONE);
			holder.tvTime.setVisibility(View.GONE);
			holder.btnDetail.setVisibility(View.GONE);
		} else {*/
			holder.tvMenu.setText(map.get("menu") + "");
			holder.tvHospital.setText(map.get("hospital") + "");
			holder.tvTime.setText(map.get("time").toString());
			holder.tvHospital.setVisibility(View.VISIBLE);
			holder.tvTime.setVisibility(View.VISIBLE);
			holder.btnDetail.setVisibility(View.VISIBLE);
			holder.btnDetail.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
					Intent intent =new Intent(mContext,PhyMenuDetailActivity.class);
					//intent.putExtra("date", mList.get(position).get("id")+"");
					intent.putExtra("flag", 1);
					intent.putExtra("package_id", mList.get(position).get("package_id")+"");
				    mContext.startActivity(intent);
				}
			});
		//}
		
		return convertView;
	}
}
