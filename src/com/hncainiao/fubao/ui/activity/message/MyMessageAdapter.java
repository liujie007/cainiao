package com.hncainiao.fubao.ui.activity.message;


import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.adapter.Adapter;

public class MyMessageAdapter extends  Adapter<Map<String, Object>>{

	public MyMessageAdapter(Context mContext) {
		super(mContext);
		// TODO Auto-generated constructor stub
	}

	class ViewHolder
	{
		TextView tvTile,tvMessage,tvNum,neirong,time;
		LinearLayout message;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HashMap<String, Object> map= (HashMap<String, Object>) mList.get(position);
		ViewHolder viewHolder =null;
		if (convertView==null) {
			convertView=	LayoutInflater.from(mContext).inflate(R.layout.list_message_itme, null);
			viewHolder=new ViewHolder();
			viewHolder.tvTile=(TextView)convertView.findViewById(R.id.tv_title);
			viewHolder.tvMessage=(TextView)convertView.findViewById(R.id.tv_message);
			viewHolder.tvNum=(TextView)convertView.findViewById(R.id.tv_num);
			viewHolder.time=(TextView)convertView.findViewById(R.id.time);
			viewHolder.neirong=(TextView)convertView.findViewById(R.id.messageconnect);
			viewHolder.message=(LinearLayout)convertView.findViewById(R.id.message_lay);
			convertView.setTag(viewHolder);
		}
		else {
			viewHolder=(ViewHolder)convertView.getTag();
		}
		if(map.get("status").equals("1")){
			viewHolder.message.setVisibility(ViewGroup.VISIBLE);
			
		}else if(map.get("status").equals("2")){
			viewHolder.message.setVisibility(ViewGroup.GONE);

		}
		
		viewHolder.tvTile.setText(""+map.get("tv_tile"));
		viewHolder.tvMessage.setText(""+map.get("tv_message"));
		viewHolder.neirong.setText(""+map.get("connect"));
		viewHolder.time.setText(""+map.get("time"));
		viewHolder.tvNum.setText(""+map.get("tv_num"));
		return convertView;	
	}
}
