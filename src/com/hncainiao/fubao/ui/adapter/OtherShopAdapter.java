package com.hncainiao.fubao.ui.adapter;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hncainiao.fubao.R;

public class OtherShopAdapter extends Adapter<Map<String, Object>>{
	
	 
	
	private Context mContext;
	private LayoutInflater inflater;

	public OtherShopAdapter(Context Context) {
		super(Context);
		this.mContext = Context;
		this.inflater = LayoutInflater.from(mContext);
	}
	
	
	  class ViewHolder{
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
				convertView = inflater.inflate(R.layout.other_shop_item, null);
				holder = new ViewHolder();
				holder.adress = (TextView) convertView
						.findViewById(R.id.adress);
				holder.distance = (TextView) convertView
						.findViewById(R.id.distance);
				holder.name = (TextView) convertView
						.findViewById(R.id.name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.name.setText(map.get("name") +"");
			holder.distance.setText(map.get("distance")+"");
			holder.adress.setText(map.get("adress")+"");
			//BaseActivity.imageLoader.displayImage(Constant.URL_IMAGE_HOST_STRING+map.get("img"), 
					//holder.img, BaseActivity.options);
			return convertView;
		}

}
