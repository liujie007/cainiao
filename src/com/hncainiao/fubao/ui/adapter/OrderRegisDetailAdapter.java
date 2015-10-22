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
public class OrderRegisDetailAdapter extends Adapter<Map<String, Object>> {

	private Context mContext;

	private LayoutInflater inflater;

	public OrderRegisDetailAdapter(Context mContext) {
		super(mContext);
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.inflater = LayoutInflater.from(mContext);
	}

	static class ViewHolder {
		ImageView ivOrder;
		TextView tvTime;
		TextView tvOut;
		TextView tvMoney;
		View view;//listview下面的那条下划线
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = (HashMap<String, Object>) mList.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater
					.inflate(R.layout.item_list_order_regis, null);
			holder = new ViewHolder();
			holder.tvTime = (TextView) convertView
					.findViewById(R.id.tv_item_order_time);
			holder.tvOut = (TextView) convertView
					.findViewById(R.id.tv_item_order_outpatient);
			holder.tvMoney = (TextView) convertView
					.findViewById(R.id.tv_item_order_money);
			holder.ivOrder = (ImageView) convertView
					.findViewById(R.id.iv_item_order_state);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvTime.setText(map.get("order_time") + "");
		holder.tvOut.setText(map.get("order_out") + "");
		holder.tvMoney.setText("挂号费" + map.get("order_money"));
		
		//String str=SharedPreferencesConfig.getStringConfig(mContext, "doctor_status1");
		String str=map.get("order_status")+"";
          System.out.println("预约列表+++"+str);
		if (str.equals("1")) { // 有号
			holder.ivOrder.setImageResource(R.drawable.img_order);
		}else if(str.equals("0")){
			
			holder.ivOrder.setImageResource(R.drawable.img_unorder);

		}
		 else  if(str.equals("2")){
				//holder.tvTime.setVisibility(ViewGroup.GONE);
				//holder.tvMoney.setVisibility(ViewGroup.GONE);
				//holder.tvOut.setVisibility(ViewGroup.GONE);
				//holder.ivOrder.setVisibility(ViewGroup.INVISIBLE);
				holder.ivOrder.setImageResource(R.drawable.nohao);

			}
		return convertView;
	}
}
