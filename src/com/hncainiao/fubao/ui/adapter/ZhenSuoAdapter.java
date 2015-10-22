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
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.ui.activity.BaseActivity;

public class ZhenSuoAdapter extends Adapter<Map<String, Object>>{
	private Context mContext;
	private LayoutInflater inflater;
	 
	
	public ZhenSuoAdapter(Context Context) {
		super(Context);
		this.mContext = Context;
		this.inflater = LayoutInflater.from(mContext);
	}
	
	 class ViewHolder{
			ImageView img;
			TextView name;
			TextView adress;
			TextView distance;

		}  
	 @Override
		public View getView( int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			HashMap<String, Object> map = (HashMap<String, Object>) mList
					.get(position);
			ViewHolder holder = null;
			
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.zhensuo_item, null);
				holder = new ViewHolder();
				holder.img = (ImageView) convertView.findViewById(R.id.yaodian_img);
				holder.adress = (TextView) convertView.findViewById(R.id.adress);
				holder.distance = (TextView) convertView.findViewById(R.id.yaodian_distance);
				holder.name = (TextView) convertView.findViewById(R.id.taodian_name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.name.setText(map.get("name") +"");
			holder.distance.setText(map.get("distance")+"");
			holder.adress.setText(map.get("address")+"");
			BaseActivity.imageLoader.displayImage(Constant.URL_IMAGE_HOST_STRING+map.get("img"), 
					holder.img, BaseActivity.options);
			return convertView;
		}
	 

}
