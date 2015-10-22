package com.hncainiao.fubao.ui.adapter;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
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

public class Res_srarchAdapter extends Adapter<Map<String, Object>>{
	
 private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
   private Context  mContext;
   private LayoutInflater inflater;

   public Res_srarchAdapter(Context mContext) {
		super(mContext);
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.inflater = LayoutInflater.from(mContext);
	}
   class ViewHolder{
	    ImageView ivPic;
		TextView tvName;
		TextView tvLevel;
		TextView tvLocate;
		TextView good;
		TextView keshi;
   }
   
   public View getView(int position, View convertView, ViewGroup parent) {
	   
	   HashMap<String, Object> map = (HashMap<String, Object>) mList
				.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.res_search_item, null);
			holder = new ViewHolder();
			holder.ivPic = (ImageView) convertView
					.findViewById(R.id.img_item_doctor);
			holder.tvName = (TextView) convertView
					.findViewById(R.id.tv_item_doctor_name);
			holder.tvLevel = (TextView) convertView
					.findViewById(R.id.doc_level);
			holder.tvLocate = (TextView) convertView
					.findViewById(R.id.hospital);
			holder.good = (TextView) convertView
					.findViewById(R.id.good_at);
			holder.keshi = (TextView) convertView
					.findViewById(R.id.doc_keshi);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvName.setText(map.get("name") + "");
		holder.tvLevel.setText(map.get("level") + "");
		holder.tvLocate.setText(map.get("hospital") + "");//医院名
		holder.good.setText(map.get("good")+"");
		holder.keshi.setText(map.get("keshi")+"");
		BaseActivity.imageLoader.displayImage(Constant.URL_IMAGE_HOST_STRING+map.get("img"), holder.ivPic, 
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
