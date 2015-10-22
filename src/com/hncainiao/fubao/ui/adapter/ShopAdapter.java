package com.hncainiao.fubao.ui.adapter;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ShopAdapter extends Adapter<Map<String, Object>> {
	
	 private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	 
	 private Context mContext;

		private LayoutInflater inflater;
	 

	public ShopAdapter(Context Context) {
		super(Context);
		this.mContext = Context;
		this.inflater = LayoutInflater.from(mContext);
	}
	class ViewHolder{
		ImageView ivpic;
		TextView shop_name;
		TextView hospital_name;
		TextView applyperson;
		TextView nowmoney;
		TextView startmoney;
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = (HashMap<String, Object>) mList
				.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.shop_item, null);
			holder = new ViewHolder();
			holder.ivpic = (ImageView) convertView
					.findViewById(R.id.img_item_doctor);
			holder.shop_name = (TextView) convertView
					.findViewById(R.id.tv_item_doctor_name);
			holder.hospital_name = (TextView) convertView
					.findViewById(R.id.tv_item_doctor_locate);
			holder.applyperson = (TextView) convertView
					.findViewById(R.id.tv_item_doctor_level);
			holder.nowmoney = (TextView) convertView
					.findViewById(R.id.money);
			holder.startmoney = (TextView) convertView
					.findViewById(R.id.start_money);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.shop_name.setText(map.get("shop_name") + "");
		holder.hospital_name.setText(map.get("hospital_name") + "");
		holder.applyperson.setText(map.get("applyperson") + "");
		holder.nowmoney.setText(map.get("nowmoney") + "");
		holder.startmoney.setText(map.get("startmoney") + "");
		
		holder.startmoney.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
		
		
		
		
		BaseActivity.imageLoader.displayImage(Constant.URL_IMAGE_HOST_STRING+map.get("img"), holder.ivpic, 
				BaseActivity.options, animateFirstListener);
		return convertView;
	}
	
	
	
	
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
		
		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				// 是否第一次显示
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					// 图片淡入效果
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
	}
  }

}
