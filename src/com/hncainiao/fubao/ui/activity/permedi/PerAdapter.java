package com.hncainiao.fubao.ui.activity.permedi;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.adapter.Adapter;

public class PerAdapter extends Adapter<Map<String, Object>>  {

	Context context;
	public PerAdapter(Context mContext) {
		super(mContext);
		// TODO Auto-generated constructor stub
		context=mContext;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return super.getCount();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return super.getItem(position);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		  HashMap<String, Object> map =(HashMap<String, Object>) mList.get(position);
		ViewHoder vHoder=null;
		if (convertView==null) {
			convertView=LinearLayout.inflate(context, R.layout.list_per_adater,null);
		    vHoder=new ViewHoder();
			vHoder.tvTile=(TextView)convertView.findViewById(R.id.tv_title);
			vHoder.tvConter=(TextView)convertView.findViewById(R.id.tv_conter);
			vHoder.imHead=(ImageView)convertView.findViewById(R.id.im_head);
		    convertView.setTag(vHoder);	
		}
		else {
			vHoder=(ViewHoder)convertView.getTag();
		}
		
		vHoder.tvTile.setText(map.get("title")+"");
		vHoder.tvConter.setText(map.get("content")+"");
		vHoder.imHead.setImageDrawable((Drawable) map.get("img"));
		return convertView;		
	}
	class ViewHoder
	{
	  TextView tvTile,tvConter;
	  ImageView imHead;
	}

	
}
